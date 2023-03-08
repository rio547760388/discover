package com.ronhan.pacypay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.ronhan.iso8583.discover.sftp.files.DisputeAndFee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/11/26
 **/

public class Json {

    @Test
    public void test() {
        String str = "{\n" +
                "    \"3d\":false,\n" +
                "    \"param\":{\n" +
                "        \"card.expiryMonth\":\"12\",\n" +
                "        \"notificationUrl\":\"http://127.0.0.1/notify/channel/trustpay\",\n" +
                "        \"amount\":\"1.10\",\n" +
                "        \"customer.browser.javaEnabled\":\"false\",\n" +
                "        \"customer.browser.language\":\"zh-CN\",\n" +
                "        \"customer.browser.screenHeight\":\"1440\",\n" +
                "        \"customer.browser.javascriptEnabled\":\"true\",\n" +
                "        \"paymentBrand\":\"VISA\",\n" +
                "        \"shopperResultUrl\":\"http://127.0.0.1/notify/back/500010211022174141014\",\n" +
                "        \"customer.ip\":\"127.0.0.1\",\n" +
                "        \"customer.browser.screenColorDepth\":\"24\",\n" +
                "        \"entityId\":\"8ac7a4c874d4c2430174d96283d00cd7\",\n" +
                "        \"card.number\":\"4200000000000000\",\n" +
                "        \"customer.browser.challengeWindow\":\"5\",\n" +
                "        \"card.holder\":\"Test Test\",\n" +
                "        \"card.cvv\":\"666\",\n" +
                "        \"customer.browser.timezone\":\"-480\",\n" +
                "        \"merchantTransactionId\":\"500010211022174141014\",\n" +
                "        \"card.expiryYear\":\"2022\",\n" +
                "        \"currency\":\"USD\",\n" +
                "        \"customer.browser.acceptHeader\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\",\n" +
                "        \"customer.browser.screenWidth\":\"2560\",\n" +
                "        \"customer.browser.userAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36\"\n" +
                "    }\n" +
                "}";
        JSONObject j = JSON.parseObject(str);
        System.out.println(JSONPath.set(j, "$.a.b", "hoho"));
        System.out.println(JSONPath.read(str, "$.param['card.expiryMonth']"));
        System.out.println(str);
        System.out.println(j.toJSONString());
    }

    @Test
    public void parseFile() throws IOException {
        Path p = Paths.get("D:\\dc\\PRD.DCIINTO.DCOUTD6.D6DFFHO.20220113.N063936");
        List<String> list = Files.readAllLines(p);
        DisputeAndFee daf = DisputeAndFee.parse(list);
        System.out.println(daf);
        daf.getHeaderRecord();
        CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(new FileOutputStream("D:\\disputeAndFee2.csv")), CSVFormat.EXCEL);
        csvPrinter.printRecord("Record Type", "Participant DXS IIC", "Participant ISO IIC", "Version Indicator", "Handoff Date", "Handoff File Number", "Time Stamp", "Empty File Indicator");
        csvPrinter.printRecord(daf.getHeaderRecord().getType(), daf.getHeaderRecord().getParticipantDxsCode(), daf.getHeaderRecord().getParticipantIIC(), daf.getHeaderRecord().getVersion(), daf.getHeaderRecord().getHandoffDate(), daf.getHeaderRecord().getHandoffFileNumber(), daf.getHeaderRecord().getTimestamp(), daf.getHeaderRecord().getEmptyFileIndicator());

        daf.getFeeRecords().forEach(fr -> {
            try {
                csvPrinter.printRecord("Record Type", "Source DXS IIC", "Source ISO IIC", "Destination DXS IIC", "Destination ISO IIC", "Recap Number", "Currency Code", "Recap Date", "Settlement Currency Code");

                DisputeAndFee.FeeHeaderRecord fhr = fr.getFeeHeaderRecord();
                csvPrinter.printRecord(fhr.getType(), fhr.getSourceDxsCode(), fhr.getSourceIIC(), fhr.getDestinationDxsCode(), fhr.getDestinationIIC(), fhr.getRecapNumber(), fhr.getCurrencyCode(), fhr.getRecapDate(), fhr.getSettlementCurrencyCode());

                fr.getFeeDetailRecords().forEach(fdr -> {
                    try {
                        csvPrinter.printRecord("Record Type", "Reserved", "Fee Date", "Type of Charge", "Clearing Reference Number", "Xchange Reference Number", "Program Transaction Amount", "Gross Fee Amount", "Net Fee Amount", "Interchange Fee Commission in Transaction Currency", "Gross Fee Settlement Amount in US$", "Net Fee Settlement Amount in US$", "Interchange Fee Commission in US$", "Settlement Date", "Settlement Indicator", "Fee Description", "Rule SN", "Rule Description", "Volume", "Transaction Count", "Fee basis", "Rate/Amount", "Sequence Number");

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
