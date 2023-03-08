package com.ronhan.pacypay.endpoint;

import com.alibaba.fastjson.JSON;
import com.ronhan.Country;
import com.ronhan.Currency;
import com.ronhan.crypto.Crypto;
import com.ronhan.iso8583.DateUtils;
import com.ronhan.iso8583.Message;
import com.ronhan.iso8583.MessageUtil;
import com.ronhan.iso8583.Providers;
import com.ronhan.iso8583.discover.DiscoverMti;
import com.ronhan.iso8583.discover.KeyExchange;
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
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
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
@RequestMapping("cert")
public class CertEndpoint {

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

    @Value("${discover.iic_dc}")
    private String iic_dc;

    @Autowired
    private TransRecordService transRecordService;

    @Autowired
    private KeyExchange keyExchange;

    @PostMapping("auth")
    public Response<ParsedMessage> auth(@Valid @RequestBody AuthRequest auth, @RequestParam(name = "index") int index) {
        //log.info("auth request: {}", JSON.toJSONString(auth));
        //convert
        auth.setAcceptorCountry(Country.convertToNum(auth.getAcceptorCountry()));
        auth.setOriginatorCountry(Country.convertToNum(auth.getOriginatorCountry()));
        auth.setCurrency(Currency.alphaToNum(auth.getCurrency()));

        TransRecord tr = toTransRecord(auth);

        Response<ParsedMessage> response = null;

        transRecordService.save(tr);

        String cardBin = auth.getCardNo().substring(0, 6);

        CycleRange cycleRange = transRecordService.getCardCycleRange(cardBin);

        if (cycleRange != null) {
            tr.setIssuerDxs(cycleRange.getDxsIIC());
        }

        AtomicReference<ParsedMessage> parsedMessage = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Pair<ChannelPool, Channel> pair = getChannel(index);
        if (pair != null) {
            Channel channel = pair.getValue();
            IsoMessage message = toIsoMessage(auth);

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

    @PostMapping("refund")
    public Response<ParsedMessage> refund(@Valid @RequestBody RefundRequest refund, @RequestParam(name = "index") int index) {
        log.info("refund request: {}", JSON.toJSONString(refund));

        //convert
        refund.setCurrency(Currency.alphaToNum(refund.getCurrency()));

        TransRecord original = transRecordService.getOriginalTrans(refund.getOriginalId());

        if (original == null) {
            return Response.error(Response.ErrorCode.UNKNOWN_TRADE, null);
        }

        String d2 = DateUtils.format(DateUtils.yyMMddHHmmss, TimeZone.getDefault());

        TransRecord tr = toTransRecord(refund, original, d2);

        transRecordService.save(tr);

        Response<ParsedMessage> response = null;
        AtomicReference<ParsedMessage> parsedMessage = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Pair<ChannelPool, Channel> pair = getChannel(index);
        if (pair != null) {
            Channel channel = pair.getValue();
            IsoMessage message = toIsoMessage(refund, original, d2);

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

    @GetMapping("signOn")
    public Response<Message> signOn(@RequestParam(name = "index") int index) {

        Response<Message> response = null;

        AtomicReference<Message> parsedMessage = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Pair<ChannelPool, Channel> pair = getChannel(index);
        if (pair != null) {
            Channel channel = pair.getValue();
            IsoMessage message = mf.newMessage(DiscoverMti.MTI1804);
            String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));
            String d2 = DateUtils.format(DateUtils.yyMMddHHmmss);

            message.setField(7, IsoType.NUMERIC.value(d1, 10));
            message.setField(12, IsoType.NUMERIC.value(d2, 12));
            message.setField(24, IsoType.NUMERIC.value(801, 3));
            message.setField(93, IsoType.LLVAR.value(iic_dc, 11));
            message.setField(94, IsoType.LLVAR.value(iic, 11));

            Iso8583Decoder decoder = channel.pipeline()
                    .get(Iso8583Decoder.class);
            decoder.setMsgListener(l -> {
                IsoMessage isoMsg = l.get();
                if (isoMsg.getType() == DiscoverMti.MTI1814 && "801".equals(isoMsg.getAt(24).toString())) {
                    Message msg = MessageUtil.toMap(Providers.DISCOVER, isoMsg);
                    parsedMessage.set(msg);
                    countDownLatch.countDown();
                }
            });

            channel.writeAndFlush(message);

            try {
                if (!countDownLatch.await(60, TimeUnit.SECONDS)) {
                    //timeout
                    response = Response.error(Response.ErrorCode.TIMEOUT, null);

                } else {
                    response = Response.ok(parsedMessage.get());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                pair.getLeft().release(channel);
            }
        } else {
            response = Response.error(Response.ErrorCode.NOT_AVAILABLE, null);
        }

        return response;
    }

    @GetMapping("echo")
    public Response<Message> echo(@RequestParam(name = "index") int index) {
        Response<Message> response = null;

        AtomicReference<Message> parsedMessage = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Pair<ChannelPool, Channel> pair = getChannel(index);
        if (pair != null) {
            Channel channel = pair.getValue();
            IsoMessage message = mf.newMessage(DiscoverMti.MTI1804);
            String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));
            String d2 = DateUtils.format(DateUtils.yyMMddHHmmss);

            message.setField(7, IsoType.NUMERIC.value(d1, 10));
            message.setField(12, IsoType.NUMERIC.value(d2, 12));
            message.setField(24, IsoType.NUMERIC.value(831, 3));
            message.setField(93, IsoType.LLVAR.value(iic_dc, 11));
            message.setField(94, IsoType.LLVAR.value(iic, 11));

            Iso8583Decoder decoder = channel.pipeline()
                    .get(Iso8583Decoder.class);
            decoder.setMsgListener(l -> {
                IsoMessage isoMsg = l.get();
                if (isoMsg.getType() == DiscoverMti.MTI1814 && "831".equals(isoMsg.getAt(24).toString())) {
                    Message msg = MessageUtil.toMap(Providers.DISCOVER, isoMsg);
                    parsedMessage.set(msg);
                    countDownLatch.countDown();
                }
            });

            channel.writeAndFlush(message);

            try {
                if (!countDownLatch.await(60, TimeUnit.SECONDS)) {
                    //timeout
                    response = Response.error(Response.ErrorCode.TIMEOUT, null);

                } else {
                    response = Response.ok(parsedMessage.get());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                pair.getLeft().release(channel);
            }
        } else {
            response = Response.error(Response.ErrorCode.NOT_AVAILABLE, null);
        }

        return response;
    }

    @GetMapping("updateZPK")
    public Response<String> updateZPK(String stan1, String stan2) {
        try {
            keyExchange.update(stan1, stan2);
            return Response.ok("success");
        } catch (DecoderException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error("updateZPK error", e);
            return Response.error(400, e.getMessage());
        }
    }

    private IsoMessage toIsoMessage(AuthRequest auth) {
        IsoMessage message = mf.newMessage(DiscoverMti.MTI1100);
        String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));

        message.setField(2, IsoType.LLVAR.value(auth.getCardNo()));
        message.setField(3, IsoType.ALPHA.value("000000", 6));
        message.setField(4, IsoType.NUMERIC.value(Currency.format(auth.getAmount(), auth.getCurrency()), 12));
        message.setField(7, IsoType.NUMERIC.value(d1, 10));
        message.setField(12, IsoType.NUMERIC.value(auth.getTransactionTime(), 12));
        message.setField(14, IsoType.NUMERIC.value(auth.getExpiryYear() + auth.getExpiryMonth(), 4));
        message.setField(22, IsoType.ALPHA.value("100090100000", 12));
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

        message.setField(2, IsoType.LLVAR.value(Crypto.decryptCardNo(original.getCardNo())));
        message.setField(3, IsoType.ALPHA.value("200000", 6));
        message.setField(4, IsoType.NUMERIC.value(Currency.format(refund.getAmount(), refund.getCurrency()), 12));
        message.setField(7, IsoType.NUMERIC.value(d1, 10));
        message.setField(12, IsoType.NUMERIC.value(time, 12));
        message.setField(24, IsoType.NUMERIC.value(Double.parseDouble(refund.getAmount()) == original.getAmount() ? 400 : 401, 3));
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
        tr.setCardNo(Crypto.encrypt(request.getCardNo()));
        tr.setAmount(Double.parseDouble(request.getAmount()));
        tr.setCurrency(request.getCurrency());
        tr.setExpiryMonth(Crypto.encrypt(request.getExpiryMonth()));
        tr.setExpiryYear(Crypto.encrypt(request.getExpiryYear()));
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

    private Pair<ChannelPool, Channel> getChannel(int i) {
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

        return null;
    }
}
