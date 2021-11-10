package com.ronhan.pacypay.endpoint;

import com.alibaba.fastjson.JSON;
import com.ronhan.Country;
import com.ronhan.Currency;
import com.ronhan.iso8583.DateUtils;
import com.ronhan.iso8583.Message;
import com.ronhan.iso8583.MessageUtil;
import com.ronhan.iso8583.Providers;
import com.ronhan.iso8583.discover.DiscoverMti;
import com.ronhan.iso8583.discover.handler.Iso8583Decoder;
import com.ronhan.iso8583.discover.netty.ServerAddress;
import com.ronhan.pacypay.parser.MessageParser;
import com.ronhan.pacypay.parser.MessageParser.STATUS;
import com.ronhan.pacypay.parser.ParsedMessage;
import com.ronhan.pacypay.pojo.AuthRequest;
import com.ronhan.pacypay.pojo.CaptureRequest;
import com.ronhan.pacypay.pojo.RefundRequest;
import com.ronhan.pacypay.pojo.Response;
import com.ronhan.pacypay.pojo.entity.CycleRange;
import com.ronhan.pacypay.pojo.entity.TransRecord;
import com.ronhan.pacypay.service.TransRecordService;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import io.netty.channel.Channel;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPool;
import io.netty.util.concurrent.Future;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/25
 **/
@RestController
@Api(tags = "")
@Slf4j
public class Main {

    @Autowired
    private ServerAddress address;

    @Autowired
    private AbstractChannelPoolMap<InetSocketAddress, ChannelPool> poolMap;

    @Autowired
    private MessageFactory<IsoMessage> mf;

    @Autowired
    private Map<String, MessageParser> parserMap;

    @Value("${discover.iic}")
    private String iic;

    @Autowired
    private TransRecordService transRecordService;

    static final String de22 = "100090100000";
    static final String de22_inApp = "100090V00000";

    static final String de3 = "000000";
    static final String de3_authForCredit = "200000";


