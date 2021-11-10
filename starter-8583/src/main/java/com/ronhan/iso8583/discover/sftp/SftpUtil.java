package com.ronhan.iso8583.discover.sftp;

import org.apache.commons.io.IOUtils;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.SftpClientFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/9
 **/
public class SftpUtil {

    public final static SftpClient.OpenMode[] openModes = new SftpClient.OpenMode[]{SftpClient.OpenMode.Read, SftpClient.OpenMode.Create, SftpClient.OpenMode.Write};


    public static String read(String host, int port, String username, String password, String priKey, String pubKey, String keyPass, String path) {
        SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.start();

        try (ClientSession session = sshClient.connect(username, host, port)
                .verify(5000)
                .getClientSession()) {
            if (password != null) {
                session.addPasswordIdentity(password);
            }
            if (priKey != null && pubKey != null) {

                PKCS8EncodedKeySpec peks = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(priKey));
                X509EncodedKeySpec xks = new X509EncodedKeySpec(Base64.getDecoder().decode(pubKey));
                KeyFactory kf = KeyFactory.getInstance("RSA");
                PrivateKey prik = kf.generatePrivate(peks);
                PublicKey pubk = kf.generatePublic(xks);
                KeyPair kp = new KeyPair(pubk, prik);
                session.addPublicKeyIdentity(kp);
            }

            session.auth().verify(30000);

            try (SftpClient sftpClient = SftpClientFactory.instance().createSftpClient(session)) {
                try (InputStream is = sftpClient.read(path)) {

                    return IOUtils.toString(is);
                }
            }

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            sshClient.stop();
            try {
                sshClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void download(String host, int port, String username, String password, String priKey, String pubKey, String keyPass, List<String> files, String outputDir) {
        SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.start();

        try (ClientSession session = sshClient.connect(username, host, port)
                .verify(5000)
                .getClientSession()) {
            if (password != null) {
                session.addPasswordIdentity(password);
            }
            if (priKey != null && pubKey != null) {

                PKCS8EncodedKeySpec peks = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(priKey));
                X509EncodedKeySpec xks = new X509EncodedKeySpec(Base64.getDecoder().decode(pubKey));
                KeyFactory kf = KeyFactory.getInstance("RSA");
                PrivateKey prik = kf.generatePrivate(peks);
                PublicKey pubk = kf.generatePublic(xks);
                KeyPair kp = new KeyPair(pubk, prik);
                session.addPublicKeyIdentity(kp);
            }

            session.auth().verify(30000);

            try (SftpClient sftpClient = SftpClientFactory.instance().createSftpClient(session)) {
                files.forEach(name -> {
                    try (InputStream is = sftpClient.read(name); OutputStream os = new FileOutputStream(new File(outputDir, name))) {
                        IOUtils.copy(is, os);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            sshClient.stop();
            try {
                sshClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<String> readDir(String host, int port, String username, String password, String priKey, String pubKey, String keyPass, String path) {
        SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.start();

        try (ClientSession session = sshClient.connect(username, host, port)
                .verify(5000)
                .getClientSession()) {
            if (password != null) {
                session.addPasswordIdentity(password);
            }
            if (priKey != null && pubKey != null) {

                PKCS8EncodedKeySpec peks = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(priKey));
                X509EncodedKeySpec xks = new X509EncodedKeySpec(Base64.getDecoder().decode(pubKey));
                KeyFactory kf = KeyFactory.getInstance("RSA");
                PrivateKey prik = kf.generatePrivate(peks);
                PublicKey pubk = kf.generatePublic(xks);
                KeyPair kp = new KeyPair(pubk, prik);
                session.addPublicKeyIdentity(kp);
            }

            session.auth().verify(30000);

            try (SftpClient sftpClient = SftpClientFactory.instance().createSftpClient(session)) {
                Iterable<SftpClient.DirEntry> des = sftpClient.readDir(path);
                return StreamSupport.stream(des.spliterator(), false)
                        .map(SftpClient.DirEntry::getFilename)
                        .filter(s -> !".".equals(s) && !"..".equals(s))
                        .collect(Collectors.toList());
            }

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            sshClient.stop();
            try {
                sshClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void write(String host, int port, String username, String password, String priKey, String pubKey, String keyPass, String filename, List<String> content) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.start();

        try (ClientSession session = sshClient.connect(username, host, port)
                .verify(5000)
                .getClientSession()) {

            if (password != null) {
                session.addPasswordIdentity(password);
            }
            if (priKey != null && pubKey != null) {

                PKCS8EncodedKeySpec peks = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(priKey));
                X509EncodedKeySpec xks = new X509EncodedKeySpec(Base64.getDecoder().decode(pubKey));
                KeyFactory kf = KeyFactory.getInstance("RSA");
                PrivateKey prik = kf.generatePrivate(peks);
                PublicKey pubk = kf.generatePublic(xks);
                KeyPair kp = new KeyPair(pubk, prik);
                session.addPublicKeyIdentity(kp);
            }

            session.auth().verify(30000);

            try (SftpClient sftpClient = SftpClientFactory.instance().createSftpClient(session)) {
                try (FileChannel fc = sftpClient.openRemoteFileChannel(filename, openModes)) {
                    ByteBuffer bf = ByteBuffer.allocate(1024);
                    for (String str : content) {
                        byte[] bytes = str.getBytes(StandardCharsets.US_ASCII);
                        if (bytes.length > 1024) {
                            int start = 0;
                            while (start < bytes.length) {
                                int len = Math.min(1024, bytes.length - start);
                                bf.put(bytes, start, len);
                                bf.flip();
                                fc.write(bf);
                                bf.clear();
                                start += len;
                            }

                        } else {
                            bf.put(bytes);
                            bf.flip();
                            fc.write(bf);
                            bf.clear();
                        }
                        bf.put(System.lineSeparator().getBytes(StandardCharsets.US_ASCII));
                        bf.flip();
                        fc.write(bf);
                        bf.clear();
                    }
                }
            }

        } finally {
            sshClient.stop();
            try {
                sshClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
