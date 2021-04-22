package com.ronhan.pacypay.handler;


import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/14
 **/
@Slf4j
public class Iso8583Decoder extends ByteToMessageDecoder {
    private MessageFactory<IsoMessage> messageFactory;

    public Iso8583Decoder(MessageFactory<IsoMessage> messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!byteBuf.isReadable()) {
            return;
        }
        byte[] buf = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buf);
        IsoMessage message = messageFactory.parseMessage(buf, 0);
        list.add(message);message.getField(3).getValue();
        System.out.println(list);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("{}", cause);
    }
}
