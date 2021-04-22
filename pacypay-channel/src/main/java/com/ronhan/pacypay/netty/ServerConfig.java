package com.ronhan.pacypay.netty;

import lombok.Data;

import java.util.Random;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/22
 **/
@Data
public class ServerConfig {
    private String host;

    private int portStart;

    private int portEnd;

    private int min;

    private int max;

    public int getPort() {
        if (portEnd - portStart <= 0) {
            return portStart;
        }
        Random random = new Random();
        int offset = random.nextInt(portEnd - portStart);
        return portStart + offset;
    }

}
