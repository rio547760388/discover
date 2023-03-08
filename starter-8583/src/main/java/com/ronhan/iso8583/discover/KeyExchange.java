package com.ronhan.iso8583.discover;

import com.ronhan.crypto.Crypto;
import com.ronhan.iso8583.DateUtils;
import com.ronhan.iso8583.discover.handler.Iso8583Decoder;
import com.ronhan.iso8583.discover.netty.ServerAddress;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import io.netty.channel.Channel;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPool;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.net.InetSocketAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/23
 **/
@Slf4j
public class KeyExchange {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private String com1, com2, com3, checkValue1, checkValue2, checkValue3, zmkCheckValue;

    private final String LOCK = "DISCOVER_ZPK_LOCK";

    private final String KEY_VALID_CACHE = "DISCOVER_KEY_CACHE";

    private final String ZPK = "DISCOVER_ZPK";

    @Setter
    @Autowired
    private RedissonClient redisson;

    @Setter
    @Autowired
    private AbstractChannelPoolMap<InetSocketAddress, ChannelPool> poolMap;

    @Setter
    @Autowired
    private ServerAddress address;

    @Value("${discover.iic}")
    private String iic;

    @Value("${discover.iic_dc}")
    private String iic_dc;

    @Setter
    @Autowired
    private MessageFactory<IsoMessage> mf;

    public KeyExchange(String com1, String com2, String com3, String checkValue1, String checkValue2, String checkValue3, String zmkCheckValue) {
        this.com1 = com1;
        this.com2 = com2;
        this.com3 = com3;
        this.checkValue1 = checkValue1;
        this.checkValue2 = checkValue2;
        this.checkValue3 = checkValue3;
        this.zmkCheckValue = zmkCheckValue;
    }

