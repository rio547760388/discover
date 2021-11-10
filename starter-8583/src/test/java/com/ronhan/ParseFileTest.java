package com.ronhan;

import com.ronhan.iso8583.discover.sftp.files.ConfirmationFile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/8/12
 **/
@Slf4j
public class ParseFileTest {
    @Test
    public void testConfirmationFile() throws IOException {
        Files.list(Paths.get("D:\\"))
                .filter(p -> p.toFile().getName().contains("D6ACQHO"))
                .forEach(p -> {
                    try {
                        List<String> list = Files.readAllLines(p);
                        ConfirmationFile cf = ConfirmationFile.parse(list);
                        ConfirmationFile.FileHeaderRecord fhr = cf.getFileHeaderRecord();
                        log.info("File header | type: {}, version: {}, IIC: {}, DXS: {}, originalNum: {}, originalDate: {}", fhr.getType(), fhr.getVersion(), fhr.getIIC(), fhr.getDxsCode(), fhr.getOriginalFileNumber(), fhr.getOriginalFileDate());
                        List<ConfirmationFile.SettlementRecapData> srds = cf.getSettlementRecapData();
                        srds.forEach(srd -> {
                            ConfirmationFile.SettlementRecapHeaderRecord srhr = srd.getSettlementRecapHeaderRecord();
                            log.info("recap header | type: {}, IIC: {}, DXS: {}, issuerIIC: {}, issuerDXS: {}, curr: {}, settleCurr: {}, alterCurr: {}, recapDate: {}, recapNum: {}", srhr.getType(), srhr.getIIC(), srhr.getDxsCode(), srhr.getIssuerIIC(), srhr.getIssuerDxsCode(), srhr.getCurrencyCode(), srhr.getSettlementCurrencyCode(), srhr.getAlternateCurrency(), srhr.getRecapDate(), srhr.getRecapNumber());
                            List<ConfirmationFile.SettlementBatchPackage> sbp =  srd.getSettlementBatchPackages();
                            sbp.forEach(one -> {
                                log.info("batch header | recapNum: {}, batchNum: {}", one.getBatchHeaderRecord().getRecapNumber(), one.getBatchHeaderRecord().getBatchNumber());
                                List<ConfirmationFile.ChargeRecord> crs = one.getChargeRecords();
                                crs.forEach(cr -> {
                                    log.info("charge record | type: {}, card: {}, authNum: {}, networkReferenceId: {}, reference: {}， mcc: {}", cr.getType(), cr.getCardNumber(), cr.getAuthorizationNumber(), cr.getNetworkReferenceId(), cr.getReferenceNumber(), cr.getMCC());
                                });
                                log.info("batch trailer | recapNum: {}, batchNum: {}, creditAmount: {}", one.getBatchTrailerRecord().getRecapNumber(), one.getBatchTrailerRecord().getBatchNumber(), one.getBatchTrailerRecord().getAmountOfCreditChargesInTheBatch());
                            });
                            ConfirmationFile.SettlementRecapTrailerRecord srtr = srd.getSettlementRecapTrailerRecord();
                            log.info("recap trailer | type: {}, IIC: {}, DXS: {}, netSettleAmount: {}, netSettleAmount$: {}, numBatchRecap: {}, numDebit: {}, numCredit: {}", srtr.getType(), srtr.getIIC(), srtr.getDxsCode(), srtr.getNetSettlementAmount(), srtr.getNetSettlementAmountIn$(), srtr.getNumberOfBatchesInTheRecap(), srtr.getNumberOfDebitBatchesInTheRecap(), srtr.getNumberOfCreditBatchesInTheRecap());
                        });
                        cf.getRejectedRecapData().forEach(rrd -> {
                            rrd.getRejectRecapPackages().forEach(rrp -> {
                                ConfirmationFile.RejectedRecapErrorRecord rrer = rrp.getRejectedRecapErrorRecord();
                                log.warn("reject err record | field: {}, fieldV: {}, errCode: {}, errMsg: {}", rrer.getFieldName(), rrer.getFieldValue(), rrer.getRejectionCode(), rrer.getRejectionMessage());
                            });
                        });
                        cf.getFileErrorRecords().forEach(fer -> {
                            log.error("file err record | start: {}, errMsg: {}, end: {}", fer.getStartLine(), fer.getErrorMessage(), fer.getEndLine());
                        });
                        cf.getSuspendedRecapData().forEach(srd -> {
                            srd.getSuspensionRecords().forEach(sr -> {
                                log.info("suspend record | field: {}, fieldV: {}, suspendCode: {}, suspendMsg: {}", sr.getFieldName(), sr.getFieldValue(), sr.getSuspensionCode(), sr.getSuspensionMessage());
                            });
                        });
                        ConfirmationFile.FileTrailerRecord ftr = cf.getFileTrailerRecord();
                        log.info("file trailer | type: {}, IIC: {}, DXS: {}, netAmount: {}, grossAmount: {}, numBatch: {}, numCharge: {}, numSettle: {}, numReject: {}, numSuspend: {}",ftr.getType(), ftr.getIIC(), ftr.getDxsCode(), ftr.getNetSettlementAmount(), ftr.getGrossSettlementAmount(), ftr.getNumberOfBatches(), ftr.getNumberOfCharges(), ftr.getNumberOfSettlementRecaps(), ftr.getNumberOfRejectedRecaps(), ftr.getNumberOfSuspendedRecaps());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }
}
