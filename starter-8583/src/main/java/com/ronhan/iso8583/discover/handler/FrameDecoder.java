package com.ronhan.iso8583.discover.handler;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * @author Mloong
 * @version 0.0.1
 * <p>Discover APDU 解包</p>
 * @since 2021/5/14
 **/
public class FrameDecoder extends LengthFieldBasedFrameDecoder {
    public FrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf.order(order);
        byte[] l = new byte[length];
        buf.getBytes(offset, l);
        String len = new String(l, StandardCharsets.US_ASCII);
        return Long.parseLong(len) + 1;
    }
}