    public void init() throws DecoderException, InvalidKeySpecException, NoSuchAlgorithmException {
        String zmk = verifyZMK();
        Object cache = redisson.getBucket(KEY_VALID_CACHE).get();

        if (cache == null || cache.toString().equals("false")) {

            RLock rLock = redisson.getLock(LOCK);
            try {
                if (rLock.tryLock(5, 60, TimeUnit.SECONDS)) {
                    cache = redisson.getBucket(KEY_VALID_CACHE).get();
                    if (cache == null || cache.toString().equals("false")) {
                        if (exchangeZPK(zmk, null, null)) {
                            redisson.getBucket(KEY_VALID_CACHE).set(true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("初始化密钥失败");
            } finally {
                rLock.unlock();
            }

        }
    }

    public void update(String stan1, String stan2) throws DecoderException, InvalidKeySpecException, NoSuchAlgorithmException {
        String zmk = verifyZMK();

        redisson.getBucket(KEY_VALID_CACHE).set(false);

        Object cache = redisson.getBucket(KEY_VALID_CACHE).get();

        if (cache == null || cache.toString().equals("false")) {

            RLock rLock = redisson.getLock(LOCK);
            try {
                if (rLock.tryLock(5, 60, TimeUnit.SECONDS)) {
                    cache = redisson.getBucket(KEY_VALID_CACHE).get();
                    if (cache == null || cache.toString().equals("false")) {
                        if (exchangeZPK(zmk, stan1, stan2)) {
                            redisson.getBucket(KEY_VALID_CACHE).set(true);
                        }
                    }
                }

                activate(stan2);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("初始化密钥失败");
            } finally {
                rLock.unlock();
            }

        }
    }

    private String genZMK(String c1, String c2, String c3) throws DecoderException {
        byte[] b1 = Hex.decodeHex(c1);
        byte[] b2 = Hex.decodeHex(c2);
        byte[] b3 = Hex.decodeHex(c3);
        byte[] arr = new byte[16];
        for (int i = 0; i < 16; i++) {
            arr[i] = (byte) (b1[i] ^ b2[i] ^ b3[i]);
        }
        return Hex.encodeHexString(arr, false);
    }

    private String verifyZMK() throws DecoderException, NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
        //ZMK component1
        KeySpec ks1 = new SecretKeySpec(Hex.decodeHex(com1 + com1.substring(0, 16)), "DESede");
        SecretKey sk1 = secretKeyFactory.generateSecret(ks1);

        byte[] enc = Crypto.encrypt(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, sk1);

        String cv = Hex.encodeHexString(enc, false).substring(0, 6);
        if (!checkValue1.equals(cv)) {
            throw new IllegalArgumentException("密钥" + com1 + "验证失败 checkValue: " + checkValue1 + ", generateCheckValue: " + cv);
        }

        //ZMK component2
        KeySpec ks2 = new SecretKeySpec(Hex.decodeHex(com2 + com2.substring(0, 16)), "DESede");
        SecretKey sk2 = secretKeyFactory.generateSecret(ks2);

        enc = Crypto.encrypt(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, sk2);

        cv = Hex.encodeHexString(enc, false).substring(0, 6);
        if (!checkValue2.equals(cv)) {
            throw new IllegalArgumentException("密钥" + com2 + "验证失败 checkValue: " + checkValue2 + ", generateCheckValue: " + cv);
        }

        //ZMK component3
        KeySpec ks3 = new SecretKeySpec(Hex.decodeHex(com3 + com3.substring(0, 16)), "DESede");
        SecretKey sk3 = secretKeyFactory.generateSecret(ks3);

        enc = Crypto.encrypt(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, sk3);

        cv = Hex.encodeHexString(enc, false).substring(0, 6);
        if (!checkValue3.equals(cv)) {
            throw new IllegalArgumentException("密钥" + com3 + "验证失败 checkValue: " + checkValue3 + ", generateCheckValue: " + cv);
        }

        //ZMK
        String zmk = genZMK(com1, com2, com3);
        KeySpec ks4 = new SecretKeySpec(Hex.decodeHex(zmk + zmk.substring(0, 16)), "DESede");
        SecretKey sk4 = secretKeyFactory.generateSecret(ks4);

        enc = Crypto.encrypt(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, sk4);

        cv = Hex.encodeHexString(enc, false).substring(0, 6);
        if (!zmkCheckValue.equals(cv)) {
            throw new IllegalArgumentException("密钥" + zmk + "验证失败 checkValue: " + zmkCheckValue + ", generateCheckValue: " + cv);
        }

        return zmk;
    }

    private String genZPK(String zmk) {
        SecretKey sk = Crypto.genSecretKey(112, Crypto.Algo.DESede);
        byte[] keyArr = sk.getEncoded();
        String zpk = Hex.encodeHexString(keyArr, false).substring(0, 32);
        return zpk;
    }

    private boolean exchangeZPK(String zmk, String stan1, String stan2) throws NoSuchAlgorithmException, DecoderException, InvalidKeySpecException {
        String zpk = genZPK(zmk);

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");

        KeySpec ks = new SecretKeySpec(Hex.decodeHex(zpk + zpk.substring(0, 16)), "DESede");
        SecretKey sk = secretKeyFactory.generateSecret(ks);

        byte[] enc = Crypto.encrypt(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, sk);

        String cv = Hex.encodeHexString(enc, false).substring(0, 4);

        //encrypt ZPK
        KeySpec zmkKs = new SecretKeySpec(Hex.decodeHex(zmk + zmk.substring(0, 16)), "DESede");
        SecretKey zmkKey = secretKeyFactory.generateSecret(zmkKs);
        Cipher cipher = null;
        String encryptedZPK = null;
        try {
            cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, zmkKey);
            encryptedZPK = Hex.encodeHexString(cipher.doFinal(Hex.decodeHex(zpk)), false);
        } catch (NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return send(encryptedZPK, cv, stan1, stan2);
    }

    private boolean send(String zpk, String cv, String stan1, String stan2) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ChannelPool pool;

        AtomicReference<Channel> channel = new AtomicReference<>();

        AtomicInteger ai = new AtomicInteger(0);

        pool = poolMap.get(address.getAddresses().get(0));

        pool.acquire().addListener(c -> {
            if (c.isSuccess()) {
                channel.set((Channel) c.get());

                IsoMessage message = mf.newMessage(DiscoverMti.MTI1804);
                String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));
                String d2 = DateUtils.format(DateUtils.yyMMddHHmmss, TimeZone.getDefault());

                message.setField(7, IsoType.NUMERIC.value(d1, 10));
                if (stan1 != null) {
                    message.setField(11, IsoType.NUMERIC.value(stan1, 6));
                }
                message.setField(12, IsoType.NUMERIC.value(d2, 12));
                message.setField(24, IsoType.NUMERIC.value(814, 3));
                message.setField(93, IsoType.LLVAR.value(iic_dc));
                message.setField(94, IsoType.LLVAR.value(iic));
                message.setField(96, IsoType.LLLVAR.value(zpk + cv));

                Iso8583Decoder decoder = channel.get()
                        .pipeline()
                        .get(Iso8583Decoder.class);
                decoder.setMsgListener(l -> {
                    IsoMessage isoMsg = l.get();
                    if (isoMsg.getType() == DiscoverMti.MTI1814 && "814".equals(isoMsg.getAt(24).getValue().toString())) {
                        if ("800".equals(isoMsg.getAt(39).getValue().toString())) {
                            //密钥发送成功
                            ai.getAndIncrement();
                            redisson.getBucket(ZPK).set(zpk);
                        }
                        countDownLatch.countDown();
                    }
                });

                channel.get().writeAndFlush(message);
            } else {
                //获取channel失败
                log.error("获取channel失败");
                countDownLatch.countDown();
            }
        });

        try {
            if (!countDownLatch.await(24, TimeUnit.SECONDS)) {
                //timeout
                log.error("密钥交换超时");
            } else {
                if (ai.get() == 1) {
                    CountDownLatch cdl = new CountDownLatch(1);
                    IsoMessage message = mf.newMessage(DiscoverMti.MTI1804);
                    String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));
                    String d2 = DateUtils.format(DateUtils.yyMMddHHmmss, TimeZone.getDefault());

                    message.setField(7, IsoType.NUMERIC.value(d1, 10));
                    if (stan2 != null) {
                        message.setField(11, IsoType.NUMERIC.value(stan2, 6));
                    }
                    message.setField(12, IsoType.NUMERIC.value(d2, 12));
                    message.setField(24, IsoType.NUMERIC.value(811, 3));
                    message.setField(93, IsoType.LLVAR.value(iic_dc));
                    message.setField(94, IsoType.LLVAR.value(iic));
                    message.setField(96, IsoType.LLLVAR.value(zpk + cv));

                    Iso8583Decoder decoder = channel.get()
                            .pipeline()
                            .get(Iso8583Decoder.class);
                    decoder.setMsgListener(l -> {
                        IsoMessage isoMsg = l.get();
                        if (isoMsg.getType() == DiscoverMti.MTI1814 && "811".equals(isoMsg.getAt(24).getValue().toString())) {
                            if ("800".equals(isoMsg.getAt(39).getValue().toString())) {
                                //密钥激活成功
                                ai.getAndIncrement();
                            }
                            cdl.countDown();
                        }
                    });

                    channel.get().writeAndFlush(message);

                    try {
                        if (!cdl.await(12, TimeUnit.SECONDS)) {
                            log.error("密钥激活超时");
                        } else {
                            if (ai.get() == 2) {
                                return true;
                            }
                        }
                    } catch (InterruptedException ie) {
                    }

                } else {
                    log.error("密钥交换失败");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.release(channel.get());
        }

        return false;
    }

