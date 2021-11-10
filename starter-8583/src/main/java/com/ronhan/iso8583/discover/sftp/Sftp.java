package com.ronhan.iso8583.discover.sftp;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.SftpClientFactory;

import java.io.Closeable;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Mloong
 * @version 0.0.1
 * @since 2021/10/21
 **/
public class Sftp<T> implements Closeable {
    private SftpHandler<T> sftpHandler;

    private SshClient sshClient;
    private SftpClient sftpClient;

    private final int port;
    private final String username;
    private final String password;
    private final String priKey;
    private final String pubKey;
    private final String path;

    private Sftp(String host, int port, String username, String password, String priKey, String pubKey, String path) {
        this.port = port;
        this.username = username;
        this.password = password;
        this.priKey = priKey;
        this.pubKey = pubKey;
        this.path = path;

        init(host, this.port, this.username, this.password, this.priKey, this.pubKey);
    }

    /**
     * 任务处理器，读写文件，切换目录，列出文件等。
     *
     * @param sftpHandler
     * @return T
     */
    public Sftp<T> setHandler(SftpHandler<T> sftpHandler) {
        this.sftpHandler = sftpHandler;
        return this;
    }

    public T start() {
        return sftpHandler.handle(sftpClient);
    }

    private void init(String host, int port, String username, String password, String priKey, String pubKey) {
        sshClient = SshClient.setUpDefaultClient();

        sshClient.start();

        try {
            ClientSession session = sshClient.connect(username, host, port)
                    .verify(5000)
                    .getClientSession();
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

            sftpClient = SftpClientFactory.instance()
                    .createSftpClient(session);

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            sshClient.stop();
            try {
                sshClient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public SftpClient getSftpClient() {
        return sftpClient;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void close() throws IOException {
        try {
            sftpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sshClient.stop();
        try {
            sftpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T execute() throws IOException {
        T t = start();
        close();
        return t;
    }

    public static class Builder {
        private String host;
        private int port;
        private String username;
        private String password;
        private String priKey;
        private String pubKey;
        private String path;

        private Builder() {
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public void password(String password) {
            this.password = password;
        }

        public Builder priKey(String priKey) {
            this.priKey = priKey;
            return this;
        }

        public Builder pubKey(String pubKey) {
            this.pubKey = pubKey;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public <T> Sftp<T> build() {
            return new Sftp<T>(host, port, username, password, priKey, pubKey, path);
        }
    }

}
