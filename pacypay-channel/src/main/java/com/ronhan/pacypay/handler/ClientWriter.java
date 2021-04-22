package com.ronhan.pacypay.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/7
 **/
public class ClientWriter extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        /*ByteBuf buf = (ByteBuf) msg;
        buf.writeBytes("hello netty 1234".getBytes());*/
        ctx.writeAndFlush(msg, promise);
    }
}
