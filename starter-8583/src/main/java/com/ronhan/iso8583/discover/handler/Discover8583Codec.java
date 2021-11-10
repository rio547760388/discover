package com.ronhan.iso8583.discover.handler;

import com.ronhan.iso8583.MessageUtil;
import com.ronhan.iso8583.Providers;
import com.ronhan.iso8583.discover.DiscoverMti;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/8
 **/
@Slf4j
public class Discover8583Codec extends MessageToMessageCodec<ByteBuf, IsoMessage> {

    private MessageFactory<IsoMessage> mf;

    private GenericFutureListener<Future<IsoMessage>> msgListener;

    public Discover8583Codec(MessageFactory<IsoMessage> mf) {
        this.mf = mf;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, IsoMessage isoMessage, List<Object> list) throws Exception {
        byte[] data = isoMessage.writeData();

        log.info("发送消息, data:{}, HEX:{}", MessageUtil.toMap(Providers.DISCOVER, isoMessage), Hex.encodeHexString(data));

        int len = data.length;
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(String.format("%04d", len).getBytes(StandardCharsets.US_ASCII));
        byteBuf.writeBytes(data);
        byteBuf.writeByte((byte) 0x03);
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!byteBuf.isReadable()) {
            return;
        }

        byte[] buf = new byte[byteBuf.readableBytes()];

        byteBuf.readBytes(buf);

        String msg = Hex.encodeHexString(buf);


        IsoMessage message = mf.parseMessage(buf, 4);
        if (message != null) {
            log.info("Discover8583Codec解码消息, data:{}, HEX:{}", MessageUtil.toMap(Providers.DISCOVER, message), msg);

            list.add(message);
            if (DiscoverMti.MTI1804 == message.getType()) {
                IsoMessage res = mf.createResponse(message);
                res.setField(33, IsoType.LLVAR.value("00000367561", 11));
                res.setField(39, IsoType.NUMERIC.value(800, 3));
                ctx.channel().writeAndFlush(res);
            } else if (DiscoverMti.MTI1814 == message.getType()) {
                String actionCode = Objects.toString(message.getAt(39).getValue(), "");
                //sign on, echo: 800  Accepted/ 909 Destination Not Known/ 910 Destination Not In Service
                if (!"800".equals(actionCode)) {
                    ctx.channel().close();
                }
            }

            Future<IsoMessage> future = ctx.executor()
                    .newSucceededFuture(message);

            if (msgListener != null) {
                future.addListener(msgListener);
            }
        } else {
            log.error("解码失败：{}", msg);
        }
    }

    public void setMsgListener(GenericFutureListener<Future<IsoMessage>> listener) {
        this.msgListener = listener;
    }
}
