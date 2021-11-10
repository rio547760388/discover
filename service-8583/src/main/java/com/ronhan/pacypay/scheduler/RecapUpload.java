package com.ronhan.pacypay.scheduler;

import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/11/8
 **/
public interface RecapUpload {
    void sendFile(String filename, List<String> content);

    void sentMail(String filename);
}
