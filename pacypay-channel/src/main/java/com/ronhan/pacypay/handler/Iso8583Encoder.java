package com.ronhan.pacypay.handler;


import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/14
 **/
@Slf4j
public class Iso8583Encoder extends MessageToByteEncoder<IsoMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, IsoMessage isoMessage, ByteBuf byteBuf) throws Exception {
        isoMessage.setValue(2, "123", IsoType.NUMERIC, 10);
        byteBuf.writeBytes(isoMessage.writeData());
    }
}
