package com.ronhan.iso8583;

import lombok.Data;

import java.util.TreeMap;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/21
 **/
@Data
public class Message {
    private String channel;
    private String mti;
    private TreeMap<Integer, String> data;
}
