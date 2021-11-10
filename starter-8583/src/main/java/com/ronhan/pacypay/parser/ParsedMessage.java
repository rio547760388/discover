package com.ronhan.pacypay.parser;

import lombok.Data;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/25
 **/
@Data
public class ParsedMessage {
    private String provider;

    private String status;

    private Double amount;

    private String currency;

    private Double billingAmount;

    private String billingCurrency;

    private String cardNo;

    private String transmissionTime;

    private String traceNumber;

    private String localTransTime;

    private String approvalCode;

    private String actionCode;

    /**
     * 1-auth, 2-refund
     */
    private String type;

    private String referenceId;

    private String code;

    private String reason;

    private String uniqueId;

    private String cavvValidationResult;

    public enum TransType {
        AUTH("1"),
        REVERSAL("2");

        private String val;
        TransType(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }
}
