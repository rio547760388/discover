package com.ronhan.pacypay.pojo;

import lombok.Data;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/12/10
 **/
@Data
public class Reconciliation {

    private Long id;

    private String acquirerDXS_IIC;

    private String acquirerISO_IIC;

    private String versionIndicator;

    private String originalFileDate;

    private String originalFileNum;

    private String createTimestamp;

    private String emptyFile;

    private String issuerDXS_IIC;

    private String issuerISO_IIC;

    private String recapNumber;

    private String currencyCode;

    private String recapDate;

    private String settleCurrencyCode;

    private String alternateCurrency;

    private String batchNumber;

    private String type;

    private String sequenceNum;

    private String cardNum;

    private String chargeDate;

    private String chargeType;

    private String typeOfCharge;

    private String referenceNum;

    private String authorizationNum;

    private String programTransRate;

    private String interchangePTFIn$;

    private String grossChargeAmount;

    private String netChargeAmount;

    private String alternateCurrGrossAmount;

    private String alternateCurrNetAmount;

    private String interchangeCommissionTransCurr;

    private String interchangeCommissionAlternateCurr;

    private String grossSettlementAmount;

    private String netSettlementAmount;

    private String interchangeCommissionSettleAmount;

    private String grossSettleAmountIn$;

    private String netSettleAmountIn$;

    private String interchangeCommissionIn$;

    private String interchangePTFInSettleCurr;

    private String priceRule;

    private String priceRuleCode;

    private String priceRuleSerialNum;

    private String settleDate;

    private String eci;

    private String cavv;

    private String networkReferenceId;

    private String atmInterchangeFeeIn$;

    private String atmSecurityFeeIn$;

    private String atmProcessFeeIn$;

    private String atmInterchangeFeeInSettleCurr;

    private String atmSecurityFeeInSettleCurr;

    private String atmProcessFeeInSettleCurr;

    private String surchargeFee;

    private String aqgeo;

    private String cardProductType;

    private String mcc;

    private String intes;

    private String merchantId;

    private String cardholderPresent;

    private String cardPresent;

    private String captureMethod;

    private String merchantGeoCode;

    private String issuerGeoCode;

    private String merchantPan;

    private String fieldName;

    private String fieldValue;

    private String suspensionCode;

    private String suspensionMessage;

    private String rejectionCode;

    private String rejectionMessage;

    private String numberOfSettlementRecaps;

    private String numberOfBatches;

    private String numberOfCharges;

    private String numberOfRejectedRecaps;

    private String numberOfSuspendedRecaps;

    private String netSettlementAmountOfFileTrailer;

    private String grossSettlementAmountOfFileTrailer;

    private String filename;

    private String uniqueId;
}
