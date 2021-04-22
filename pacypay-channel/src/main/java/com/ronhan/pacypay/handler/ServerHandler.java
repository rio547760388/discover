package com.ronhan.pacypay.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.nio.charset.StandardCharsets;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/7
 **/
public class ServerHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        ctx.writeAndFlush(msg, promise);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("err");
        cause.printStackTrace();
        ctx.close();
    }
}
