package com.ronhan.iso8583.discover.sftp.files;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/29
 **/
public class DisputeAndFee {

    public static class FileHeaderRecord {
        /**
         * 01
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getParticipantDxsCode() {
            return data.substring(2, 5);
        }

        public String getParticipantIIC() {
            return data.substring(5, 16);
        }

        public String getVersion() {
            return data.substring(16, 21);
        }

        public String getHandoffDate() {
            return data.substring(21, 29);
        }

        public String getHandoffFileNumber() {
            return data.substring(29, 32);
        }

        public String getTimestamp() {
            return data.substring(32, 46);
        }

        public String getEmptyFileIndicator() {
            return data.substring(46, 47);
        }

        public String getFiller() {
            return data.substring(47, 800);
        }
    }

    public static class FeeHeaderRecord {
        /**
         * 24
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getSourceDxsCode() {
            return data.substring(2, 5);
        }

        public String getSourceIIC() {
            return data.substring(5, 16);
        }

        public String getDestinationDxsCode() {
            return data.substring(16, 19);
        }

        public String getDestinationIIC() {
            return data.substring(19, 30);
        }

        public String getRecapNumber() {
            return data.substring(30, 33);
        }

        public String getCurrencyCode() {
            return data.substring(33, 36);
        }

        public String getRecapDate() {
            return data.substring(36, 44);
        }

        public String getSettlementCurrencyCode() {
            return data.substring(44, 47);
        }

        public String getFiller() {
            return data.substring(47, 800);
        }
    }

    public static class FeeDetailRecord {
        /**
         * 26
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getReserved() {
            return data.substring(2, 5);
        }

        public String getFeeDate() {
            return data.substring(5, 13);
        }

        public String getTypeOfCharge() {
            return data.substring(13, 15);
        }

        public String getClearingReferenceNumber() {
            return data.substring(15, 23);
        }

        public String getXchangeReferenceNumber() {
            return data.substring(23, 35);
        }

        public String getProgramTransactionAmount() {
            return data.substring(35, 41);
        }

        public String getGrossFeeAmount() {
            return data.substring(41, 57);
        }

        public String getNetFeeAmount() {
            return data.substring(57, 73);
        }

        public String getInterchangeFeeCommissionInTransactionCurrency() {
            return data.substring(73, 89);
        }

        public String getGrossFeeSettlementAmount() {
            return data.substring(89, 105);
        }

        public String getNetFeeSettlementAmount() {
            return data.substring(105, 121);
        }

        public String getInterchangeFeeCommissionInSettlementCurrency() {
            return data.substring(121, 137);
        }

        public String getGrossFeeSettlementAmountIn$() {
            return data.substring(137, 153);
        }

        public String getNetFeeSettlementAmountIn$() {
            return data.substring(153, 169);
        }

        public String getInterchangeFeeCommissionIn$() {
            return data.substring(169, 185);
        }

        public String getSettlementDate() {
            return data.substring(185, 199);
        }

        public String getSettlementIndicator() {
            return data.substring(199, 200);
        }

        public String getFeeDescription() {
            return data.substring(200, 260);
        }

        public String getRuleSN() {
            return data.substring(260, 296);
        }

        public String getRuleDescription() {
            return data.substring(296, 334);
        }

        public String getVolume() {
            return data.substring(334, 350);
        }

        public String getTransactionCount() {
            return data.substring(350, 358);
        }

        public String getFeeBasis() {
            return data.substring(358, 359);
        }

        public String getRateAmount() {
            return data.substring(359, 367);
        }

        public String getSequenceNumber() {
            return data.substring(367, 372);
        }

        public String getFiller() {
            return data.substring(372, 800);
        }

    }

    public static class FeeTrailerRecord {
        /**
         * 28
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getSourceDxsCode() {
            return data.substring(2, 5);
        }

        public String getSourceIIC() {
            return data.substring(5, 16);
        }

        public String getDestinationDxsCode() {
            return data.substring(16, 19);
        }

        public String getDestinationIIC() {
            return data.substring(19, 30);
        }

        public String getRecapNumber() {
            return data.substring(30, 33);
        }

        public String getCurrencyCode() {
            return data.substring(33, 36);
        }

        public String getNumberOfFeeDetailRecords() {
            return data.substring(36, 44);
        }

        public String getSummationOfGrossFeeAmounts() {
            return data.substring(44, 62);
        }

        public String getFxRate() {
            return data.substring(62, 78);
        }

        public String getDebitCreditIndicator() {
            return data.substring(78, 79);
        }

        public String getFiller() {
            return data.substring(79, 800);
        }
    }

    public static class DisputeHeaderRecord {
        /**
         * 34
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getSourceDxsCode() {
            return data.substring(2, 5);
        }

        public String getSourceIIC() {
            return data.substring(5, 16);
        }

        public String getDestinationDxsCode() {
            return data.substring(16, 19);
        }

        public String getDestinationIIC() {
            return data.substring(19, 30);
        }

        public String getRecapNumber() {
            return data.substring(30, 33);
        }

        public String getCurrencyCode() {
            return data.substring(33, 36);
        }

        public String getRecapDate() {
            return data.substring(36, 44);
        }

        public String getSettlementCurrencyCode() {
            return data.substring(44, 47);
        }

        public String getFiller() {
            return data.substring(47, 800);
        }
    }

    public static class DisputeDetailRecord {
        /**
         * 36
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getSequenceNumber() {
            return data.substring(2, 5);
        }

        public String getDisputeDate() {
            return data.substring(5, 13);
        }

        public String getTypeOfCharge() {
            return data.substring(13, 15);
        }

        public String getClearingReferenceNumber() {
            return data.substring(15, 23);
        }

        public String getXchangeReferenceNumber() {
            return data.substring(23, 35);
        }

        public String getProgramTransactionAmount() {
            return data.substring(35, 41);
        }

        public String getGrossDisputeAmount() {
            return data.substring(41, 57);
        }

        public String getNetDisputeAmount() {
            return data.substring(57, 73);
        }

        public String getInterchangeFeeCommissionInTransactionCurrency() {
            return data.substring(73, 89);
        }

        public String getGrossDisputeSettlementAmount() {
            return data.substring(89, 105);
        }

        public String getNetDisputeSettlementAmount() {
            return data.substring(105, 121);
        }

        public String getInterchangeFeeCommissionInSettlementCurrency() {
            return data.substring(121, 137);
        }

        public String getGrossDisputeSettlementAmountIn$() {
            return data.substring(137, 153);
        }

        public String getNetDisputeSettlementAmountIn$() {
            return data.substring(153, 169);
        }

        public String getInterchangeFeeCommissionIn$() {
            return data.substring(169, 185);
        }

        public String getInterchangeProcessingDate() {
            return data.substring(185, 199);
        }

        public String getStageCreationDate() {
            return data.substring(199, 213);
        }

        public String getReasonCode() {
            return data.substring(213, 217);
        }

        public String getSettlementDate() {
            return data.substring(217, 231);
        }

        public String getDocumentAttachedIndicator() {
            return data.substring(231, 232);
        }

        public String getDocumentCode() {
            return data.substring(232, 332);
        }

        public String getMethodSent() {
            return data.substring(332, 432);
        }

        public String getCreditDebitIndicator() {
            return data.substring(432, 433);
        }

        public String getDisputeStage() {
            return data.substring(447, 451);
        }

        public String getDisputeStatus() {
            return data.substring(451, 455);
        }

        public String getLiableFranchise() {
            return data.substring(455, 463);
        }
    }

    public static class OriginalInterchangeDetailRecord {
        /**
         * 38
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getCardNumber() {
            return data.substring(2, 21);
        }

        public String getChargeDate() {
            return data.substring(21, 27);
        }

        public String getChargeType() {
            return data.substring(27, 30);
        }

        public String getReferenceNumber() {
            return data.substring(30, 38);
        }

        public String getAuthorizationNumber() {
            return data.substring(38, 44);
        }

        public String getInterchangePTFIn$() {
            return data.substring(44, 50);
        }

        public String getGrossChargeAmount() {
            return data.substring(50, 66);
        }

        public String getGrossChargeCurrencyCode() {
            return data.substring(66, 69);
        }

        public String getNetChargeAmount() {
            return data.substring(69, 89);
        }

        public String getInterchangeCommissionInTransactionCurrency() {
            return data.substring(89, 109);
        }

        public String getGrossSettlementAmount() {
            return data.substring(109, 129);
        }

        public String getGrossSettlementCurrencyCode() {
            return data.substring(129, 132);
        }

        public String getNetSettlementAmount() {
            return data.substring(132, 152);
        }

        public String getInterchangeCommissionSettlementAmount() {
            return data.substring(152, 172);
        }

        public String getGrossSettlementAmountIn$() {
            return data.substring(172, 192);
        }

        public String getNetSettlementAmountIn$() {
            return data.substring(192, 212);
        }

        public String getInterchangeCommissionIn$() {
            return data.substring(212, 232);
        }

        public String getInterchangePTFInSettlementCurrency() {
            return data.substring(232, 252);
        }

        public String getATMInterchangeFeeIn$() {
            return data.substring(252, 272);
        }

        public String getATMSwitchFeeIn$() {
            return data.substring(272, 292);
        }

        public String getATMNetworkAssessmentIn$() {
            return data.substring(292, 312);
        }

        public String getATMNetworkInternationalProcessingFeeIn$() {
            return data.substring(312, 332);
        }

        public String getPOSInternationalProcessingFeeIn$() {
            return data.substring(332, 352);
        }

        public String getPOSAuthorizationFeeIn$() {
            return data.substring(352, 372);
        }

        public String getPOSNetworkAssessmentIn$() {
            return data.substring(372, 392);
        }

        public String getATMInterchangeFeeInSettlementCurrency() {
            return data.substring(392, 412);
        }

        public String getATMSwitchFeeInSettlementCurrency() {
            return data.substring(412, 432);
        }

        public String getATMNetworkAssessmentInSettlementCurrency() {
            return data.substring(432, 452);
        }

        public String getATMNetworkInternationalProcessingFeeInSettlementCurrency() {
            return data.substring(452, 472);
        }

        public String getPOSInternationalProcessingFeeInSettlementCurrency() {
            return data.substring(472, 492);
        }

        public String getPOSAuthorizationFeeInSettlementCurrency() {
            return data.substring(492, 512);
        }

        public String getPOSNetworkAssessmentInSettlementCurrency() {
            return data.substring(512, 532);
        }

        public String getConversionRateOfGrossSettlementAmountIn$ToAccountingCurrency() {
            return data.substring(532, 552);
        }

        public String getCurrencyCodeOfAccountingCurrency() {
            return data.substring(552, 555);
        }

        public String getFiller() {
            return data.substring(555, 800);
        }
    }

    public static class DisputeTrailerRecord {
        /**
         * 40
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getSourceDxsCode() {
            return data.substring(2, 5);
        }

        public String getSourceIIC() {
            return data.substring(5, 16);
        }

        public String getDestinationDxsCode() {
            return data.substring(16, 19);
        }

        public String getDestinationIIC() {
            return data.substring(19, 30);
        }

        public String getRecapNumber() {
            return data.substring(30, 33);
        }

        public String getCurrencyCode() {
            return data.substring(33, 36);
        }

        public String getNumberOfDisputeDetailRecords() {
            return data.substring(36, 44);
        }

        public String getSummationOfGrossDisputeAmounts() {
            return data.substring(44, 62);
        }

        public String getSummationOfInterchangeFeeCommissionIn$() {
            return data.substring(62, 80);
        }

        public String getFxRate() {
            return data.substring(80, 96);
        }

        public String getDebitCreditIndicator() {
            return data.substring(96, 97);
        }

        public String getFiller() {
            return data.substring(97, 800);
        }
    }

    public static class FileTrailerRecord {
        /**
         * 99
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getParticipantDxsCode() {
            return data.substring(2, 5);
        }

        public String getParticipantIIC() {
            return data.substring(5, 16);
        }

        public String getNumberOfFees() {
            return data.substring(16, 24);
        }

        public String getNumberOfDisputes() {
            return data.substring(24, 32);
        }

        public String getNumberOfOriginalCharges() {
            return data.substring(32, 40);
        }

        public String getNetSettlementAmount() {
            return data.substring(40, 58);
        }

        public String getGrossSettlementAmount() {
            return data.substring(58, 76);
        }

        public String getFiller() {
            return data.substring(76, 800);
        }
    }

    public static DisputeAndFee parse(List<String> list) {
        DisputeAndFee daf = new DisputeAndFee();
        for (String line : list) {
            if (line.isEmpty()) {
                continue;
            }
            String type = line.substring(0, 2);

        }
        return null;
    }
}
