package com.ronhan.crypto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Enumeration;

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

    private static Key aesKey;

    private static KeyStore.PrivateKeyEntry privateKeyEntry;

    static {
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dc.ks");
            ks.load(is, "ronhan2021".toCharArray());

            Enumeration<String> alias = ks.aliases();
            for (; alias.hasMoreElements(); ) {
                System.out.println(alias.nextElement());
            }

            aesKey = ks.getKey("mykey", "ronhan2021".toCharArray());

            privateKeyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("dccert", new KeyStore.PasswordProtection("ronhan2021".toCharArray()));
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            e.printStackTrace();
        }
    }

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

    public static String encrypt(String plain, Key key) {
        return Base64.getEncoder().encodeToString(encrypt(plain.getBytes(StandardCharsets.UTF_8), key));
    }

    public static String encrypt(String plain) {
        return encrypt(plain, aesKey);
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

    public static String decrypt(String secret, Key key) {
        byte[] bytes = Base64.getDecoder().decode(secret);
        return new String(decrypt(bytes, key), StandardCharsets.UTF_8);
    }

    public static String decrypt(String secret) {
        return decrypt(secret, aesKey);
    }

    public static String decryptCardNo(String cardNo) {
        if (!StringUtils.isNumeric(cardNo)) {
            return decrypt(cardNo);
        } else {
            return cardNo;
        }
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

    public static SecretKey parseKey(String key) {
        byte[] bytes = Base64.getDecoder().decode(key);
        try {
            SecretKeySpec keySpec = new SecretKeySpec(bytes, "AES");
            return SecretKeyFactory.getInstance("AES").generateSecret(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Key getKeyFromKeyStore() {
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dc.ks");
            ks.load(is, "ronhan2021".toCharArray());

            Enumeration<String> alias = ks.aliases();
            for (; alias.hasMoreElements(); ) {
                System.out.println(alias.nextElement());
            }

            Key key = ks.getKey("mykey", "ronhan2021".toCharArray());
            return key;
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyStore.PrivateKeyEntry getPrivateKeyEntry() {
        return privateKeyEntry;
    }

}
