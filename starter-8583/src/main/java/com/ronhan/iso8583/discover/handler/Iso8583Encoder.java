package com.ronhan.iso8583.discover.handler;

import com.ronhan.iso8583.Message;
import com.ronhan.iso8583.MessageUtil;
import com.ronhan.iso8583.Providers;
import com.solab.iso8583.IsoMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Mloong
 * @version 0.0.1
 * <p>Discover 出站消息，编码ISO8583信息</p>
 * @since 2021/5/13
 **/
@Slf4j
public class Iso8583Encoder extends MessageToByteEncoder<IsoMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, IsoMessage isoMessage, ByteBuf byteBuf) throws Exception {
        byte[] data = isoMessage.writeData();

        Message message = MessageUtil.toMap(Providers.DISCOVER, isoMessage);
        Map<Integer, String> map = message.getData();
        if (map.containsKey(2)) {
            map.put(2, map.get(2).substring(0, 6) + "****" + map.get(2).substring(map.get(2).length() - 4));
        }
        if (map.containsKey(14)) {
            map.put(14, "****");
        }
        if (map.containsKey(40)) {
            map.put(40, "***");
        }
        log.info("发送消息, data:{}, channel:{}, HEX:{}", message, ctx.channel(), Hex.encodeHexString(data));

        int len = data.length;
        byteBuf.writeBytes(String.format("%04d", len).getBytes(StandardCharsets.US_ASCII));
        byteBuf.writeBytes(data);
        byteBuf.writeByte((byte) 0x03);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("err: ", cause);
    }
}
