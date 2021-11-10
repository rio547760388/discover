package com.ronhan.crypto;

import org.apache.commons.lang3.tuple.Pair;

import javax.crypto.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/2
 **/
public class Crypto {

    public enum Algo {
        /**
         * 3DES
         */
        DESede,
        /**
         * AES
         */
        AES,
        /**
         * RSA
         */
        RSA
    }

    static int blockSize = 128;

    public static byte[] encrypt(byte[] bytes, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);

            int size = bytes.length;
            int i, j;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            for (i = 0; (j = size - i * blockSize) > blockSize; i++) {
                baos.write(cipher.update(bytes, i * blockSize, blockSize));
            }
            if (j > 0) {
                baos.write(cipher.doFinal(bytes, i * blockSize, j));
            } else {
                baos.write(cipher.doFinal());
            }

            return baos.toByteArray();

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] bytes, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);

            int size = bytes.length;
            int i, j;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            for (i = 0; (j = size - i * blockSize) > blockSize; i++) {
                baos.write(cipher.update(bytes, i * blockSize, blockSize));
            }
            if (j > 0) {
                baos.write(cipher.doFinal(bytes, i * blockSize, j));
            } else {
                baos.write(cipher.doFinal());
            }

            return baos.toByteArray();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SecretKey genSecretKey(int len, Algo algorithm) {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(algorithm.name());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert keyGenerator != null;
        keyGenerator.init(len, new SecureRandom(new byte[]{(byte) System.currentTimeMillis()}));
        SecretKey secretKey = keyGenerator.generateKey();
        String key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(key);
        return secretKey;
    }

    public static Pair<PrivateKey, PublicKey> genKeyPair(int len, Algo algorithm) {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(algorithm.name());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert keyPairGenerator != null;
        keyPairGenerator.initialize(len);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String pri = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        System.out.println(pri);
        String pub = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println(pub);
        return Pair.of(privateKey, publicKey);
    }

}
