package com.ronhan.iso8583.discover.handler;

import com.ronhan.iso8583.DateUtils;
import com.ronhan.iso8583.discover.DiscoverMti;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.TimeZone;

/**
 * @author Mloong
 * @version 0.0.1
 * <p>Discover tcp socket建立连接后发送1804 sign on请求</p>
 * @since 2021/4/23
 **/
@Slf4j
public class DiscoverHandler extends ChannelInboundHandlerAdapter {

    private MessageFactory<IsoMessage> mf;

    private String IIC;

    private String IIC_DC;

    public DiscoverHandler(String IIC, String IIC_DC, MessageFactory<IsoMessage> mf) {
        this.mf = mf;
        this.IIC = IIC;
        this.IIC_DC = IIC_DC;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接到{}", ctx.channel());
        IsoMessage message = mf.newMessage(DiscoverMti.MTI1804);

        String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));
        String d2 = DateUtils.format(DateUtils.yyMMddHHmmss);

        message.setField(7, IsoType.NUMERIC.value(d1, 10));
        message.setField(12, IsoType.NUMERIC.value(d2, 12));
        message.setField(24, IsoType.NUMERIC.value(801, 3));
        message.setField(93, IsoType.LLVAR.value(IIC_DC, 11));
        message.setField(94, IsoType.LLVAR.value(IIC, 11));

        ctx.channel().writeAndFlush(message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel inactive, {}", ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channel unregister, {}", ctx.channel());
    }
}
