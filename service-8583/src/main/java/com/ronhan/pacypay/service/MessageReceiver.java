package com.ronhan.pacypay.service;

import com.ronhan.iso8583.Message;
import com.ronhan.pacypay.parser.MessageParser;
import com.ronhan.pacypay.parser.ParsedMessage;
import com.ronhan.pacypay.pojo.entity.TransRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/21
 **/
@Service
@Slf4j

public class MessageReceiver {

    @Autowired
    private Map<String, MessageParser> parserMap;

    @Autowired
    private TransRecordService transRecordService;

    @RabbitHandler
    @RabbitListener(bindings = {
            @QueueBinding(
                    exchange = @Exchange(value = "${discover.exchange}", type = ExchangeTypes.FANOUT),
                    value = @Queue("${discover.queue}")
            )
    })
    public void receive(@Payload Message message) {
        log.info("message: {}", message);

        if ("1110".equals(message.getMti()) || "1430".equals(message.getMti())) {
            ParsedMessage pm = parserMap.get(message.getChannel()).parse(message);

            int status = 0;

            if (MessageParser.STATUS.APPROVED.status.equals(pm.getStatus())) {
                //approved
                status = 1;
            } else {
                //declined or failed
                status = 0;
            }
            String localTransTime = pm.getLocalTransTime();
            String traceNumber = pm.getTraceNumber();
            TransRecord tr = transRecordService.getTransByDiscoverUniqueId(localTransTime, traceNumber);
            if (tr != null) {
                if (tr.getType() == 1 && tr.getTransStatus() != 2 && tr.getTransStatus() != status) {
                    updateRecord(tr, status, pm);

                    transRecordService.save(tr);
                } else if (tr.getType() == 2) {
                    if (status == 1 && tr.getTransStatus() != status) {
                        updateRecord(tr, status, pm);

                        TransRecord original = transRecordService.getTransByDiscoverUniqueId(tr.getOriginalTransactionTime(), tr.getOriginalTraceNumber());

                        if (original != null) {
                            original.setTransStatus(2);

                            if (original.getSettled() == 0) {
                                original.setSettled(1);
                                tr.setSettled(1);
                            }

                            tr.setAcceptorId(original.getAcceptorId());
                            tr.setAcceptorName(original.getAcceptorName());
                            tr.setAcceptorStreet(original.getAcceptorStreet());
                            tr.setAcceptorCity(original.getAcceptorCity());
                            tr.setPostalCode(original.getPostalCode());
                            tr.setAcceptorRegion(original.getAcceptorRegion());
                            tr.setAcceptorCountry(original.getAcceptorCountry());
                            tr.setOriginatorCountry(original.getOriginatorCountry());

                            tr.setBillingAmount(pm.getBillingAmount());
                            tr.setBillingCurrency(pm.getBillingCurrency());

                            tr.setExpiryMonth(original.getExpiryMonth());
                            tr.setExpiryYear(original.getExpiryYear());

                            transRecordService.save(tr);
                            transRecordService.save(original);
                        }

                    }
                }
            }
        }

    }

    private void updateRecord(TransRecord tr, int status, ParsedMessage pm) {
        tr.setTransmissionTime(pm.getTransmissionTime());
        tr.setApprovalCode(pm.getApprovalCode());
        tr.setActionCode(pm.getActionCode());
        tr.setReferenceId(pm.getReferenceId());
        tr.setBillingAmount(pm.getBillingAmount());
        tr.setBillingCurrency(pm.getBillingCurrency());
        tr.setTransStatus(status);
        tr.setUpdateTime(LocalDateTime.now());
        tr.setCavvValidationRes(pm.getCavvValidationResult());
    }
}
