package com.ronhan.iso8583.discover.sftp;

import org.apache.sshd.sftp.client.SftpClient;

/**
 * @author Mloong
 * @version 0.0.1
 * @since 2021/10/21
 **/
@FunctionalInterface
public interface SftpHandler<R> {
    /**
     * sftp
     * @param sftpClient
     * @return
     */
    R handle(SftpClient sftpClient);

}
