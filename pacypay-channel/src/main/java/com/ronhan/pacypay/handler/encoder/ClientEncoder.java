package com.ronhan.pacypay.handler.encoder;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/7
 **/
public class ClientEncoder extends ChannelInboundHandlerAdapter {

    private MessageFactory<IsoMessage> messageFactory;
    public ClientEncoder(MessageFactory<IsoMessage> messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        IsoMessage isoMessage = messageFactory.newMessage(0x200);
        isoMessage.setValue(2, "333", IsoType.NUMERIC, 6);
        ctx.writeAndFlush(isoMessage);
    }

}
