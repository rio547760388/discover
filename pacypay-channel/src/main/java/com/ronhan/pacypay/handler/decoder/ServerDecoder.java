package com.ronhan.pacypay.handler.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/7
 **/
public class ServerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {

        int n = byteBuf.readableBytes();
        if (n < 10) {
            return;
        }
        System.out.println(n);
        byte[] buf = new byte[n];
        byteBuf.readBytes(buf);
        String msg = new String(buf);
        System.out.println(msg);
        list.add(msg.toUpperCase());
    }
}