    @PostMapping("auth")
    public Response<ParsedMessage> auth(@Valid @RequestBody AuthRequest auth) {
        log.info("auth request: {}", JSON.toJSONString(auth));
        //convert
        auth.setAcceptorCountry(Country.convertToNum(auth.getAcceptorCountry()));
        auth.setOriginatorCountry(Country.convertToNum(auth.getOriginatorCountry()));
        auth.setCurrency(Currency.alphaToNum(auth.getCurrency()));

        if (transRecordService.countTransByUniqueId(auth.getUniqueId()) > 0) {
            return Response.error(Response.ErrorCode.REPEAT_TRANS, null);
        }

        TransRecord tr = toTransRecord(auth);

        Response<ParsedMessage> response = null;

        transRecordService.save(tr);

        String cardBin = auth.getCardNo().substring(0, 6);

        CycleRange cycleRange = transRecordService.getCardCycleRange(cardBin);

        if (cycleRange == null) {
            tr.setTransStatus(0);
            transRecordService.save(tr);
            return Response.error(Response.ErrorCode.UNKNOWN_CYCLE_RANGE, null);
        }
        if (cycleRange != null) {
            tr.setIssuerDxs(cycleRange.getDxsIIC());
        }

        AtomicReference<ParsedMessage> parsedMessage = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Pair<ChannelPool, Channel> pair = getChannel();
        if (pair != null) {
            Channel channel = pair.getValue();
            IsoMessage message = toIsoMessage(auth);
            tr.setTransactionTime(message.getAt(12).getValue().toString());
            tr.setTraceNumber(message.getAt(11).getValue().toString());

            Iso8583Decoder decoder = channel.pipeline()
                    .get(Iso8583Decoder.class);
            decoder.setMsgListener(l -> {
                IsoMessage isoMsg = l.get();
                if (isoMsg.getType() == DiscoverMti.MTI1110) {
                    Message msg = MessageUtil.toMap(Providers.DISCOVER, isoMsg);
                    parsedMessage.set(parserMap.get(msg.getChannel()).parse(msg));
                    countDownLatch.countDown();
                }
            });

            channel.writeAndFlush(message);

            try {
                if (!countDownLatch.await(60, TimeUnit.SECONDS)) {
                    //timeout
                    response = Response.error(Response.ErrorCode.TIMEOUT, null);

                    tr.setTransStatus(0);
                } else {
                    ParsedMessage pm = parsedMessage.get();
                    if (pm != null) {
                        pm.setType(ParsedMessage.TransType.AUTH.getVal());
                        response = Response.ok(pm);
                        pm.setUniqueId(auth.getUniqueId());

                        updateRecord(tr, pm);
                    } else {
                        response = Response.error(Response.ErrorCode.SYSTEM_BUSY, null);

                        tr.setTransStatus(0);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                pair.getLeft().release(channel);
            }
        } else {
            response = Response.error(Response.ErrorCode.NOT_AVAILABLE, null);
            tr.setTransStatus(0);
        }

        transRecordService.save(tr);
        return response;
    }

    @PostMapping("capture")
    public Response capture(@Valid @RequestBody CaptureRequest captureRequest) {
        TransRecord tr = transRecordService.getOriginalTrans(captureRequest.getUniqueId());
        if (tr == null) {
            return Response.error(Response.ErrorCode.UNKNOWN_TRADE.code, null);
        }
        if (tr.getAutoCapture() == null || tr.getAutoCapture() != 0 || tr.getSettled() == 1) {
            return Response.error(Response.ErrorCode.TRADE_CANNOT_CAPTURE.code, null);
        }

        tr.setCaptured(1);
        transRecordService.save(tr);
        return Response.ok(captureRequest);
    }

    @PostMapping("reversal")
    public Response<ParsedMessage> refund(@Valid @RequestBody RefundRequest refund) {
        log.info("refund request: {}", JSON.toJSONString(refund));

        //convert
        refund.setCurrency(Currency.alphaToNum(refund.getCurrency()));

        TransRecord original = transRecordService.getOriginalTrans(refund.getOriginalId());

        if (original == null) {
            return Response.error(Response.ErrorCode.UNKNOWN_TRADE, null);
        }

        if (transRecordService.countTransByUniqueId(refund.getUniqueId()) > 0) {
            return Response.error(Response.ErrorCode.REPEAT_TRANS, null);
        }

        String d2 = DateUtils.format(DateUtils.yyMMddHHmmss, TimeZone.getDefault());

        TransRecord tr = toTransRecord(refund, original, d2);

        transRecordService.save(tr);

        Response<ParsedMessage> response = null;
        AtomicReference<ParsedMessage> parsedMessage = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Pair<ChannelPool, Channel> pair = getChannel();

        if (pair != null) {
            Channel channel = pair.getValue();
            IsoMessage message = toIsoMessage(refund, original, d2);
            tr.setTransactionTime(message.getAt(12).getValue().toString());
            tr.setTraceNumber(message.getAt(11).getValue().toString());

            Iso8583Decoder decoder = channel
                    .pipeline()
                    .get(Iso8583Decoder.class);
            decoder.setMsgListener(l -> {
                IsoMessage isoMsg = l.get();
                if (isoMsg.getType() == DiscoverMti.MTI1430) {
                    Message msg = MessageUtil.toMap(Providers.DISCOVER, isoMsg);
                    parsedMessage.set(parserMap.get(msg.getChannel()).parse(msg));
                    countDownLatch.countDown();
                }
            });

            channel.writeAndFlush(message);

            try {
                if (!countDownLatch.await(60, TimeUnit.SECONDS)) {
                    //timeout
                    response = Response.error(Response.ErrorCode.TIMEOUT, null);

                    tr.setTransStatus(0);
                } else {
                    ParsedMessage pm = parsedMessage.get();
                    if (pm != null) {
                        response = Response.ok(pm);
                        pm.setType(ParsedMessage.TransType.REVERSAL.getVal());
                        pm.setUniqueId(refund.getUniqueId());

                        updateRecord(tr, pm);

                        updateOriginal(original, tr, pm);
                    } else {
                        response = Response.error(Response.ErrorCode.SYSTEM_BUSY, null);

                        tr.setTransStatus(0);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                pair.getLeft().release(channel);
            }
        } else {
            response = Response.error(Response.ErrorCode.NOT_AVAILABLE, null);
            tr.setTransStatus(0);
        }
        transRecordService.save(tr);
        return response;
    }

    private IsoMessage toIsoMessage(AuthRequest auth) {
        IsoMessage message = mf.newMessage(DiscoverMti.MTI1100);
        String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));

        message.setField(2, IsoType.LLVAR.value(auth.getCardNo()));
        message.setField(3, IsoType.ALPHA.value("1".equals(auth.getAuthForCredit()) ? de3_authForCredit : de3, 6));
        message.setField(4, IsoType.NUMERIC.value(Currency.format(auth.getAmount(), auth.getCurrency()), 12));
        message.setField(7, IsoType.NUMERIC.value(d1, 10));
        message.setField(12, IsoType.NUMERIC.value(auth.getTransactionTime(), 12));
        message.setField(14, IsoType.NUMERIC.value(auth.getExpiryYear() + auth.getExpiryMonth(), 4));
        message.setField(22, IsoType.ALPHA.value("V".equals(auth.getInApp()) ? de22_inApp : de22, 12));
        message.setField(24, IsoType.NUMERIC.value(100, 3));
        message.setField(26, IsoType.NUMERIC.value(auth.getMcc(), 4));
        message.setField(32, IsoType.LLVAR.value(iic));
        message.setField(33, IsoType.LLVAR.value(iic));
        message.setField(40, IsoType.NUMERIC.value(auth.getCvv(), 3));
        message.setField(42, IsoType.ALPHA.value(auth.getAcceptorId(), 15));
        message.setField(43, IsoType.LLVAR.value(auth.toAcLocation()));
        message.setField(49, IsoType.NUMERIC.value(auth.getCurrency(), 3));
        message.setField(92, IsoType.NUMERIC.value(auth.getOriginatorCountry(), 3));
        if (auth.getThreeDSecure() != null) {
            message.setField(122, IsoType.LLLVAR.value(auth.getThreeDSecure().format3D()));
        }
        return message;
    }

    private IsoMessage toIsoMessage(RefundRequest refund, TransRecord original, String time) {
        IsoMessage message = mf.newMessage(DiscoverMti.MTI1420);
        String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));

        message.setField(2, IsoType.LLVAR.value(original.getCardNo()));
        message.setField(3, IsoType.ALPHA.value("200000", 6));
        message.setField(4, IsoType.NUMERIC.value(Currency.format(refund.getAmount(), refund.getCurrency()), 12));
        message.setField(7, IsoType.NUMERIC.value(d1, 10));
        message.setField(12, IsoType.NUMERIC.value(time, 12));
        message.setField(24, IsoType.NUMERIC.value("1".equals(refund.getFullRefund()) ? 400 : 401, 3));
        message.setField(26, IsoType.NUMERIC.value(original.getMcc(), 4));
        message.setField(32, IsoType.LLVAR.value(iic));
        message.setField(33, IsoType.LLVAR.value(iic));
        message.setField(38, IsoType.NUMERIC.value(original.getApprovalCode(), 6));
        message.setField(49, IsoType.NUMERIC.value(refund.getCurrency(), 3));
        message.setField(56, IsoType.LLVAR.value("1100" +
                original.getTraceNumber() +
                original.getTransactionTime() +
                iic));
        if (StringUtils.isNotBlank(original.getReferenceId())) {
            message.setField(123, IsoType.LLLVAR.value(original.getReferenceId()));
        }
        return message;
    }

