package com.ronhan.iso8583.discover.handler;

import com.ronhan.iso8583.MessageUtil;
import com.ronhan.iso8583.Providers;
import com.ronhan.iso8583.discover.DiscoverMti;
import com.ronhan.iso8583.discover.MessagePublisher;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.util.List;
import java.util.Objects;

/**
 * @author Mloong
 * @version 0.0.1
 * <p>Discover 入站消息解码成ISO8583 IsoMessage</p>
 * @since 2021/5/14
 **/
@Slf4j
public class Iso8583Decoder extends ByteToMessageDecoder {

    private MessageFactory<IsoMessage> mf;

    private MessagePublisher publisher;

    private GenericFutureListener<Future<IsoMessage>> msgListener;

    final String ECHO_STATUS = "800";

    public Iso8583Decoder(MessageFactory<IsoMessage> mf, MessagePublisher publisher) {
        this.mf = mf;
        this.publisher = publisher;
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
            log.info("解码消息, data:{}, channel: {}, HEX:{}", MessageUtil.toMap(Providers.DISCOVER, message), ctx.channel(), msg);

            list.add(message);
            if (DiscoverMti.MTI1804 == message.getType()) {
                IsoMessage res = mf.createResponse(message);
                res.setField(24, null);
                res.setField(39, IsoType.NUMERIC.value(800, 3));
                ctx.channel().writeAndFlush(res);
                return;
            } else if (DiscoverMti.MTI1814 == message.getType()) {
                String actionCode = Objects.toString(message.getAt(39).getValue(), "");
                //sign on, echo: 800  Accepted/ 909 Destination Not Known/ 910 Destination Not In Service
                if (!ECHO_STATUS.equals(actionCode)) {
                    log.error("echo test fail");
                }
            } else if (DiscoverMti.MTI1304 == message.getType()) {
                return;
            }

            Future<IsoMessage> future = ctx.executor()
                    .newSucceededFuture(message);
            if (msgListener != null) {
                future.addListener(msgListener);
            }

            future.addListener(f -> {
                publisher.sendMsg(Providers.DISCOVER, message);
            });
        } else {
            log.error("解码失败：{}", msg);
        }
    }

    public void setMsgListener(GenericFutureListener<Future<IsoMessage>> listener) {
        this.msgListener = listener;
    }

}
