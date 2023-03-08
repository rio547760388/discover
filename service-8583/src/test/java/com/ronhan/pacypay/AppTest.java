package com.ronhan.pacypay;


import com.ronhan.crypto.Crypto;
import com.ronhan.iso8583.DateUtils;
import com.ronhan.iso8583.Message;
import com.ronhan.iso8583.MessageUtil;
import com.ronhan.iso8583.Providers;
import com.ronhan.iso8583.discover.DiscoverMti;
import com.ronhan.iso8583.discover.handler.Iso8583Decoder;
import com.ronhan.iso8583.discover.netty.ServerAddress;
import com.ronhan.iso8583.discover.sftp.SftpUtil;
import com.ronhan.pacypay.dao.ConfirmationEntityRepository;
import com.ronhan.pacypay.parser.MessageParser;
import com.ronhan.pacypay.parser.ParsedMessage;
import com.ronhan.pacypay.pojo.FxRateQuery;
import com.ronhan.pacypay.pojo.PageResult;
import com.ronhan.pacypay.pojo.Response;
import com.ronhan.pacypay.pojo.entity.ConfirmationEntity;
import com.ronhan.pacypay.pojo.entity.FxRate;
import com.ronhan.pacypay.scheduler.DailyRecap;
import com.ronhan.pacypay.service.SettlementService;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import io.netty.channel.Channel;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
class AppTests {
    @Autowired
    ServerAddress address;

    @Autowired
    AbstractChannelPoolMap<InetSocketAddress, ChannelPool> poolMap;

    @Autowired
    MessageFactory<IsoMessage> mf;

    @Autowired
    MessageParser messageParser;

    @Autowired
    Validator validator;

    @Autowired
    Map<String, MessageParser> parserMap;

    String iic = "00000367561";

    String iic_a = "00000367265";

    String iic_b = "00000361641";


