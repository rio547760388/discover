package com.ronhan.pacypay.scheduler;

import com.ronhan.crypto.Crypto;
import com.ronhan.iso8583.discover.sftp.SftpUtil;
import com.ronhan.pacypay.constants.ProgramEnvConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/11/8
 **/
@Component
@Slf4j
public class RecapUploadImpl implements RecapUpload {

    @Value("${discover.sftp.host}")
    private String host;

    @Value("${discover.sftp.port}")
    private int port;

    @Value("${discover.sftp.username}")
    private String username;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username:}")
    private String account;

    @Value(("${warn.receiver:}"))
    private String[] receiver;

    @Value("${spring.profiles.active}")
    private String env;

    @Override
    @Retryable(include = {RuntimeException.class}, backoff = @Backoff(delay = 60000))
    public void sendFile(String filename, List<String> content) {
        log.info("上传interchange file {}", filename);
        try {
            SftpUtil.write(host, port, "DCINT" + username, Crypto.getPrivateKeyEntry(), filename, content);
            SftpUtil.write(host, port, "DCINT" + username, Crypto.getPrivateKeyEntry(), filename + ".SENT", Collections.EMPTY_LIST);

            checkFileHash(filename);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException("上传失败");
        }
    }

    @Override
    public void sentMail(String filename) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setFrom(account);
            helper.setTo(receiver);
            helper.setSubject("Discover文件上传失败");
            helper.setText("Discover文件上传失败（" + filename+ "）,环境" + env, false);

            javaMailSender.send(mimeMessage);
            log.info("邮件发送成功{}", Arrays.deepToString(receiver));
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private void checkFileHash(String filename) {
        try {
            String dir = ProgramEnvConstant.baseDir;
            String local = FileUtils.readFileToString(new File(dir + File.separator + "OUT" + File.separator + filename));
            String remote = SftpUtil.read(host, port, "DCINT" + username, Crypto.getPrivateKeyEntry(), filename);
            if (!StringUtils.equals(checksum(local), checksum(remote))) {
                log.info("{}文件与本地不一致", filename);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String checksum(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return Hex.encodeHexString(md.digest(content.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
