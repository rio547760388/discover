package com.ronhan.pacypay.netty;

import io.netty.channel.Channel;

import java.util.concurrent.TimeoutException;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/21
 **/
public interface ChannelPool {
    /**
     *
     * @return
     */
    int status();

    Channel get() throws TimeoutException;

    boolean start();

    boolean stop();

    boolean release(Channel channel);
}