    private boolean activate(String stan2) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ChannelPool pool;

        AtomicReference<Channel> channel = new AtomicReference<>();

        AtomicInteger ai = new AtomicInteger(0);

        pool = poolMap.get(address.getAddresses().get(0));

        pool.acquire().addListener(c -> {
            if (c.isSuccess()) {
                channel.set((Channel) c.get());
                CountDownLatch cdl = new CountDownLatch(1);
                IsoMessage message = mf.newMessage(DiscoverMti.MTI1804);
                String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));
                String d2 = DateUtils.format(DateUtils.yyMMddHHmmss, TimeZone.getDefault());

                message.setField(7, IsoType.NUMERIC.value(d1, 10));
                if (stan2 != null) {
                    message.setField(11, IsoType.NUMERIC.value(stan2, 6));
                }
                message.setField(12, IsoType.NUMERIC.value(d2, 12));
                message.setField(24, IsoType.NUMERIC.value(811, 3));
                message.setField(93, IsoType.LLVAR.value(iic_dc));
                message.setField(94, IsoType.LLVAR.value(iic));

                Iso8583Decoder decoder = channel.get()
                        .pipeline()
                        .get(Iso8583Decoder.class);
                decoder.setMsgListener(l -> {
                    IsoMessage isoMsg = l.get();
                    if (isoMsg.getType() == DiscoverMti.MTI1814 && "811".equals(isoMsg.getAt(24).getValue().toString())) {
                        if ("800".equals(isoMsg.getAt(39).getValue().toString())) {
                            //密钥发送成功
                            ai.getAndIncrement();
                        }
                        countDownLatch.countDown();
                    }
                });

                channel.get().writeAndFlush(message);
            } else {
                log.error("获取channel失败");
                countDownLatch.countDown();
            }
        });

        try {
            if (!countDownLatch.await(12, TimeUnit.SECONDS)) {
                log.error("密钥激活超时");
            } else {
                if (ai.get() == 1) {
                    return true;
                }
            }
        } catch (InterruptedException ie) {
        } finally {
            pool.release(channel.get());
        }
        return false;
    }
}