    private TransRecord toTransRecord(AuthRequest request) {
        TransRecord tr = new TransRecord();
        tr.setType(1);
        tr.setCardNo(request.getCardNo());
        tr.setAmount(Double.parseDouble(request.getAmount()));
        tr.setCurrency(request.getCurrency());
        tr.setExpiryMonth(request.getExpiryMonth());
        tr.setExpiryYear(request.getExpiryYear());
        tr.setMcc(request.getMcc());
        tr.setAcceptorId(request.getAcceptorId());
        tr.setAcceptorName(request.getAcceptorName());
        tr.setAcceptorStreet(request.getAcceptorStreet());
        tr.setAcceptorCity(request.getAcceptorCity());
        tr.setPostalCode(request.getPostalCode());
        tr.setAcceptorRegion(request.getAcceptorRegion());
        tr.setAcceptorCountry(request.getAcceptorCountry());
        tr.setOriginatorCountry(request.getOriginatorCountry());
        tr.setImportTime(LocalDateTime.now());
        tr.setSettled(0);
        tr.setUniqueId(request.getUniqueId());
        if (request.getAutoCapture() != null) {
            tr.setAutoCapture(request.getAutoCapture());
            if (request.getAutoCapture() == 1) {
                tr.setCaptured(1);
            }
        } else {
            tr.setAutoCapture(1);
            tr.setCaptured(1);
        }

        tr.setDe22("V".equals(request.getInApp()) ? de22_inApp : de22);

        //3DS
        if (request.getThreeDSecure() != null) {
            StringBuilder sb = new StringBuilder();

            String type = request.getThreeDSecure().getType();
            String eci = Integer.valueOf(request.getThreeDSecure().getEci()).toString();
            if (StringUtils.isEmpty(type)) {
                if ("4".equals(eci)) {
                    type = "3";
                } else if ("5".equals(eci) || "6".equals(eci) || "7".equals(eci) || "8".equals(eci)) {
                    type = "2";
                } else if ("0".equals(eci) || "9".equals(eci)) {
                    type = "2";
                }
            }
            String decodeCavv = Hex.encodeHexString(Base64.getDecoder().decode(request.getThreeDSecure().getCavv()));
            tr.setAuthType(type);
            tr.setEci(eci);
            tr.setThreeDSAuthResCode(decodeCavv.substring(0, 2));
            tr.setSecondFacAuthResCode(decodeCavv.substring(2, 4));
            tr.setCavvKeyIndicator(decodeCavv.substring(4, 6));
            tr.setCavv(decodeCavv.substring(6, 10));
            tr.setUnpredictableNum(decodeCavv.substring(10, 14));
            tr.setAuthTrackingNum(decodeCavv.substring(14, 30));
            tr.setCavvBase64(request.getThreeDSecure().getCavv());

            sb.append(type);
            sb.append(Integer.valueOf(eci));
            sb.append(decodeCavv, 0, 2);
            sb.append(decodeCavv, 2, 4);
            sb.append(decodeCavv, 4, 6);
            sb.append(decodeCavv, 6, 10);
            sb.append(decodeCavv, 10, 14);
            sb.append(decodeCavv, 14, 30);

            String ipAddress = request.getThreeDSecure().getIpAddress();
            if (ipAddress == null) {
                tr.setVersionAndAuthAction("00");
                tr.setIpHex("00000000");

                sb.append("00");
                sb.append("00000000");
            } else {
                tr.setVersionAndAuthAction("10");
                String[] ipArr = ipAddress.split("\\.");
                String part1 = String.format("%02x", Integer.parseInt(ipArr[0]));
                String part2 = String.format("%02x", Integer.parseInt(ipArr[1]));
                String part3 = String.format("%02x", Integer.parseInt(ipArr[2]));
                String part4 = String.format("%02x", Integer.parseInt(ipArr[3]));
                tr.setIpHex(part1 + part2 + part3 + part4);

                sb.append("10");
                sb.append(part1).append(part2).append(part3).append(part4);
            }

            request.getThreeDSecure().setFormattedDe122(sb.toString());
        }
        return tr;
    }

