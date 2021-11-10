package com.ronhan.iso8583.discover.sftp.files;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/16
 **/
@Data
public class ConfirmationFile {
    private FileHeaderRecord fileHeaderRecord;

    private List<SettlementRecapData> settlementRecapData = new ArrayList<>();

    private List<SuspendedRecapData> suspendedRecapData = new ArrayList<>();

    private List<RejectedRecapData> rejectedRecapData = new ArrayList<>();

    private List<FileErrorRecord> fileErrorRecords = new ArrayList<>();

    private FileTrailerRecord fileTrailerRecord;

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

        public String getDxsCode() {
            return data.substring(2, 5);
        }

        public String getIIC() {
            return data.substring(5, 16);
        }

        public String getVersion() {
            return data.substring(16, 21);
        }

        public String getOriginalFileDate() {
            return data.substring(21, 29);
        }

        public String getOriginalFileNumber() {
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

    public static class SettlementRecapHeaderRecord {
        /**
         * 02
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getDxsCode() {
            return data.substring(2, 5);
        }

        public String getIIC() {
            return data.substring(5, 16);
        }

        public String getIssuerDxsCode() {
            return data.substring(16, 19);
        }

        public String getIssuerIIC() {
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

        public String getAlternateCurrency() {
            return data.substring(47, 50);
        }

        public String getFiller() {
            return data.substring(50, 800);
        }
    }

    public static class BatchHeaderRecord {
        /**
         * 04
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getRecapNumber() {
            return data.substring(2, 5);
        }

        public String getBatchNumber() {
            return data.substring(5, 8);
        }

        public String getFiller() {
            return data.substring(8, 800);
        }
    }

    public static class ChargeRecord {
        /**
         * 06
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

        public String getCardNumber() {
            return data.substring(5, 24);
        }

        public String getChargeDate() {
            return data.substring(24, 32);
        }

        public String getChargeType() {
            return data.substring(32, 35);
        }

        public String getTypeOfCharge() {
            return data.substring(35, 37);
        }

        public String getReferenceNumber() {
            return data.substring(37, 45);
        }

        public String getAuthorizationNumber() {
            return data.substring(45, 51);
        }

        public String getProgramTransactionRate() {
            return data.substring(51, 57);
        }

        public String getInterchangePTF$() {
            return data.substring(57, 63);
        }

        public String getGrossChargeAmount() {
            return data.substring(63, 79);
        }

        public String getNetChargeAmount() {
            return data.substring(79, 99);
        }

        public String getAlternateCurrencyGrossAmount() {
            return data.substring(99, 119);
        }

        public String getAlternateCurrencyNetAmount() {
            return data.substring(119, 139);
        }

        public String getInterchangeCommissionInTransactionCurrency() {
            return data.substring(139, 159);
        }

        public String getInterchangeCommissionInAlternateCurrency() {
            return data.substring(159, 179);
        }

        public String getGrossSettlementAmount() {
            return data.substring(179, 199);
        }

        public String getNetSettlementAmount() {
            return data.substring(199, 219);
        }

        public String getInterchangeCommissionSettlementAmount() {
            return data.substring(219, 239);
        }

        public String getGrossSettlementAmountIn$() {
            return data.substring(239, 259);
        }

        public String getNetSettlementAmountIn$() {
            return data.substring(259, 279);
        }

        public String getInterchangeCommissionIn$() {
            return data.substring(279, 299);
        }

        public String getInterchangePTFInSettlementCurrency() {
            return data.substring(299, 319);
        }

        public String getPricingRuleName() {
            return data.substring(319, 355);
        }

        public String getPricingRuleCode() {
            return data.substring(355, 361);
        }

        public String getPricingRuleSerialNumber() {
            return data.substring(361, 397);
        }

        public String getSettlementDate() {
            return data.substring(397, 405);
        }

        public String getECI() {
            return data.substring(405, 406);
        }

        public String getCAVV() {
            return data.substring(406, 410);
        }

        public String getNetworkReferenceId() {
            return data.substring(410, 425);
        }

        public String getATMInterchangeFeeIn$() {
            return data.substring(425, 445);
        }

        public String getATMSecurityFeeIn$() {
            return data.substring(445, 465);
        }

        public String getATMNetworkInternationalProcessingFeeIn$() {
            return data.substring(465, 485);
        }

        public String getATMInterchangeFeeInSettlementCurrency() {
            return data.substring(485, 505);
        }

        public String getATMSecurityFeeInSettlementCurrency() {
            return data.substring(505, 525);
        }

        public String getATMNetworkInternationalProcessingFeeInSettlementCurrency() {
            return data.substring(525, 545);
        }

        public String getSurchargeFee() {
            return data.substring(545, 555);
        }

        public String getAcquirerGeoCode() {
            return data.substring(555, 558);
        }

        public String getCardProductType() {
            return data.substring(558, 559);
        }

        public String getMCC() {
            return data.substring(559, 563);
        }

        public String getINTES() {
            return data.substring(563, 567);
        }

        public String getMerchantId() {
            return data.substring(567, 582);
        }

        public String getCardholderPresent() {
            return data.substring(582, 583);
        }

        public String getCardPresent() {
            return data.substring(583, 584);
        }

        public String getCaptureMethod() {
            return data.substring(584, 585);
        }

        public String getMerchantGeoCode() {
            return data.substring(585, 588);
        }

        public String getIssuerGeoCode() {
            return data.substring(588, 591);
        }

        public String getMerchantPan() {
            return data.substring(591, 610);
        }

        public String getFiller() {
            return data.substring(610, 800);
        }
    }

    public static class BatchTrailerRecord {
        /**
         * 08
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getRecapNumber() {
            return data.substring(2, 5);
        }

        public String getBatchNumber() {
            return data.substring(5, 8);
        }

        public String getNumberOfChargesInTheBatch() {
            return data.substring(8, 10);
        }

        public String getNumberOfCreditChargesInTheBatch() {
            return data.substring(10, 12);
        }

        public String getNumberOfDebitChargesInTheBatch() {
            return data.substring(12, 14);
        }

        public String getAmountOfCreditChargesInTheBatch() {
            return data.substring(14, 30);
        }

        public String getAmountOfDebitChargesInTheBatch() {
            return data.substring(30, 46);
        }

        public String getFiller() {
            return data.substring(46, 800);
        }
    }

    public static class SettlementRecapTrailerRecord {
        /**
         * 10
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getDxsCode() {
            return data.substring(2, 5);
        }

        public String getIIC() {
            return data.substring(5, 16);
        }

        public String getIssuerDxsCode() {
            return data.substring(16, 19);
        }

        public String getIssuerIIC() {
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

        public String getNumberOfBatchesInTheRecap() {
            return data.substring(47, 50);
        }

        public String getNumberOfCreditBatchesInTheRecap() {
            return data.substring(50, 53);
        }

        public String getNumberOfDebitBatchesInTheRecap() {
            return data.substring(53, 56);
        }

        public String getRecapGrossAmountInTheCurrencyOfTheTransaction() {
            return data.substring(56, 74);
        }

        public String getRecapGrossAlternateAmountDenominatedInTheAlternateCurrency() {
            return data.substring(74, 92);
        }

        public String getOriginalRecapDiscountRate() {
            return data.substring(92, 98);
        }

        public String getBlendedProgramTransactionAmount() {
            return data.substring(98, 104);
        }

        public String getInterchangePTFIN$() {
            return data.substring(104, 122);
        }

        public String getInterchangePTFInSettlementCurrency() {
            return data.substring(122, 140);
        }

        public String getPricedRecapNetAmountDenominatedInTheCurrencyOfTheTransaction() {
            return data.substring(140, 158);
        }

        public String getPricedRecapNetAlternateAmountDenominatedInTheAlternateCurrency() {
            return data.substring(158, 176);
        }

        public String getRecapInterchangeCommissionInTransactionCurrency() {
            return data.substring(176, 194);
        }

        public String getInterchangeCommissionInAlternateCurrency() {
            return data.substring(194, 212);
        }

        public String getGrossSettlementAmount() {
            return data.substring(212, 230);
        }

        public String getNetSettlementAmount() {
            return data.substring(230, 248);
        }

        public String getRecapInterchangeCommissionInSettlementCurrency() {
            return data.substring(248, 266);
        }

        public String getGrossSettlementAmountIn$() {
            return data.substring(266, 284);
        }

        public String getNetSettlementAmountIn$() {
            return data.substring(284, 302);
        }

        public String getInterchangeCommissionIn$() {
            return data.substring(302, 320);
        }

        public String getSettlementDateOfRecap() {
            return data.substring(320, 328);
        }

        public String getFXRate() {
            return data.substring(328, 344);
        }

        public String getPayableOrReceivableIndicator() {
            return data.substring(344, 345);
        }

        public String getFiller() {
            return data.substring(345, 800);
        }
    }

    public static class SuspendedRecapHeaderRecord {
        /**
         * 12
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getDxsCode() {
            return data.substring(2, 5);
        }

        public String getIIC() {
            return data.substring(5, 16);
        }

        public String getIssuerDxsCode() {
            return data.substring(16, 19);
        }

        public String getIssuerIIC() {
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

        public String getFiller() {
            return data.substring(44, 800);
        }
    }

    public static class SuspensionRecord {
        /**
         * 14
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getRecapNumber() {
            return data.substring(2, 5);
        }

        public String getBatchNumber() {
            return data.substring(5, 8);
        }

        public String getSequenceNumber() {
            return data.substring(8, 11);
        }

        public String getFieldName() {
            return data.substring(11, 43);
        }

        public String getFieldValue() {
            return data.substring(43, 107);
        }

        public String getSuspensionCode() {
            return data.substring(107, 109);
        }

        public String getSuspensionMessage() {
            return data.substring(109, 237);
        }

        public String getFiller() {
            return data.substring(237, 800);
        }
    }

    public static class SuspendedRecapTrailerRecode {
        /**
         * 16
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getDxsCode() {
            return data.substring(2, 5);
        }

        public String getIIC() {
            return data.substring(5, 16);
        }

        public String getIssuerDxsCode() {
            return data.substring(16, 19);
        }

        public String getIssuerIIC() {
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

        public String getNumberOfSuspensions() {
            return data.substring(44, 52);
        }

        public String getFiller() {
            return data.substring(52, 800);
        }
    }

    public static class RejectedRecapHeaderRecord {
        /**
         * 18
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getDxsCode() {
            return data.substring(2, 5);
        }

        public String getIIC() {
            return data.substring(5, 16);
        }

        public String getIssuerDxsCode() {
            return data.substring(16, 19);
        }

        public String getIssuerIIC() {
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

        public String getFiller() {
            return data.substring(44, 800);
        }
    }

    public static class RejectedRecapErrorRecord {
        /**
         * 20
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getRecapNumber() {
            return data.substring(2, 5);
        }

        public String getBatchNumber() {
            return data.substring(5, 8);
        }

        public String getSequenceNumber() {
            return data.substring(8, 11);
        }

        public String getFieldName() {
            return data.substring(11, 43);
        }

        public String getFieldValue() {
            return data.substring(43, 107);
        }

        public String getRejectionCode() {
            return data.substring(107, 109);
        }

        public String getRejectionMessage() {
            return data.substring(109, 237);
        }

        public String getFiller() {
            return data.substring(237, 800);
        }
    }

    public static class RejectedRecapFileRecord {
        /**
         * 22
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getInterchangeFileRecord() {
            return data.substring(2, 800);
        }
    }

    public static class RejectedRecapTrailerRecord {
        /**
         * 24
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getDxsCode() {
            return data.substring(2, 5);
        }

        public String getIIC() {
            return data.substring(5, 16);
        }

        public String getIssuerDxsCode() {
            return data.substring(16, 19);
        }

        public String getIssuerIIC() {
            return data.substring(19, 30);
        }

        public String getRecapNumber() {
            return data.substring(30 ,33);
        }

        public String getCurrencyCode() {
            return data.substring(33, 36);
        }

        public String getRecapDate() {
            return data.substring(36, 44);
        }

        public String getNumberOfRejections() {
            return data.substring(44, 52);
        }

        public String getFiller() {
            return data.substring(52, 800);
        }
    }

    public static class FileErrorRecord {
        /**
         * 32
         */
        @Setter
        @Getter
        private String data;

        public String getType() {
            return data.substring(0, 2);
        }

        public String getStartLine() {
            return data.substring(2, 10);
        }

        public String getEndLine() {
            return data.substring(10, 18);
        }

        public String getErrorMessage() {
            return data.substring(18, 274);
        }

        public String getFiller() {
            return data.substring(274, 800);
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

        public String getDxsCode() {
            return data.substring(2, 5);
        }

        public String getIIC() {
            return data.substring(5, 16);
        }

        public String getNumberOfSettlementRecaps() {
            return data.substring(16, 24);
        }

        public String getNumberOfBatches() {
            return data.substring(24, 32);
        }

        public String getNumberOfCharges() {
            return data.substring(32, 40);
        }

        public String getNumberOfRejectedRecaps() {
            return data.substring(40, 48);
        }

        public String getNumberOfSuspendedRecaps() {
            return data.substring(48, 56);
        }

        public String getNetSettlementAmount() {
            return data.substring(56, 74);
        }

        public String getGrossSettlementAmount() {
            return data.substring(74, 92);
        }

        public String getFiller() {
            return data.substring(92, 800);
        }
    }

    @Data
    public static class SettlementRecapData {
        private SettlementRecapHeaderRecord settlementRecapHeaderRecord;

        private List<SettlementBatchPackage> settlementBatchPackages = new ArrayList<>();

        private SettlementRecapTrailerRecord settlementRecapTrailerRecord;
    }

    @Data
    public static class SettlementBatchPackage {
        private BatchHeaderRecord batchHeaderRecord;

        private List<ChargeRecord> chargeRecords = new ArrayList<>();

        private BatchTrailerRecord batchTrailerRecord;
    }

    @Data
    public static class SuspendedRecapData {
        private SuspendedRecapHeaderRecord suspendedRecapHeaderRecord;

        private List<SuspensionRecord> suspensionRecords = new ArrayList<>();

        private SuspendedRecapTrailerRecode suspendedRecapTrailerRecode;
    }

    @Data
    public static class RejectedRecapData {
        private RejectedRecapHeaderRecord rejectedRecapHeaderRecord;

        private List<RejectedRecapPackage> rejectRecapPackages = new ArrayList<>();

        private RejectedRecapTrailerRecord rejectedRecapTrailerRecord;
    }

    @Data
    public static class RejectedRecapPackage {
        private RejectedRecapErrorRecord rejectedRecapErrorRecord;

        private RejectedRecapFileRecord rejectedRecapFileRecord;
    }

    public static ConfirmationFile parse(List<String> list) {
        ConfirmationFile cf = new ConfirmationFile();
        for (String line : list) {
            if (line.isEmpty()) {
                continue;
            }
            String type = line.substring(0, 2);

            int index = cf.getSettlementRecapData().size() - 1;
            int index1 = -1;
            if (index != -1) {
                index1 = cf.getSettlementRecapData().get(index).getSettlementBatchPackages().size() - 1;
            }

            int index2 = cf.getSuspendedRecapData().size() - 1;

            int index3 = cf.getRejectedRecapData().size() - 1;
            int index4 = -1;
            if (index3 != -1) {
                index4 = cf.getRejectedRecapData().get(index3).getRejectRecapPackages().size() - 1;
            }
            switch (type) {
                case "01":
                    FileHeaderRecord fhr = new FileHeaderRecord();
                    fhr.setData(line);
                    cf.setFileHeaderRecord(fhr);
                    break;
                case "02":
                    SettlementRecapData srd = new SettlementRecapData();
                    SettlementRecapHeaderRecord srhr = new SettlementRecapHeaderRecord();
                    srhr.setData(line);
                    srd.setSettlementRecapHeaderRecord(srhr);
                    cf.getSettlementRecapData().add(srd);
                    break;
                case "04":
                    SettlementBatchPackage sbp = new SettlementBatchPackage();
                    BatchHeaderRecord bhr = new BatchHeaderRecord();
                    bhr.setData(line);
                    sbp.setBatchHeaderRecord(bhr);
                    cf.getSettlementRecapData().get(index).getSettlementBatchPackages().add(sbp);
                    break;
                case "06":
                    ChargeRecord cr = new ChargeRecord();
                    cr.setData(line);
                    cf.getSettlementRecapData().get(index).getSettlementBatchPackages().get(index1).getChargeRecords().add(cr);
                    break;
                case "08":
                    BatchTrailerRecord btr = new BatchTrailerRecord();
                    btr.setData(line);
                    cf.getSettlementRecapData().get(index).getSettlementBatchPackages().get(index1).setBatchTrailerRecord(btr);
                    break;
                case "10":
                    SettlementRecapTrailerRecord srtr = new SettlementRecapTrailerRecord();
                    srtr.setData(line);
                    cf.getSettlementRecapData().get(index).setSettlementRecapTrailerRecord(srtr);
                    break;
                case "12":
                    SuspendedRecapData susrd = new SuspendedRecapData();
                    SuspendedRecapHeaderRecord susrhr = new SuspendedRecapHeaderRecord();
                    susrhr.setData(line);
                    susrd.setSuspendedRecapHeaderRecord(susrhr);
                    cf.getSuspendedRecapData().add(susrd);
                    break;
                case "14":
                    SuspensionRecord sr = new SuspensionRecord();
                    sr.setData(line);
                    cf.getSuspendedRecapData().get(index2).getSuspensionRecords().add(sr);
                    break;
                case "16":
                    SuspendedRecapTrailerRecode susrtr = new SuspendedRecapTrailerRecode();
                    susrtr.setData(line);
                    cf.getSuspendedRecapData().get(index2).setSuspendedRecapTrailerRecode(susrtr);
                    break;
                case "18":
                    RejectedRecapData rrd = new RejectedRecapData();
                    RejectedRecapHeaderRecord rrhr = new RejectedRecapHeaderRecord();
                    rrhr.setData(line);
                    rrd.setRejectedRecapHeaderRecord(rrhr);
                    cf.getRejectedRecapData().add(rrd);
                    break;
                case "20":
                    RejectedRecapPackage rrp = new RejectedRecapPackage();
                    RejectedRecapErrorRecord rrer = new RejectedRecapErrorRecord();
                    rrer.setData(line);
                    rrp.setRejectedRecapErrorRecord(rrer);
                    cf.getRejectedRecapData().get(index3).getRejectRecapPackages().add(rrp);
                    break;
                case "22":
                    RejectedRecapFileRecord rrfr = new RejectedRecapFileRecord();
                    rrfr.setData(line);
                    cf.getRejectedRecapData().get(index3).getRejectRecapPackages().get(index4).setRejectedRecapFileRecord(rrfr);
                    break;
                case "24":
                    RejectedRecapTrailerRecord rrtr = new RejectedRecapTrailerRecord();
                    rrtr.setData(line);
                    cf.getRejectedRecapData().get(index3).setRejectedRecapTrailerRecord(rrtr);
                    break;
                case "32":
                    FileErrorRecord fer = new FileErrorRecord();
                    fer.setData(line);
                    cf.getFileErrorRecords().add(fer);
                    break;
                case "99":
                    FileTrailerRecord ftr = new FileTrailerRecord();
                    ftr.setData(line);
                    cf.setFileTrailerRecord(ftr);
                    break;
            }
        }

        return cf;
    }
}
