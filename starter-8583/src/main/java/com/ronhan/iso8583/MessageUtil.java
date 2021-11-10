package com.ronhan.iso8583;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoValue;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/21
 **/
public class MessageUtil {
    public static Message toMap(String channel, IsoMessage message) {
        Message msg = new Message();

        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 2; i <= 128; i++) {
            IsoValue isoValue = message.getAt(i);
            if (isoValue != null) {
                map.put(i, isoValue.toString());
            }
        }

        msg.setChannel(channel);
        msg.setMti(Integer.toHexString(message.getType()));
        msg.setData(map);

        return msg;
    }

}
