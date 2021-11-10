package com.ronhan.pacypay.parser;

import com.ronhan.iso8583.Message;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/25
 **/
public interface MessageParser {
    ParsedMessage parse(Message message);

    enum STATUS {
        /**
         * approved
         */
        APPROVED("APPROVED"),
        /**
         * declined
         */
        DECLINED("DECLINED"),
        /**
         * failed
         */
        FAILED("FAILED"),
        /**
         * error
         */
        ERROR("ERROR");

        STATUS(String status) {
            this.status = status;
        }
        public final String status;
    }

    enum CODE {
        /**
         * 成功
         */
        C100("100"),
        /**
         * error
         */
        C999("999")
        ;

        CODE(String c) {
            code = c;
        }
        public final String code;
    }
}
