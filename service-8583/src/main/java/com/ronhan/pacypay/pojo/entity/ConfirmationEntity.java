package com.ronhan.pacypay.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/8/27
 **/
@Data
@Entity
@Table(name = "CONFIRMATION_ENTITY")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  File Header 部分
     */
    @Column(name = "acquirer_dxs_iic", length = 10)
    private String acquirerDXS_IIC;

    @Column(name = "acquirer_iso_iic", length = 20)
    private String acquirerISO_IIC;

    @Column(name = "version_indicator", length = 10)
    private String versionIndicator;

    @Column(name = "original_file_date", length = 10)
    private String originalFileDate;

    @Column(name = "original_file_num", length = 10)
    private String originalFileNum;

    @Column(name = "create_timestamp", length = 20)
    private String createTimestamp;

    @Column(name = "empty_file", length = 10)
    private String emptyFile;

    /**
     * Settlement Recap Header
     */
    @Column(name = "issuer_dxs_iic", length = 10)
    private String issuerDXS_IIC;

    @Column(name = "issuer_iso_iic", length = 20)
    private String issuerISO_IIC;

    @Column(name = "recap_number", length = 10)
    private String recapNumber;

    @Column(name = "currency_code", length = 10)
    private String currencyCode;

    @Column(name = "recap_date", length = 10)
    private String recapDate;

    @Column(name = "settle_currency_code", length = 10)
    private String settleCurrencyCode;

    @Column(name = "alternate_currency", length = 10)
    private String alternateCurrency;

    /**
     * Batch Header
     */
    @Column(name = "batch_number", length = 10)
    private String batchNumber;

    /**
     * 06-charge record/ 14-Suspension Record
     */
    @Column(name = "record_type", length = 10)
    private String type;

    /**
     * Charge Record
     */
    @Column(name = "sequence_num", length = 10)
    private String sequenceNum;

    @Column(name = "card_num", length = 64)
    private String cardNum;

    @Column(name = "charge_date", length = 10)
    private String chargeDate;

    @Column(name = "charge_type", length = 10)
    private String chargeType;

    @Column(name = "type_of_charge", length = 10)
    private String typeOfCharge;

    @Column(name = "reference_num", length = 10)
    private String referenceNum;

    @Column(name = "authorization_num", length = 10)
    private String authorizationNum;

    @Column(name = "program_trans_rate", length = 10)
    private String programTransRate;

    @Column(name = "interchange_ptf_usd", length = 10)
    private String interchangePTFIn$;

    @Column(name = "gross_charge_amount", length = 20)
    private String grossChargeAmount;

    @Column(name = "net_charge_amount", length = 30)
    private String netChargeAmount;

    @Column(name = "alternate_curr_gross_amount", length = 30)
    private String alternateCurrGrossAmount;

    @Column(name = "alternate_curr_net_amount", length = 30)
    private String alternateCurrNetAmount;

    @Column(name = "interchange_commission_trans_curr", length = 30)
    private String interchangeCommissionTransCurr;

    @Column(name = "interchange_commission_alternate_curr", length = 30)
    private String interchangeCommissionAlternateCurr;

    @Column(name = "gross_settlement_amount", length = 30)
    private String grossSettlementAmount;

    @Column(name = "net_settlement_amount", length = 30)
    private String netSettlementAmount;

    @Column(name = "interchange_commission_settle_amount", length = 30)
    private String interchangeCommissionSettleAmount;

    @Column(name = "gross_settle_amount_usd", length = 30)
    private String grossSettleAmountIn$;

    @Column(name = "net_settle_amount_usd", length = 30)
    private String netSettleAmountIn$;

    @Column(name = "interchange_commission_usd", length = 30)
    private String interchangeCommissionIn$;

    @Column(name = "interchange_ptf_in_settle_curr", length = 30)
    private String interchangePTFInSettleCurr;

    @Column(name = "price_rule", length = 50)
    private String priceRule;

    @Column(name = "price_rule_code", length = 10)
    private String priceRuleCode;

    @Column(name = "price_rule_serial_num", length = 50)
    private String priceRuleSerialNum;

    @Column(name = "settle_date", length = 10)
    private String settleDate;

    @Column(name = "eci", length = 10)
    private String eci;

    @Column(name = "cavv", length = 10)
    private String cavv;

    @Column(name = "network_reference_id", length = 20)
    private String networkReferenceId;

    @Column(name = "atm_interchange_fee_usd", length = 30)
    private String atmInterchangeFeeIn$;

    @Column(name = "atm_security_usd", length = 30)
    private String atmSecurityFeeIn$;

    @Column(name = "atm_process_fee_usd", length = 30)
    private String atmProcessFeeIn$;

    @Column(name = "atm_interchange_fee_settle_curr", length = 30)
    private String atmInterchangeFeeInSettleCurr;

    @Column(name = "atm_security_fee_settle_curr", length = 30)
    private String atmSecurityFeeInSettleCurr;

    @Column(name = "atm_process_fee_settle_curr", length = 30)
    private String atmProcessFeeInSettleCurr;

    @Column(name = "surcharge_fee", length = 20)
    private String surchargeFee;

    @Column(name = "aqgeo", length = 10)
    private String aqgeo;

    @Column(name = "card_product_type", length = 10)
    private String cardProductType;

    @Column(name = "mcc", length = 10)
    private String mcc;

    @Column(name = "intes", length = 10)
    private String intes;

    @Column(name = "merchant_id", length = 20)
    private String merchantId;

    @Column(name = "cardholder_present", length = 10)
    private String cardholderPresent;

    @Column(name = "card_present", length = 10)
    private String cardPresent;

    @Column(name = "capture_method", length = 10)
    private String captureMethod;

    @Column(name = "merchant_geo_code", length = 10)
    private String merchantGeoCode;

    @Column(name = "issuer_geo_code", length = 10)
    private String issuerGeoCode;

    @Column(name = "merchant_pan", length = 30)
    private String merchantPan;

    /**
     * suspension message/rejection message
     */
    @Column(name = "filed_name", length = 32)
    private String fieldName;

    @Column(name = "field_value", length = 64)
    private String fieldValue;

    /**
     * suspension message
     */
    @Column(name = "suspension_code", length = 10)
    private String suspensionCode;

    @Column(name = "suspension_message", length = 128)
    private String suspensionMessage;

    /**
     * rejection message
     */
    @Column(name = "rejection_code", length = 2)
    private String rejectionCode;

    @Column(name = "rejection_message", length = 128)
    private String rejectionMessage;

    /**
     * File Trailer Record
     */
    @Column(name = "num_of_settle_recap", length = 10)
    private String numberOfSettlementRecaps;

    @Column(name = "num_of_batch", length = 10)
    private String numberOfBatches;

    @Column(name = "num_of_charge", length = 10)
    private String numberOfCharges;

    @Column(name = "num_of_reject", length = 10)
    private String numberOfRejectedRecaps;

    @Column(name = "num_of_suspend", length = 10)
    private String numberOfSuspendedRecaps;

    @Column(name = "net_settle_amount_file_trailer", length = 20)
    private String netSettlementAmountOfFileTrailer;

    @Column(name = "gross_settle_amount_file_trailer", length = 20)
    private String grossSettlementAmountOfFileTrailer;

    @Column(name = "filename", length = 100)
    private String filename;
}
