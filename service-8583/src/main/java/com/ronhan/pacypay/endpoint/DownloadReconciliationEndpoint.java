package com.ronhan.pacypay.endpoint;

import com.ronhan.pacypay.pojo.Reconciliation;
import com.ronhan.pacypay.service.ReconciliationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/12/10
 **/
@RestController
@Api(tags = "")
@Slf4j
public class DownloadReconciliationEndpoint {

    @Autowired
    private ReconciliationService reconciliationService;

    @GetMapping("downloadReconciliation")
    public void downloadReconciliation(String start, String end, HttpServletRequest req, HttpServletResponse res) {

        List<Reconciliation> list = reconciliationService.fetchData(start, end);

        res.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        res.setHeader("Content-Disposition", "attachment;filename=reconciliation_" + start + "_" + end + ".csv");

        try (OutputStream os = res.getOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(os);
             BufferedWriter bw = new BufferedWriter(osw)) {
            CSVPrinter csvPrinter = new CSVPrinter(bw, CSVFormat.EXCEL);

            csvPrinter.printRecord("Pacypay Order No", "Recap Number", "Batch Number", "Sequence Number", "Charge Date", "Charge Type", "Type of Charge", "Reference Number", "Authorization Number", "Program Transaction Rate",
                    "Interchange PTF in US$", "Gross Charge Amount", "Net Charge Amount", "Alternate Currency Gross Amount", "Alternate Currency Net Amount", "Interchange Commission in Transaction Currency", "Interchange Commission in Alternate Currency", "Gross Settlement Amount", "Net Settlement Amount", "Interchange Commission Settlement Amount",
                    "Gross Settlement Amount in US$", "Net Settlement Amount in US$", "Interchange Commission in US$", "Interchange PTF in Settlement Currency", "Pricing Rule Name", "Pricing Rule Code", "Pricing Rule Serial Number", "Settlement Date", "Electronic Commerce and Payments Indicator (ECI)", "CAVV",
                    "Network Reference ID (NRID)", "ATM Interchange Fee in US$", "ATM Security Fee in US$", "ATM Network International Processing Fee in US$", "ATM Interchange Fee in Settlement Currency", "ATM Security Fee in Settlement Currency", "ATM Network International Processing Fee in Settlement Currency", "Surcharge Fee", "Acquirer Geo Code", "Card Product Type",
                    "MCC Code", "INTES Code", "Merchant ID", "Cardholder Present", "Card Present", "Capture Method", "Merchant Geo Code", "Issuer Geo Code", "Merchant PAN");

            list.forEach(l -> {
                try {
                    csvPrinter.printRecord("'" + l.getUniqueId(), l.getRecapNumber(), l.getBatchNumber(), l.getSequenceNum(), l.getChargeDate(), l.getChargeType(), l.getTypeOfCharge(), l.getReferenceNum(), l.getAuthorizationNum(), l.getProgramTransRate(),
                            l.getInterchangePTFIn$(), l.getGrossChargeAmount(), l.getNetChargeAmount(), l.getAlternateCurrGrossAmount(), l.getAlternateCurrNetAmount(), l.getInterchangeCommissionTransCurr(), l.getInterchangeCommissionAlternateCurr(), l.getGrossSettlementAmount(), l.getNetSettlementAmount(), l.getInterchangeCommissionSettleAmount(),
                            l.getGrossSettleAmountIn$(), l.getNetSettleAmountIn$(), l.getInterchangeCommissionIn$(), l.getInterchangePTFInSettleCurr(), l.getPriceRule(), l.getPriceRuleCode(), l.getPriceRuleSerialNum(), l.getSettleDate(), l.getEci(), l.getCavv(),
                            "'" + l.getNetworkReferenceId(), l.getAtmInterchangeFeeIn$(), l.getAtmSecurityFeeIn$(), l.getAtmProcessFeeIn$(), l.getAtmInterchangeFeeInSettleCurr(), l.getAtmSecurityFeeInSettleCurr(), l.getAtmProcessFeeInSettleCurr(), l.getSurchargeFee(), l.getAqgeo(), l.getCardProductType(),
                            l.getMcc(), l.getIntes(), "'" + l.getMerchantId(), l.getCardholderPresent(), l.getCardPresent(), l.getCaptureMethod(), l.getMerchantGeoCode(), l.getIssuerGeoCode(), l.getMerchantPan()
                            );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
