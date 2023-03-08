package com.ronhan.pacypay.pojo;

import com.ronhan.pacypay.parser.ParsedMessage;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/27
 **/
@Data
public class Response<T> {
    private T data;
    private LocalDateTime timestamp = LocalDateTime.now();
    private int code = 0;
    private String msg;

    public static <T> Response<T> ok(T data) {
        Response<T> res = new Response<>();
        res.setCode(0);
        res.setMsg("success");
        res.setData(data);
        return res;
    }

    public static <T> Response<T> error(int code, T data) {
        Response<T> res = new Response<>();
        res.setCode(code);
        res.setMsg("error");
        res.setData(data);
        return res;
    }

    public static <T> Response<T> error(int code, String msg, T data) {
        Response<T> res = new Response<>();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    public static <T> Response<T> error(ErrorCode unknownCycleRange, T data) {
        Response<T> res = new Response<>();
        res.setCode(unknownCycleRange.code);
        res.setMsg(unknownCycleRange.msg);
        res.setData(data);
        return res;
    }

    public enum ErrorCode {
        /**
         * 请求超时
         */
        TIMEOUT(1, "timeout"),
        /**
         * 系统繁忙
         */
        SYSTEM_BUSY(2, "System busy"),
        /**
         * 卡bin not exist
         */
        UNKNOWN_CYCLE_RANGE(3, "unknown cycle range"),
        /**
         * unknown trade
         */
        UNKNOWN_TRADE(4, "unknown trade"),
        /**
         * trade cannot capture
         */
        TRADE_CANNOT_CAPTURE(5, "trade cannot capture"),
        /**
         * no available channel
         */
        NOT_AVAILABLE(6, "not available"),
        /**
         * repeat transaction
         */
        REPEAT_TRANS(7, "repeat transaction"),
        /**
         * acceptorName + acceptorStreet + acceptorCity too long
         */
        ACCEPTOR_LOCATION_TOO_LONG(8, "total length of (acceptorName, acceptorStreet, acceptorCity) must be less than 80")
        ;

        public final int code;
        public final String msg;
        ErrorCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
