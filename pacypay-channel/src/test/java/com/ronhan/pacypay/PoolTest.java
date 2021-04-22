package com.ronhan.pacypay;

import com.ronhan.pacypay.netty.ChannelPoolImpl;
import com.ronhan.pacypay.netty.ServerConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/22
 **/
public class PoolTest {
    @Test
    public void test() {
        ExecutorService es = Executors.newFixedThreadPool(5);
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPortStart(7000);
        serverConfig.setPortEnd(7000);
        serverConfig.setHost("localhost");
        serverConfig.setMin(5);
        serverConfig.setMax(10);
        ChannelPoolImpl channelPool = new ChannelPoolImpl(serverConfig);
        if (channelPool.start()) {
            System.out.println("pool start");
        }

        for (int i = 0; i < 10; i++) {
            es.submit(() -> {
                try {
                    Channel channel = channelPool.get();
                    ByteBuf buf = Unpooled.copiedBuffer(Thread.currentThread().getName() + " hello netty " + channel.id(), StandardCharsets.UTF_8);
                    channel.writeAndFlush(buf);
                    channel.read();
                    channelPool.release(channel);

                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
