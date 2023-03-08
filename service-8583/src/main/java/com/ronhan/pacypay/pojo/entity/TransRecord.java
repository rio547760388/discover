package com.ronhan.pacypay.pojo.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/29
 **/
@Data
@Entity
@Table(name = "TRANS_RECORD")
public class TransRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 类型：1-auth，2-reversal
     */
    @Column(name = "TRANS_TYPE", length = 2)
    private Integer type;

    /**
     * 卡号
     */
    @Column(name = "CARDNO", length = 64)
    private String cardNo;

    /**
     * 交易金额
     */
    @Column(name = "TRANS_AMOUNT", precision = 16, scale = 4)
    private Double amount;

    /**
     * 交易币种
     */
    @Column(name = "TRANS_CURRENCY", length = 3)
    private String currency;

    /**
     * 卡过期月份
     */
    @Column(name = "EXPIRY_MONTH", length = 64)
    private String expiryMonth;

    /**
     * 卡过期年
     */
    @Column(name = "EXPIRY_YEAR", length = 64)
    private String expiryYear;

    /**
     * mcc
     */
    @Column(name = "MERCHANT_CATEGORY_CODE", length = 10)
    private String mcc;

    /**
     * 唯一标识，类似商户号
     */
    @Column(name = "ACCEPTOR_ID", length = 15)
    private String acceptorId;

    /**
     * 商户名称，auth必填
     */
    @Column(name = "ACCEPTOR_NAME")
    private String acceptorName;

    /**
     * 商户地址
     */
    @Column(name = "ACCEPTOR_STREET")
    private String acceptorStreet;

    /**
     * 商户城市，auth必填
     */
    @Column(name = "ACCEPTOR_CITY")
    private String acceptorCity;

    /**
     * 商户邮编
     */
    @Column(name = "POSTAL_CODE", length = 10)
    private String postalCode;

    /**
     * 地区
     */
    @Column(name = "ACCEPTOR_REGION", length = 3)
    private String acceptorRegion;

    /**
     * 商户国家
     */
    @Column(name = "ACCEPTOR_COUNTRY", length = 3)
    private String acceptorCountry;

    /**
     * 交易发生的国家
     */
    @Column(name = "ORIGINATOR_COUNTRY", length = 3)
    private String originatorCountry;

    /**
     * DE11
     */
    @Column(name = "TRACE_NUMBER", length = 6)
    private String traceNumber;

    /**
     * 发生discover时间，UTC
     */
    @Column(name = "TRANSMISSION_TIME", length = 10)
    private String transmissionTime;

    /**
     * 交易时间
     */
    @Column(name = "TRANSACTION_TIME", length = 12)
    private String transactionTime;

    /**
     * 授权码
     */
    @Column(name = "APPROVAL_CODE", length = 6)
    private String approvalCode;

    /**
     * 相应码
     */
    @Column(name = "ACTION_CODE", length = 3)
    private String actionCode;

    /**
     * DCI referenceID
     */
    @Column(name = "REFERENCE_ID", length = 32)
    private String referenceId;

    /**
     * 账单金额
     */
    @Column(name = "BILLING_AMOUNT", precision = 16, scale = 4)
    private Double billingAmount;

    /**
     * 账单币种
     */
    @Column(name = "BILLING_CURRENCY", length = 3)
    private String billingCurrency;

    /**
     * 交易状态，0-失败，1-成功，2-退款
     */
    @Column(name = "TRANS_STATUS", length = 2)
    private Integer transStatus;

    /**
     * 入库时间
     */
    @Column(name = "IMPORT_TIME")
    private LocalDateTime importTime;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 0-未结算，1-已结算
     */
    @Column(name = "SETTLED", length = 2)
    private Integer settled;

    /**
     * 退款使用，原始auth traceNumber
     */
    @Column(name = "ORIGINAL_TRACE_NUMBER", length = 6)
    private String originalTraceNumber;

    /**
     * 退款使用，原始auth transactionTime
     */
    @Column(name = "ORIGINAL_TRANS_TIME", length = 12)
    private String originalTransactionTime;

    /**
     * 发送交易机构识别码
     */
    @Column(name = "IIC", length = 20)
    private String iic;
/*
    *//**
     * discover charge_type,行业分类
     */
    @Column(name = "CHARGE_TYPE", length = 10)
    private String chargeType;

    @Column(name = "INTES", length = 10)
    private String intes;

    /**
     * 上游系统订单号
     */
    @Column(name = "UNIQUE_ID", length = 50)
    private String uniqueId;

    @Column(name = "ECI", length = 10)
    private String eci;

    /**
     * cavv HEX 7-10
     */
    @Column(name = "CAVV", length = 10)
    private String cavv;

    @Column(name = "ISSUER_DXS", length = 10)
    private String issuerDxs;

    /**
     * 0-手工capture，1-自动capture
     */
    @Column(name = "AUTO_CAPTURE")
    private Integer autoCapture;

    /**
     * 0-未capture，1-已capture
     */
    @Column(name = "CAPTURED")
    private Integer captured;

    /**
     * cavv base64格式
     */
    @Column(name = "CAVVBASE64", length = 50)
    private String cavvBase64;

    /**
     * 2-ProtectBuy
     */
    @Column(name = "ATUH_TYPE", length = 10)
    private String authType;

    /**
     * cavv HEX 1-2
     */
    @Column(name = "THREE_DS_AUTH_RES_CODE", length = 10)
    private String threeDSAuthResCode;

    /**
     * cavv HEX 3-4
     */
    @Column(name = "SECOND_FAC_AUTH_RES_CODE", length = 10)
    private String secondFacAuthResCode;

    /**
     * cavv HEX 5-6
     */
    @Column(name = "CAVV_KEY_INDICATOR", length = 10)
    private String cavvKeyIndicator;

    /**
     * cavv HEX 11-14
     */
    @Column(name = "UNPREDICTABLE_NUM", length = 10)
    private String unpredictableNum;

    /**
     * cavv HEX 15-30
     */
    @Column(name = "AUTH_TRACKING_NUM", length = 20)
    private String authTrackingNum;

    /**
     * First Digit:
     * • 0 - Authentication action and
     * Cardholder IP address not
     * present.
     * • 1 - Authentication action and
     * Cardholder IP address present.
     * Second Digit:
     * • 0 - Standard authentication
     * performed (no ADS or FYP
     * performed).
     * • 1 - ADS-registration
     * authentication performed.
     * • 2 - Forgot your password
     * (FYP)-re-registration/re-authoriz
     * ation performed.
     */
    @Column(name = "VERSION_AND_AUTH_ACTION", length = 10)
    private String versionAndAuthAction;

    /**
     * 客户ip 16进制
     */
    @Column(name = "IP_HEX", length = 10)
    private String ipHex;

    /**
     * CAVV Validation Result
     * From authorization DE 44 Position 6-7 as provided in
     * the authorization message
     * 01=CAVV passed verification- authentication
     * 02=CAVV failed verification -authentication
     * 03=CAVV passed validation—attempt
     * 04=CAVV failed validation—attempt
     * 05=CAVV not validated, issuer not participating in
     * CAVV validation
     * 06=CAVV Unable to perform authentication
     */
    @Column(name = "CAVV_VALIDATION_RES", length = 10)
    private String cavvValidationRes;

    /**
     * auth DE22
     */
    @Column(name = "de22", length = 20)
    private String de22;
}