    private TransRecord toTransRecord(RefundRequest request, TransRecord original, String time) {
        TransRecord tr = new TransRecord();
        tr.setType(2);
        tr.setCardNo(original.getCardNo());
        tr.setAmount(Double.parseDouble(request.getAmount()));
        tr.setCurrency(request.getCurrency());
        tr.setMcc(original.getMcc());
        tr.setApprovalCode(original.getApprovalCode());
        tr.setReferenceId(original.getReferenceId());
        tr.setTransactionTime(time);
        tr.setImportTime(LocalDateTime.now());
        tr.setOriginalTraceNumber(original.getTraceNumber());
        tr.setOriginalTransactionTime(original.getTransactionTime());
        tr.setSettled(0);
        tr.setUniqueId(request.getUniqueId());
        tr.setIssuerDxs(original.getIssuerDxs());
        tr.setAutoCapture(1);
        tr.setCaptured(1);
        //3DS
        tr.setAuthType(original.getAuthType());
        tr.setEci(original.getEci());
        tr.setThreeDSAuthResCode(original.getThreeDSAuthResCode());
        tr.setSecondFacAuthResCode(original.getSecondFacAuthResCode());
        tr.setCavvKeyIndicator(original.getCavvKeyIndicator());
        tr.setCavv(original.getCavv());
        tr.setUnpredictableNum(original.getUnpredictableNum());
        tr.setAuthTrackingNum(original.getAuthTrackingNum());
        tr.setVersionAndAuthAction(original.getVersionAndAuthAction());
        tr.setIpHex(original.getIpHex());
        return tr;
    }

    private void updateRecord(TransRecord tr, ParsedMessage pm) {
        tr.setTraceNumber(pm.getTraceNumber());
        tr.setTransmissionTime(pm.getTransmissionTime());
        tr.setTransactionTime(pm.getLocalTransTime());
        tr.setApprovalCode(pm.getApprovalCode());
        tr.setActionCode(pm.getActionCode());
        tr.setReferenceId(pm.getReferenceId());
        tr.setBillingAmount(pm.getBillingAmount());
        tr.setBillingCurrency(pm.getBillingCurrency());
        if (STATUS.APPROVED.status.equals(pm.getStatus())) {
            tr.setTransStatus(1);
        } else {
            tr.setTransStatus(0);
        }
        tr.setUpdateTime(LocalDateTime.now());
        tr.setCavvValidationRes(pm.getCavvValidationResult());
    }

    private void updateOriginal(TransRecord original, TransRecord tr, ParsedMessage pm) {
        if (STATUS.APPROVED.status.equals(pm.getStatus())) {
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

            transRecordService.save(original);
        }
    }

    private Pair<ChannelPool, Channel> getChannel() {
        int size = address.getAddresses().size();
        for (int i = 0; i < size; i++) {
            InetSocketAddress isa = address.getAddresses().get(i);
            ChannelPool pool = poolMap.get(isa);
            Future<Channel> future = pool.acquire();
            try {
                Channel channel = future.get(3, TimeUnit.SECONDS);
                if (channel.isActive()) {
                    return Pair.of(pool, channel);
                } else {
                    pool.release(channel);
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