    @Test
    void testAuth() {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ChannelPool pool;

        AtomicReference<Channel> channel = new AtomicReference<>();
        AtomicReference<ParsedMessage> parsedMessage = new AtomicReference<>();

        pool = poolMap.get(address.getAddresses().get(0));

        pool.acquire().addListener(c -> {
            if (c.isSuccess()) {
                channel.set((Channel) c.get());

                IsoMessage message = mf.newMessage(DiscoverMti.MTI1100);
                String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));
                String d2 = DateUtils.format(DateUtils.yyMMddHHmmss);

                message.setField(2, IsoType.LLVAR.value("36259600500102"));
                message.setField(3, IsoType.ALPHA.value("000000", 6));
                message.setField(4, IsoType.NUMERIC.value("000000100200", 12));
                message.setField(7, IsoType.NUMERIC.value(d1, 10));
                message.setField(12, IsoType.NUMERIC.value(d2, 12));
                message.setField(14, IsoType.NUMERIC.value("2407", 4));
                message.setField(22, IsoType.ALPHA.value("000090100000", 12));
                message.setField(24, IsoType.NUMERIC.value(100, 3));
                message.setField(26, IsoType.NUMERIC.value(5311, 4));
                message.setField(32, IsoType.LLVAR.value(iic, 11));
                message.setField(33, IsoType.LLVAR.value(iic, 11));
                message.setField(40, IsoType.NUMERIC.value("086", 3));
                message.setField(42, IsoType.ALPHA.value("0083531100156", 15));
                message.setField(43, IsoType.LLVAR.value("hongxunder\\Jiasheng Garden, Cangxia New City, 169\\FUZHOU\\350000    CHN840"));
                message.setField(49, IsoType.NUMERIC.value(840, 3));
                message.setField(92, IsoType.NUMERIC.value(840, 3));


                Iso8583Decoder decoder = channel.get()
                        .pipeline()
                        .get(Iso8583Decoder.class);
                decoder.setMsgListener(l -> {
                    IsoMessage isoMsg = l.get();
                    System.out.println(isoMsg.debugString());
                    if (isoMsg.getType() == DiscoverMti.MTI1110) {
                        Message msg = MessageUtil.toMap(Providers.DISCOVER, isoMsg);
                        parsedMessage.set(parserMap.get(msg.getChannel()).parse(msg));
                        countDownLatch.countDown();
                    }
                });

                channel.get().writeAndFlush(message);
            } else {

                countDownLatch.countDown();
            }
        });

        try {
            if (!countDownLatch.await(12, TimeUnit.SECONDS)) {

            } else {
                Assertions.assertNotNull(parsedMessage.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.release(channel.get());
        }
    }


    @Test
    public void testRefund() {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ChannelPool pool;

        AtomicReference<Channel> channel = new AtomicReference<>();

        pool = poolMap.get(address.getAddresses().get(0));

        pool.acquire().addListener(c -> {
            if (c.isSuccess()) {
                channel.set((Channel) c.get());

                IsoMessage message = mf.newMessage(DiscoverMti.MTI1420);
                String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));
                String d2 = DateUtils.format(DateUtils.yyMMddHHmmss);

                message.setField(2, IsoType.LLVAR.value("6011492100005191"));
                message.setField(3, IsoType.ALPHA.value("200000", 6));
                message.setField(4, IsoType.NUMERIC.value("000000012345", 12));
                message.setField(7, IsoType.NUMERIC.value(d1, 10));
                message.setField(12, IsoType.NUMERIC.value(d2, 12));
                message.setField(24, IsoType.NUMERIC.value(400, 3));
                message.setField(26, IsoType.NUMERIC.value(5311, 4));
                message.setField(32, IsoType.LLVAR.value(iic_a, 11));
                message.setField(33, IsoType.LLVAR.value(iic_a, 11));
                message.setField(38, IsoType.ALPHA.value("70632P", 6));
                message.setField(49, IsoType.NUMERIC.value(840, 3));
                message.setField(56, IsoType.LLVAR.value("1100" + "000002" + "210625181557" + iic_a));
                message.setField(123, IsoType.LLLVAR.apply("441151425583928"));

                Iso8583Decoder decoder = channel.get()
                        .pipeline()
                        .get(Iso8583Decoder.class);
                decoder.setMsgListener(l -> {
                    IsoMessage isoMsg = l.get();
                    if (isoMsg.getType() == DiscoverMti.MTI1430) {
                        Message msg = MessageUtil.toMap(Providers.DISCOVER, isoMsg);
                        System.out.println(msg);
                        countDownLatch.countDown();
                    }
                });

                channel.get().writeAndFlush(message);
            } else {

                countDownLatch.countDown();
            }
        });

        try {
            if (!countDownLatch.await(12, TimeUnit.SECONDS)) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.release(channel.get());
        }
    }


    @Test
    public void keyExchange() {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ChannelPool pool;

        AtomicReference<Channel> channel = new AtomicReference<>();

        pool = poolMap.get(address.getAddresses().get(0));

        pool.acquire().addListener(c -> {
            if (c.isSuccess()) {
                channel.set((Channel) c.get());

                IsoMessage message = mf.newMessage(DiscoverMti.MTI1804);
                String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));
                String d2 = DateUtils.format(DateUtils.yyMMddHHmmss);

                message.setField(7, IsoType.NUMERIC.value(d1, 10));
                message.setField(11, IsoType.NUMERIC.value(d2, 12));
                message.setField(12, IsoType.NUMERIC.value(d2, 12));
                message.setField(24, IsoType.NUMERIC.value(814, 3));
                message.setField(93, IsoType.LLVAR.value("00000361589"));
                message.setField(94, IsoType.LLVAR.value(iic_b));
                message.setField(96, IsoType.LLLVAR.value("F618C7F029EF94A8D88E2EB56B7392B5"));

                Iso8583Decoder decoder = channel.get()
                        .pipeline()
                        .get(Iso8583Decoder.class);
                decoder.setMsgListener(l -> {
                    IsoMessage isoMsg = l.get();
                    System.out.println(isoMsg.debugString());
                    if (isoMsg.getType() == DiscoverMti.MTI1814 && isoMsg.getAt(24).getValue().toString().equals("814")) {
                        Message msg = MessageUtil.toMap(Providers.DISCOVER, isoMsg);
                        System.out.println(msg);
                        countDownLatch.countDown();
                    }
                });

                channel.get().writeAndFlush(message);
            } else {

                countDownLatch.countDown();
            }
        });

        try {
            if (!countDownLatch.await(12, TimeUnit.SECONDS)) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.release(channel.get());
        }
    }

    @Autowired
    DailyRecap dailyRecap;

    @Test
    public void testRecap() throws IOException {
        dailyRecap.produce();
    }

    @Test
    public void testConfirmationFile() throws IOException {
        dailyRecap.consume();
    }

    @Value("${discover.sftp.host}")
    private String host;

    @Value("${discover.sftp.port}")
    private int port;

    @Value("${discover.sftp.username}")
    private String username;

    @Value("${discover.dxs_code}")
    private String dxsCode;

    @Value("${discover.env}")
    private String env;

    @Value("${discover.sftp.pubKey}")
    private String pub;

    @Value("${discover.sftp.priKey}")
    private String pri;
    @Test
    public void testSftp() {
        List<String> s = SftpUtil.readDir(host, port, "DCINT"+username, Crypto.getPrivateKeyEntry(), ".");
        System.out.println(s);
    }

    @Autowired
    ConfirmationEntityRepository confirmationEntityRepository;
    @Test
    public void test() {
        String filename = "CRT.DCIINTO.DCOUTD6.D6ACQHO.20210906.N111948";
        ConfirmationEntity ce = new ConfirmationEntity();
        ce.setFilename(filename);
        System.out.println(confirmationEntityRepository.count(Example.of(ce, ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.EXACT))));
    }

    @Autowired
    private SettlementService settlementService;

    @Test
    public void fxRates() {
        FxRateQuery query = new FxRateQuery();
        query.setPageNum(2);
        PageResult<FxRate> res = settlementService.list(query);
        System.out.println(res);
    }
}
