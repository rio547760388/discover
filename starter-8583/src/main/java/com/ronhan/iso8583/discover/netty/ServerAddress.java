package com.ronhan.iso8583.discover.netty;

import lombok.Data;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/27
 **/
@Data
public class ServerAddress {
    private List<InetSocketAddress> addresses;
}
