package com.ronhan.pacypay.util;

import com.ronhan.iso8583.discover.sftp.files.ConfirmationFile;
import com.ronhan.iso8583.discover.sftp.files.DisputeAndFee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/12/9
 **/
public class DcFileWriter {
    public static void writeInterchangeConfirmation(List<String> content, OutputStream os) throws IOException {
        ConfirmationFile cf = ConfirmationFile.parse(content);
        ConfirmationFile.FileHeaderRecord fhr = cf.getFileHeaderRecord();

        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        CSVPrinter csvPrinter = new CSVPrinter(bw, CSVFormat.EXCEL);

        csvPrinter.printRecord("Record Type", "Acquiring DXS IIC", "Acquiring ISO IIC", "Version Indicator", "Original File Date", "Original File Number", "Time Stamp", "Empty File Indicator");
        csvPrinter.printRecord(fhr.getType(), fhr.getDxsCode(), fhr.getIIC(), fhr.getVersion(), fhr.getOriginalFileDate(), fhr.getOriginalFileNumber(), fhr.getTimestamp(), fhr.getEmptyFileIndicator());

        cf.getSettlementRecapData().forEach(srd -> {
            try {
                ConfirmationFile.SettlementRecapHeaderRecord srhr = srd.getSettlementRecapHeaderRecord();
                csvPrinter.printRecord("Record Type", "Acquirer DXS IIC", "Acquiring ISO IIC", "Issuer DXS IIC", "Issuer ISO IIC", "Recap Number", "Currency Code", "Recap Date", "Settlement Currency Code", "Alternate Currency");
                csvPrinter.printRecord(srhr.getType(), srhr.getDxsCode(), srhr.getIIC(), srhr.getIssuerDxsCode(), srhr.getIssuerIIC(), srhr.getRecapNumber(), srhr.getCurrencyCode(), srhr.getRecapDate(), srhr.getSettlementCurrencyCode(), srhr.getAlternateCurrency());

                srd.getSettlementBatchPackages().forEach(sbp -> {
                    try {
                        ConfirmationFile.BatchHeaderRecord bhr = sbp.getBatchHeaderRecord();
                        csvPrinter.printRecord("Record Type", "Recap Number", "Batch Number");
                        csvPrinter.printRecord(bhr.getType(), bhr.getRecapNumber(), bhr.getBatchNumber());

                        csvPrinter.printRecord("Record Type", "Sequence Number", "Card Number", "Charge Date", "Charge Type", "Type of Charge", "Reference Number", "Authorization Number", "Program Transaction Rate", "Interchange PTF in US$", "Gross Charge Amount", "Net Charge Amount", "Alternate Currency Gross Amount", "Alternate Currency Net Amount", "Interchange Commission in Transaction Currency", "Interchange Commission in Alternate Currency", "Gross Settlement Amount", "Net Settlement Amount", "Interchange Commission Settlement Amount", "Gross Settlement Amount in US$", "Net Settlement Amount in US$", "Interchange Commission in US$", "Interchange PTF in Settlement Currency", "Pricing Rule Name", "Pricing Rule Code", "Pricing Rule Serial Number", "Settlement Date", "Electronic Commerce and Payments Indicator (ECI)", "CAVV", "Network Reference ID (NRID)", "ATM Interchange Fee in US$", "ATM Security Fee in US$", "ATM Network International Processing Fee in US$", "ATM Interchange Fee in Settlement Currency", "ATM Security Fee in Settlement Currency", "ATM Network International Processing Fee in Settlement Currency", "Surcharge Fee", "Acquirer Geo Code", "Card Product Type", "MCC Code", "INTES Code", "Merchant ID", "Cardholder Present", "Card Present", "Capture Method", "Merchant Geo Code", "Issuer Geo Code", "Merchant PAN");
                        sbp.getChargeRecords().forEach(cr -> {
                            try {
                                csvPrinter.printRecord(cr.getType(), cr.getSequenceNumber(), cr.getCardNumber(), cr.getChargeDate(), cr.getChargeType(), cr.getTypeOfCharge(), cr.getReferenceNumber(), cr.getAuthorizationNumber(), cr.getProgramTransactionRate(), cr.getInterchangePTF$(), cr.getGrossChargeAmount(), cr.getNetChargeAmount(), cr.getAlternateCurrencyGrossAmount(), cr.getAlternateCurrencyNetAmount(), cr.getInterchangeCommissionInTransactionCurrency(), cr.getInterchangeCommissionInAlternateCurrency(), cr.getGrossSettlementAmount(), cr.getNetSettlementAmount(), cr.getInterchangeCommissionSettlementAmount(), cr.getGrossSettlementAmountIn$(), cr.getNetSettlementAmountIn$(), cr.getInterchangeCommissionIn$(), cr.getInterchangePTFInSettlementCurrency(), cr.getPricingRuleName(), cr.getPricingRuleCode(), cr.getPricingRuleSerialNumber(), cr.getSettlementDate(), cr.getECI(), cr.getCAVV(), cr.getNetworkReferenceId(), cr.getATMInterchangeFeeIn$(), cr.getATMSecurityFeeIn$(), cr.getATMNetworkInternationalProcessingFeeIn$(), cr.getATMInterchangeFeeInSettlementCurrency(), cr.getATMSecurityFeeInSettlementCurrency(), cr.getATMNetworkInternationalProcessingFeeInSettlementCurrency(), cr.getSurchargeFee(), cr.getAcquirerGeoCode(), cr.getCardProductType(), cr.getMCC(), cr.getINTES(), cr.getMerchantId(), cr.getCardholderPresent(), cr.getCardPresent(), cr.getCaptureMethod(), cr.getMerchantGeoCode(), cr.getIssuerGeoCode(), cr.getMerchantPan());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        ConfirmationFile.BatchTrailerRecord btr = sbp.getBatchTrailerRecord();
                        csvPrinter.printRecord("Record Type", "Recap Number", "Batch Number", "Number of Charges in the Batch", "Number of Credit Charges in the Batch", "Number of Debit Charges in the Batch", "Amount of Credit Charges in the Batch", "Amount of Debit Charges in the Batch");
                        csvPrinter.printRecord(btr.getType(), btr.getRecapNumber(), btr.getBatchNumber(), btr.getNumberOfChargesInTheBatch(), btr.getNumberOfCreditChargesInTheBatch(), btr.getNumberOfDebitChargesInTheBatch(), btr.getAmountOfCreditChargesInTheBatch(), btr.getAmountOfDebitChargesInTheBatch());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                ConfirmationFile.SettlementRecapTrailerRecord srtr = srd.getSettlementRecapTrailerRecord();
                csvPrinter.printRecord("Record Type", "Acquirer DXS IIC", "Acquirer ISO IIC", "Issuer DXS IIC", "Issuer ISO IIC", "Recap Number", "Currency Code", "Recap Date", "Settlement Currency Code", "Number of Batches in the Recap", "Number of Credit Batches in the Recap", "Number of Debit Batches in the Recap", "Recap Gross Amount in the Currency of the Transaction", "Recap Gross Alternate Amount denominated in the Alternate Currency", "Original Recap Discount Rate", "Blended Program Transaction Amount (PTA)", "Interchange PTF in US$", "Interchange PTF in Settlement Currency", "Priced Recap Net Amount denominated in the Currency of the Transaction", "Priced Recap Net Alternate Amount denominated in the Alternate Currency", "Recap Interchange Commission in Transaction Currency", "Interchange Commission in Alternate Currency", "Gross Settlement Amount", "Net Settlement Amount", "Recap Interchange Commission in Settlement Currency", "Gross Settlement Amount in US$", "Net Settlement Amount in US$", "Interchange Commission in US$", "Settlement Date of Recap", "FX rate", "Payable/Receivable Indicator");
                csvPrinter.printRecord(srtr.getType(), srtr.getDxsCode(), srtr.getIIC(), srtr.getIssuerDxsCode(), srtr.getIssuerIIC(), srtr.getRecapNumber(), srtr.getCurrencyCode(), srtr.getRecapDate(), srtr.getSettlementCurrencyCode(), srtr.getNumberOfBatchesInTheRecap(), srtr.getNumberOfCreditBatchesInTheRecap(), srtr.getNumberOfDebitBatchesInTheRecap(), srtr.getRecapGrossAmountInTheCurrencyOfTheTransaction(), srtr.getRecapGrossAlternateAmountDenominatedInTheAlternateCurrency(), srtr.getOriginalRecapDiscountRate(), srtr.getBlendedProgramTransactionAmount(), srtr.getInterchangePTFIN$(), srtr.getInterchangePTFInSettlementCurrency(), srtr.getPricedRecapNetAmountDenominatedInTheCurrencyOfTheTransaction(), srtr.getPricedRecapNetAlternateAmountDenominatedInTheAlternateCurrency(), srtr.getRecapInterchangeCommissionInTransactionCurrency(), srtr.getInterchangeCommissionInAlternateCurrency(), srtr.getGrossSettlementAmount(), srtr.getNetSettlementAmount(), srtr.getRecapInterchangeCommissionInSettlementCurrency(), srtr.getGrossSettlementAmountIn$(), srtr.getNetSettlementAmountIn$(), srtr.getInterchangeCommissionIn$(), srtr.getSettlementDateOfRecap(), srtr.getFXRate(), srtr.getPayableOrReceivableIndicator());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        cf.getSuspendedRecapData().forEach(srd -> {
            try {
                ConfirmationFile.SuspendedRecapHeaderRecord srhr = srd.getSuspendedRecapHeaderRecord();
                csvPrinter.printRecord("Record Type", "Acquirer DXS IIC", "Acquiring ISO IIC", "Issuer DXS IIC", "Issuer ISO IIC", "Recap Number", "Currency Code", "Recap Date");
                csvPrinter.printRecord(srhr.getType(), srhr.getDxsCode(), srhr.getIIC(), srhr.getIssuerDxsCode(), srhr.getIssuerIIC(), srhr.getRecapNumber(), srhr.getCurrencyCode(), srhr.getRecapDate());

                csvPrinter.printRecord("Record Type", "Recap Number", "Batch Number", "Sequence Number", "Field Name", "Field Value", "Suspension Code", "Suspension Message");
                srd.getSuspensionRecords().forEach(sr -> {
                    try {
                        csvPrinter.printRecord(sr.getType(), sr.getRecapNumber(), sr.getBatchNumber(), sr.getSequenceNumber(), sr.getFieldName(), sr.getFieldValue(), sr.getSuspensionCode(), sr.getSuspensionMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                ConfirmationFile.SuspendedRecapTrailerRecode srtr = srd.getSuspendedRecapTrailerRecode();
                csvPrinter.printRecord("Record Type", "Acquirer DXS IIC", "Acquiring ISO IIC", "Issuer DXS IIC", "Issuer ISO IIC", "Recap Number", "Currency Code", "Recap Date", "Number of Suspensions");
                csvPrinter.printRecord(srtr.getType(), srtr.getDxsCode(), srtr.getIIC(), srtr.getIssuerDxsCode(), srtr.getIssuerIIC(), srtr.getRecapNumber(), srtr.getCurrencyCode(), srtr.getRecapDate(), srtr.getNumberOfSuspensions());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        cf.getRejectedRecapData().forEach(rrd -> {
            try {
                ConfirmationFile.RejectedRecapHeaderRecord srhr = rrd.getRejectedRecapHeaderRecord();
                csvPrinter.printRecord("Record Type", "Acquirer DXS IIC", "Acquiring ISO IIC", "Issuer DXS IIC", "Issuer ISO IIC", "Recap Number", "Currency Code", "Recap Date");
                csvPrinter.printRecord(srhr.getType(), srhr.getDxsCode(), srhr.getIIC(), srhr.getIssuerDxsCode(), srhr.getIssuerIIC(), srhr.getRecapNumber(), srhr.getCurrencyCode(), srhr.getRecapDate());

                rrd.getRejectRecapPackages().forEach(rrp -> {
                    try {
                        ConfirmationFile.RejectedRecapErrorRecord rrer = rrp.getRejectedRecapErrorRecord();
                        csvPrinter.printRecord("Record Type", "Recap Number", "Batch Number", "Sequence Number", "Field Name", "Field Value", "Rejection Code", "Rejection Message");
                        csvPrinter.printRecord(rrer.getType(), rrer.getRecapNumber(), rrer.getBatchNumber(), rrer.getSequenceNumber(), rrer.getFieldName(), rrer.getFieldValue(), rrer.getRejectionCode(), rrer.getRejectionMessage());

                        ConfirmationFile.RejectedRecapFileRecord rrfr = rrp.getRejectedRecapFileRecord();
                        csvPrinter.printRecord("Record Type", "Interchange File Record");
                        csvPrinter.printRecord(rrfr.getType(), rrfr.getInterchangeFileRecord());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                ConfirmationFile.RejectedRecapTrailerRecord rrtr = rrd.getRejectedRecapTrailerRecord();
                csvPrinter.printRecord("Record Type", "Acquirer DXS IIC", "Acquiring ISO IIC", "Issuer DXS IIC", "Issuer ISO IIC", "Recap Number", "Currency Code", "Recap Date", "Number of Rejections");
                csvPrinter.printRecord(rrtr.getType(), rrtr.getDxsCode(), rrtr.getIIC(), rrtr.getIssuerDxsCode(), rrtr.getIssuerIIC(), rrtr.getRecapNumber(), rrtr.getCurrencyCode(), rrtr.getRecapDate(), rrtr.getNumberOfRejections());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        cf.getFileErrorRecords().forEach(fer -> {
            try {
                csvPrinter.printRecord("Record Type", "Start Line", "End Line", "Error Message");
                csvPrinter.printRecord(fer.getType(), fer.getStartLine(), fer.getEndLine(), fer.getErrorMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ConfirmationFile.FileTrailerRecord ftr = cf.getFileTrailerRecord();
        csvPrinter.printRecord("Record Type", "Acquiring DXS IIC", "Acquiring ISO IIC", "Number of Settlement Recaps", "Number of Batches", "Number of Charges", "Number of Rejected Recaps", "Number of Suspended Recaps", "Net Settlement Amount", "Gross Settlement Amount");
        csvPrinter.printRecord(ftr.getType(), ftr.getDxsCode(), ftr.getIIC(), ftr.getNumberOfSettlementRecaps(), ftr.getNumberOfBatches(), ftr.getNumberOfCharges(), ftr.getNumberOfRejectedRecaps(), ftr.getNumberOfSuspendedRecaps(), ftr.getNetSettlementAmount(), ftr.getGrossSettlementAmount());
        csvPrinter.close(true);

    }


    public static void writeDisputeAndFee(List<String> content, OutputStream os) throws IOException {
        DisputeAndFee daf = DisputeAndFee.parse(content);

        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        CSVPrinter csvPrinter = new CSVPrinter(bw, CSVFormat.EXCEL);

        csvPrinter.printRecord("Record Type", "Participant DXS IIC", "Participant ISO IIC", "Version Indicator", "Handoff Date", "Handoff File Number", "Time Stamp", "Empty File Indicator");
        csvPrinter.printRecord(daf.getHeaderRecord().getType(), daf.getHeaderRecord().getParticipantDxsCode(), daf.getHeaderRecord().getParticipantIIC(), daf.getHeaderRecord().getVersion(), daf.getHeaderRecord().getHandoffDate(), daf.getHeaderRecord().getHandoffFileNumber(), daf.getHeaderRecord().getTimestamp(), daf.getHeaderRecord().getEmptyFileIndicator());

        daf.getFeeRecords().forEach(fr -> {
            try {
                csvPrinter.printRecord("Record Type", "Source DXS IIC", "Source ISO IIC", "Destination DXS IIC", "Destination ISO IIC", "Recap Number", "Currency Code", "Recap Date", "Settlement Currency Code");

                DisputeAndFee.FeeHeaderRecord fhr = fr.getFeeHeaderRecord();
                csvPrinter.printRecord(fhr.getType(), fhr.getSourceDxsCode(), fhr.getSourceIIC(), fhr.getDestinationDxsCode(), fhr.getDestinationIIC(), fhr.getRecapNumber(), fhr.getCurrencyCode(), fhr.getRecapDate(), fhr.getSettlementCurrencyCode());

                csvPrinter.printRecord("Record Type", "Reserved", "Fee Date", "Type of Charge", "Clearing Reference Number", "Xchange Reference Number", "Program Transaction Amount", "Gross Fee Amount", "Net Fee Amount", "Interchange Fee Commission in Transaction Currency", "Gross Fee Settlement Amount in US$", "Net Fee Settlement Amount in US$", "Interchange Fee Commission in US$", "Settlement Date", "Settlement Indicator", "Fee Description", "Rule SN", "Rule Description", "Volume", "Transaction Count", "Fee basis", "Rate/Amount", "Sequence Number");
                fr.getFeeDetailRecords().forEach(fdr -> {
                    try {
                        csvPrinter.printRecord(fdr.getType(), fdr.getReserved(), fdr.getFeeDate(), fdr.getTypeOfCharge(), fdr.getClearingReferenceNumber(), fdr.getXchangeReferenceNumber(), fdr.getProgramTransactionAmount(), fdr.getGrossFeeAmount(), fdr.getNetFeeAmount(), fdr.getInterchangeFeeCommissionInTransactionCurrency(), fdr.getGrossFeeSettlementAmountIn$(), fdr.getNetFeeSettlementAmountIn$(), fdr.getInterchangeFeeCommissionIn$(), fdr.getSettlementDate(), fdr.getSettlementIndicator(), fdr.getFeeDescription(), fdr.getRuleSN(), fdr.getRuleDescription(), fdr.getVolume(), fdr.getTransactionCount(), fdr.getFeeBasis(), fdr.getRateAmount(), fdr.getSequenceNumber());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                csvPrinter.printRecord("Record Type", "Source DXS IIC", "Source ISO IIC", "Destination DXS IIC", "Destination ISO IIC", "Recap Number", "Currency Code", "Number of Fee Detail Records", "Summation of Gross Fee Amounts", "FX rate", "Debit/Credit Indicator");
                DisputeAndFee.FeeTrailerRecord ftr = fr.getFeeTrailerRecord();
                csvPrinter.printRecord(ftr.getType(), ftr.getSourceDxsCode(), ftr.getSourceIIC(), ftr.getDestinationDxsCode(), ftr.getDestinationIIC(), ftr.getRecapNumber(), ftr.getCurrencyCode(), ftr.getNumberOfFeeDetailRecords(), ftr.getSummationOfGrossFeeAmounts(), ftr.getFxRate(), ftr.getDebitCreditIndicator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        daf.getDisputeRecords().forEach(dr -> {
            try {
                csvPrinter.printRecord("Record Type", "Source DXS IIC", "Source ISO IIC", "Destination DXS IIC", "Destination ISO IIC", "Recap Number", "Currency Code", "Recap Date", "Settlement Currency Code");

                DisputeAndFee.DisputeHeaderRecord dhr = dr.getDisputeHeaderRecord();
                csvPrinter.printRecord(dhr.getType(), dhr.getSourceDxsCode(), dhr.getSourceIIC(), dhr.getDestinationDxsCode(), dhr.getDestinationIIC(), dhr.getRecapNumber(), dhr.getCurrencyCode(), dhr.getRecapDate(), dhr.getSettlementCurrencyCode());

                dr.getDisputeRecordPackages().forEach(drp -> {
                    DisputeAndFee.DisputeDetailRecord ddr = drp.getDisputeDetailRecord();
                    try {
                        csvPrinter.printRecord("Record Type", "Sequence Number", "Dispute Date", "Type of Charge", "Clearing Reference Number", "Xchange Reference Number", "Program Transaction Amount", "Gross Dispute Amount", "Net Dispute Amount", "Interchange Fee Commission in Transaction Currency", "Gross Dispute Settlement Amount", "Net Dispute Settlement Amount", "Interchange Fee Commission in Settlement Currency", "Gross Dispute Settlement Amount in US$", "Net Dispute Settlement Amount in US$", "Interchange Fee Commission in US$", "Interchange Processing Date", "Stage Creation Date", "Reason Code", "Settlement Date", "Document Attached Indicator", "Document Code", "Method Sent", "Credit / Debit Indicator", "Dispute Stage", "Dispute Status", "Liable Franchise");

                        csvPrinter.printRecord(ddr.getType(), ddr.getSequenceNumber(), ddr.getDisputeDate(), ddr.getTypeOfCharge(), ddr.getClearingReferenceNumber(), ddr.getXchangeReferenceNumber(), ddr.getProgramTransactionAmount(), ddr.getGrossDisputeAmount(), ddr.getNetDisputeAmount(), ddr.getInterchangeFeeCommissionInTransactionCurrency(), ddr.getGrossDisputeSettlementAmount(), ddr.getNetDisputeSettlementAmount(), ddr.getInterchangeFeeCommissionInSettlementCurrency(), ddr.getGrossDisputeSettlementAmountIn$(), ddr.getNetDisputeSettlementAmountIn$(), ddr.getInterchangeFeeCommissionIn$(), ddr.getInterchangeProcessingDate(), ddr.getStageCreationDate(), ddr.getReasonCode(), ddr.getSettlementDate(), ddr.getDocumentAttachedIndicator(), ddr.getDocumentCode(), ddr.getMethodSent(), ddr.getCreditDebitIndicator(), ddr.getDisputeStage(), ddr.getDisputeStatus(), ddr.getLiableFranchise());

                        DisputeAndFee.OriginalInterchangeDetailRecord oidr = drp.getOriginalInterchangeDetailRecord();
                        csvPrinter.printRecord("Record Type", "Card Number", "Charge Date", "Charge Type", "Reference Number", "Authorization Number", "Interchange PTF in US$", "Gross Charge Amount", "Gross Charge Currency Code", "Net Charge Amount", "Interchange Commission in Transaction Currency", "Gross Settlement Amount", "Gross Settlement Currency Code", "Net Settlement Amount", "Interchange Commission Settlement Amount", "Gross Settlement Amount in US$", "Net Settlement Amount in US$", "Interchange Commission in US$", "Interchange PTF in Settlement Currency", "ATM Interchange Fee in US$", "ATM Switch Fee in US$", "ATM Network Assessment in US$", "ATM Network International Processing Fee in US$", "POS International Processing Fee in US$", "POS Authorization Fee in US$", "POS Network Assessment in US$", "ATM Interchange Fee in Settlement Currency", "ATM Switch Fee in Settlement Currency", "ATM Network Assessment in Settlement Currency", "ATM Network International Processing Fee in Settlement Currency", "POS International Processing Fee in Settlement Currency", "POS Authorization Fee in Settlement Currency", "POS Network Assessment in Settlement Currency", "Conversion Rate of Gross Settlement Amount in US$ to Accounting Currency", "Currency Code of Accounting Currency");
                        csvPrinter.printRecord(oidr.getType(), oidr.getCardNumber(), oidr.getChargeDate(), oidr.getChargeType(), oidr.getReferenceNumber(), oidr.getAuthorizationNumber(), oidr.getInterchangePTFIn$(), oidr.getGrossChargeAmount(), oidr.getGrossChargeCurrencyCode(), oidr.getNetChargeAmount(), oidr.getInterchangeCommissionInTransactionCurrency(), oidr.getGrossSettlementAmount(), oidr.getGrossSettlementCurrencyCode(), oidr.getNetSettlementAmount(), oidr.getInterchangeCommissionSettlementAmount(), oidr.getGrossSettlementAmountIn$(), oidr.getNetSettlementAmountIn$(), oidr.getInterchangeCommissionIn$(), oidr.getInterchangePTFInSettlementCurrency(), oidr.getATMInterchangeFeeIn$(), oidr.getATMSwitchFeeIn$(), oidr.getATMNetworkAssessmentIn$(), oidr.getATMNetworkInternationalProcessingFeeIn$(), oidr.getPOSInternationalProcessingFeeIn$(), oidr.getPOSAuthorizationFeeIn$(), oidr.getPOSNetworkAssessmentIn$(), oidr.getATMInterchangeFeeInSettlementCurrency(), oidr.getATMSwitchFeeInSettlementCurrency(), oidr.getATMNetworkAssessmentInSettlementCurrency(), oidr.getATMNetworkInternationalProcessingFeeInSettlementCurrency(), oidr.getPOSInternationalProcessingFeeInSettlementCurrency(), oidr.getPOSAuthorizationFeeInSettlementCurrency(), oidr.getPOSNetworkAssessmentInSettlementCurrency(), oidr.getConversionRateOfGrossSettlementAmountIn$ToAccountingCurrency(), oidr.getCurrencyCodeOfAccountingCurrency());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                csvPrinter.printRecord("Record Type", "Source DXS IIC", "Source ISO IIC", "Destination DXS IIC", "Destination ISO IIC", "Recap Number", "Currency Code", "Number of Dispute Detail Records", "Summation of Gross Dispute Amounts", "Summation of Interchange Fee Commission in US$", "FX rate", "Debit/Credit Indicator");
                DisputeAndFee.DisputeTrailerRecord dtr = dr.getDisputeTrailerRecord();
                csvPrinter.printRecord(dtr.getType(), dtr.getSourceDxsCode(), dtr.getSourceIIC(), dtr.getDestinationDxsCode(), dtr.getDestinationIIC(), dtr.getRecapNumber(), dtr.getCurrencyCode(), dtr.getNumberOfDisputeDetailRecords(), dtr.getSummationOfGrossDisputeAmounts(), dtr.getSummationOfInterchangeFeeCommissionIn$(), dtr.getFxRate(), dtr.getDebitCreditIndicator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        DisputeAndFee.FileTrailerRecord tr = daf.getTrailerRecord();
        csvPrinter.printRecord("Record Type", "Participant DXS IIC", "Participant ISO IIC", "Number of Fees", "Number of Disputes", "Number of Original Charges", "Net Settlement Amount", "Gross Settlement Amount");
        csvPrinter.printRecord(tr.getType(), tr.getParticipantDxsCode(), tr.getParticipantIIC(), tr.getNumberOfFees(), tr.getNumberOfDisputes(), tr.getNumberOfOriginalCharges(), tr.getNetSettlementAmount(), tr.getGrossSettlementAmount());
        csvPrinter.close(true);

    }
}
