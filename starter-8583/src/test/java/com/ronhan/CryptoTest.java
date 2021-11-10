package com.ronhan;

import com.ronhan.crypto.Crypto;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/3
 **/
public class CryptoTest {

    @Test
    public void testComponents() throws DecoderException, NoSuchAlgorithmException, InvalidKeySpecException {
        String com1 = "C8BF16523D9107949ED5EF29942AF892", com2 = "D6FD4C649804EF2F235E0D5219681073", com3 = "3BCDDA9BD6EAA2ABCE97A783C2FB4608";

        //ZMK component1
        KeySpec ks1 = new SecretKeySpec(Hex.decodeHex(com1 + com1.substring(0, 16)), "DESede");

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey sk1 = secretKeyFactory.generateSecret(ks1);

        System.out.println("component1: " + Hex.encodeHexString(sk1.getEncoded(), false));

        byte[] enc = Crypto.encrypt(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, sk1);
        Assertions.assertEquals(Hex.encodeHexString(enc, false).substring(0, 6), "5DB633");


        //ZMK component2
        KeySpec ks2 = new SecretKeySpec(Hex.decodeHex(com2 + com2.substring(0, 16)), "DESede");

        SecretKey sk2 = secretKeyFactory.generateSecret(ks2);

        System.out.println("component2: " + Hex.encodeHexString(sk2.getEncoded(), false));

        enc = Crypto.encrypt(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, sk2);
        Assertions.assertEquals(Hex.encodeHexString(enc, false).substring(0, 6), "BA6911");

        //ZMK component3
        KeySpec ks3 = new SecretKeySpec(Hex.decodeHex(com3 + com3.substring(0, 16)), "DESede");

        SecretKey sk3 = secretKeyFactory.generateSecret(ks3);

        System.out.println("component3: " + Hex.encodeHexString(sk3.getEncoded(), false));

        enc = Crypto.encrypt(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, sk3);
        Assertions.assertEquals(Hex.encodeHexString(enc, false).substring(0, 6), "A8375D");

        //ZMK
        String zmk = genZMK(com1, com2, com3);
        KeySpec ks4 = new SecretKeySpec(Hex.decodeHex(zmk + zmk.substring(0, 16)), "DESede");
        SecretKey sk4 = secretKeyFactory.generateSecret(ks4);

        System.out.println("zmk: " + Hex.encodeHexString(sk4.getEncoded(), false));

        enc = Crypto.encrypt(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, sk4);
        Assertions.assertEquals(Hex.encodeHexString(enc, false).substring(0, 6), "40546B");
    }


    String genZMK(String c1, String c2, String c3) throws DecoderException {
        byte[] b1 = Hex.decodeHex(c1);
        byte[] b2 = Hex.decodeHex(c2);
        byte[] b3 = Hex.decodeHex(c3);
        byte[] arr = new byte[16];
        for (int i = 0; i < 16; i++) {
            arr[i] = (byte) (b1[i] ^ b2[i] ^ b3[i]);
        }
        return Hex.encodeHexString(arr, false);
    }

    @Test
    public void testCurrency() {
        System.out.println(Currency.format("12.3", "156"));
    }
}
