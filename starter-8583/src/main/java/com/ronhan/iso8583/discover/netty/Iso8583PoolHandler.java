package com.ronhan.iso8583.discover.netty;

import com.ronhan.iso8583.discover.MessagePublisher;
import com.ronhan.iso8583.discover.handler.*;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/28
 **/
@Slf4j
public class Iso8583PoolHandler implements ChannelPoolHandler {

    private String IIC;

    private String IIC_DC;

    private MessageFactory<IsoMessage> mf;

    private MessagePublisher publisher;

    public Iso8583PoolHandler(String IIC, String IIC_DC, MessageFactory<IsoMessage> mf, MessagePublisher publisher) {
        this.IIC = IIC;
        this.IIC_DC = IIC_DC;
        this.mf = mf;
        this.publisher = publisher;
    }

    @Override
    public void channelReleased(Channel channel) throws Exception {
        log.info("release channel {}", channel);
        channel.pipeline().get(Iso8583Decoder.class).setMsgListener(null);
    }

    @Override
    public void channelAcquired(Channel channel) throws Exception {
        log.info("acquire channel {}", channel);
    }

    @Override
    public void channelCreated(Channel channel) throws Exception {
        log.info("create channel {}", channel);
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new IdleStateHandler(0, 30, 0, TimeUnit.SECONDS));
        pipeline.addLast(new IdleEventHandler(IIC, IIC_DC, mf));
        pipeline.addLast(new FrameDecoder(Integer.MAX_VALUE, 0, 4));
        pipeline.addLast(new Iso8583Decoder(mf, publisher));
        pipeline.addLast(new DiscoverHandler(IIC, IIC_DC, mf));
        pipeline.addLast(new Iso8583Encoder());
    }
}
