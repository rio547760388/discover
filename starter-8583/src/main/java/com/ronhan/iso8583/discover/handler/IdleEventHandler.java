package com.ronhan.iso8583.discover.handler;

import com.ronhan.iso8583.DateUtils;
import com.ronhan.iso8583.discover.DiscoverMti;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.TimeZone;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/18
 **/
@Slf4j
public class IdleEventHandler extends ChannelInboundHandlerAdapter {

    private String IIC;
    private String IIC_DC;
    private MessageFactory<IsoMessage> mf;

    public IdleEventHandler(String IIC, String IIC_DC, MessageFactory<IsoMessage> mf) {
        this.IIC = IIC;
        this.IIC_DC = IIC_DC;
        this.mf = mf;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                log.info("发送心跳信息");

                IsoMessage message = mf.newMessage(DiscoverMti.MTI1804);

                String d1 = DateUtils.format(DateUtils.MMddHHmmss, TimeZone.getTimeZone("UTC"));
                String d2 = DateUtils.format(DateUtils.yyMMddHHmmss);

                message.setField(7, IsoType.NUMERIC.value(d1, 10));
                message.setField(12, IsoType.NUMERIC.value(d2, 12));
                message.setField(24, IsoType.NUMERIC.value(831, 3));
                message.setField(93, IsoType.LLVAR.value(IIC_DC, 11));
                message.setField(94, IsoType.LLVAR.value(IIC, 11));

                ctx.channel().writeAndFlush(message);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
