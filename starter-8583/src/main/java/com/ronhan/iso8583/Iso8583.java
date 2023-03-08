package com.ronhan.iso8583;

import com.solab.iso8583.IsoMessage;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2022/7/29
 **/
public interface Iso8583<T extends IsoMessage, R> {
    R send(T t);
}
