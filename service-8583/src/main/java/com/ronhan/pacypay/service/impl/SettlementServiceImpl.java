package com.ronhan.pacypay.service.impl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ronhan.iso8583.discover.sftp.files.ConfirmationFile;
import com.ronhan.pacypay.dao.ConfirmationEntityRepository;
import com.ronhan.pacypay.dao.FxRateRepository;
import com.ronhan.pacypay.pojo.entity.*;
import com.ronhan.pacypay.service.SettlementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/8/27
 **/
@Slf4j
@Service
public class SettlementServiceImpl implements SettlementService {

    @Autowired
    private ConfirmationEntityRepository confirmationEntityRepository;

    @Autowired
    private JPAQueryFactory factory;

    @Autowired
    private FxRateRepository rateRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void loadFile(Path path, String filename) throws IOException {
        log.info("处理文件：{}", filename);
        Path absolutePath = Paths.get(path.toFile().getAbsolutePath(), filename);

        if (!absolutePath.toFile().exists()) {
            return;
        }

        List<String> lines = Files.readAllLines(absolutePath, StandardCharsets.UTF_8);

        Path target = Paths.get(path.toFile().getAbsolutePath(), "load2db", filename);
        if (target.toFile().exists()) {
            absolutePath.toFile().delete();
            return;
        }

        boolean load = false;
        if (filename.contains("ACQHO")) {
            ConfirmationFile cf = ConfirmationFile.parse(lines);
            saveCfFile(cf, filename);
            load = true;
        }
        //move file
        if (load) {
            Path p = Paths.get(path.toFile().getAbsolutePath(), "load2db");
            if (!p.toFile().exists()) {
                p.toFile().mkdirs();
            }
            Files.move(absolutePath, target);
        }
    }

    @Override
    public FxRate loadRate(String sellCurrency, String buyCurrency) {
        QFxRate q = QFxRate.fxRate;
        return factory.select(q)
                .from(q)
                .where(q.sellCurrency.eq(sellCurrency)
                .and(q.buyCurrency.eq(buyCurrency)))
                .orderBy(new OrderSpecifier<>(Order.DESC, q.id))
                .limit(1)
                .fetchOne();
    }

    @Override
    public RecapRecord getLastRecapRecord() {
        QRecapRecord q = QRecapRecord.recapRecord;
        return factory.select(q)
                .from(q)
                .orderBy(new OrderSpecifier<>(Order.DESC, q.id))
                .limit(1)
                .fetchOne();
    }

    @Override
    public void saveRate(List<FxRate> rates) {
        rateRepository.saveAll(rates);
    }

    private void saveCfFile(ConfirmationFile cf, String filename) {
        List<ConfirmationEntity> ceList = new ArrayList<>();
        ConfirmationFile.FileHeaderRecord fhr = cf.getFileHeaderRecord();
        //empty file
        if ("0".equals(fhr.getEmptyFileIndicator())) {
            return;
        }

        ConfirmationFile.FileTrailerRecord ftr = cf.getFileTrailerRecord();

        //charge record
        cf.getSettlementRecapData().forEach(recap -> {
            ConfirmationFile.SettlementRecapHeaderRecord srhr = recap.getSettlementRecapHeaderRecord();
            recap.getSettlementBatchPackages().forEach(batch -> {
                ConfirmationFile.BatchHeaderRecord bhr = batch.getBatchHeaderRecord();
                batch.getChargeRecords().forEach(charge -> {
                    ConfirmationEntity ce = ConfirmationEntity.builder()
                            .acquirerDXS_IIC(trim(fhr.getDxsCode()))
                            .acquirerISO_IIC(trim(fhr.getIIC()))
                            .versionIndicator(trim(fhr.getVersion()))
                            .originalFileDate(trim(fhr.getOriginalFileDate()))
                            .originalFileNum(trim(fhr.getOriginalFileNumber()))
                            .createTimestamp(trim(fhr.getTimestamp()))
                            .emptyFile(trim(fhr.getEmptyFileIndicator()))
                            .issuerDXS_IIC(trim(srhr.getIssuerDxsCode()))
                            .issuerISO_IIC(trim(srhr.getIssuerIIC()))
                            .recapNumber(trim(srhr.getRecapNumber()))
                            .currencyCode(trim(srhr.getCurrencyCode()))
                            .recapDate(trim(srhr.getRecapDate()))
                            .settleCurrencyCode(trim(srhr.getSettlementCurrencyCode()))
                            .alternateCurrency(trim(srhr.getAlternateCurrency()))
                            .batchNumber(trim(bhr.getBatchNumber()))
                            .type(trim(charge.getType()))
                            .sequenceNum(trim(charge.getSequenceNumber()))
                            .cardNum(trim(charge.getCardNumber()))
                            .chargeDate(trim(charge.getChargeDate()))
                            .chargeType(trim(charge.getChargeType()))
                            .typeOfCharge(trim(charge.getTypeOfCharge()))
                            .referenceNum(trim(charge.getReferenceNumber()))
                            .authorizationNum(trim(charge.getAuthorizationNumber()))
                            .programTransRate(trim(charge.getProgramTransactionRate()))
                            .interchangePTFIn$(trim(charge.getInterchangePTF$()))
                            .grossChargeAmount(trim(charge.getGrossChargeAmount()))
                            .netChargeAmount(trim(charge.getNetChargeAmount()))
                            .alternateCurrGrossAmount(trim(charge.getAlternateCurrencyGrossAmount()))
                            .alternateCurrNetAmount(trim(charge.getAlternateCurrencyNetAmount()))
                            .interchangeCommissionTransCurr(trim(charge.getInterchangeCommissionInTransactionCurrency()))
                            .interchangeCommissionAlternateCurr(trim(charge.getInterchangeCommissionInAlternateCurrency()))
                            .grossSettlementAmount(trim(charge.getGrossSettlementAmount()))
                            .netSettlementAmount(trim(charge.getNetSettlementAmount()))
                            .interchangeCommissionSettleAmount(trim(charge.getInterchangeCommissionSettlementAmount()))
                            .grossSettleAmountIn$(trim(charge.getGrossSettlementAmountIn$()))
                            .netSettleAmountIn$(trim(charge.getNetSettlementAmountIn$()))
                            .interchangeCommissionIn$(trim(charge.getInterchangeCommissionIn$()))
                            .interchangePTFInSettleCurr(trim(charge.getInterchangePTFInSettlementCurrency()))
                            .priceRule(trim(charge.getPricingRuleName()))
                            .priceRuleCode(trim(charge.getPricingRuleCode()))
                            .priceRuleSerialNum(trim(charge.getPricingRuleSerialNumber()))
                            .settleDate(trim(charge.getSettlementDate()))
                            .eci(trim(charge.getECI()))
                            .cavv(trim(charge.getCAVV()))
                            .networkReferenceId(trim(charge.getNetworkReferenceId()))
                            .atmInterchangeFeeIn$(trim(charge.getATMInterchangeFeeIn$()))
                            .atmSecurityFeeIn$(trim(charge.getATMSecurityFeeIn$()))
                            .atmProcessFeeIn$(trim(charge.getATMNetworkInternationalProcessingFeeIn$()))
                            .atmInterchangeFeeInSettleCurr(trim(charge.getATMInterchangeFeeInSettlementCurrency()))
                            .atmSecurityFeeInSettleCurr(trim(charge.getATMSecurityFeeInSettlementCurrency()))
                            .atmProcessFeeInSettleCurr(trim(charge.getATMNetworkInternationalProcessingFeeInSettlementCurrency()))
                            .surchargeFee(trim(charge.getSurchargeFee()))
                            .aqgeo(trim(charge.getAcquirerGeoCode()))
                            .cardProductType(trim(charge.getCardProductType()))
                            .mcc(trim(charge.getMCC()))
                            .intes(trim(charge.getINTES()))
                            .merchantId(trim(charge.getMerchantId()))
                            .cardholderPresent(trim(charge.getCardholderPresent()))
                            .captureMethod(trim(charge.getCaptureMethod()))
                            .merchantGeoCode(trim(charge.getMerchantGeoCode()))
                            .issuerGeoCode(trim(charge.getIssuerGeoCode()))
                            .merchantPan(trim(charge.getMerchantPan()))
                            .numberOfSettlementRecaps(trim(ftr.getNumberOfSettlementRecaps()))
                            .numberOfBatches(trim(ftr.getNumberOfBatches()))
                            .numberOfCharges(trim(ftr.getNumberOfCharges()))
                            .numberOfRejectedRecaps(trim(ftr.getNumberOfRejectedRecaps()))
                            .numberOfSuspendedRecaps(trim(ftr.getNumberOfSuspendedRecaps()))
                            .netSettlementAmountOfFileTrailer(trim(ftr.getNetSettlementAmount()))
                            .grossSettlementAmountOfFileTrailer(trim(ftr.getGrossSettlementAmount()))
                            .filename(filename)
                            .build();
                    ceList.add(ce);
                });
            });
        });

        //suspension record
        cf.getSuspendedRecapData().forEach(suspend -> {
            ConfirmationFile.SuspendedRecapHeaderRecord srhr = suspend.getSuspendedRecapHeaderRecord();
            suspend.getSuspensionRecords().forEach(record -> {
                ConfirmationEntity ce = ConfirmationEntity.builder()
                        .acquirerDXS_IIC(trim(fhr.getDxsCode()))
                        .acquirerISO_IIC(trim(fhr.getIIC()))
                        .versionIndicator(trim(fhr.getVersion()))
                        .originalFileDate(trim(fhr.getOriginalFileDate()))
                        .originalFileNum(trim(fhr.getOriginalFileNumber()))
                        .createTimestamp(trim(fhr.getTimestamp()))
                        .emptyFile(trim(fhr.getEmptyFileIndicator()))
                        .issuerDXS_IIC(trim(srhr.getIssuerDxsCode()))
                        .issuerISO_IIC(trim(srhr.getIssuerIIC()))
                        .recapNumber(trim(srhr.getRecapNumber()))
                        .currencyCode(trim(srhr.getCurrencyCode()))
                        .recapDate(trim(srhr.getRecapDate()))
                        .type(trim(record.getType()))
                        .batchNumber(trim(record.getBatchNumber()))
                        .sequenceNum(trim(record.getSequenceNumber()))
                        .fieldName(trim(record.getFieldName()))
                        .fieldValue(trim(record.getFieldValue()))
                        .suspensionCode(trim(record.getSuspensionCode()))
                        .suspensionMessage(trim(record.getSuspensionMessage()))
                        .numberOfSettlementRecaps(trim(ftr.getNumberOfSettlementRecaps()))
                        .numberOfBatches(trim(ftr.getNumberOfBatches()))
                        .numberOfCharges(trim(ftr.getNumberOfCharges()))
                        .numberOfRejectedRecaps(trim(ftr.getNumberOfRejectedRecaps()))
                        .numberOfSuspendedRecaps(trim(ftr.getNumberOfSuspendedRecaps()))
                        .netSettlementAmountOfFileTrailer(trim(ftr.getNetSettlementAmount()))
                        .grossSettlementAmountOfFileTrailer(trim(ftr.getGrossSettlementAmount()))
                        .filename(filename)
                        .build();
                ceList.add(ce);
            });
        });

        //rejection record
        cf.getRejectedRecapData().forEach(rejectRecap -> {
            ConfirmationFile.RejectedRecapHeaderRecord rrhr = rejectRecap.getRejectedRecapHeaderRecord();
            rejectRecap.getRejectRecapPackages().forEach(pack -> {
                ConfirmationFile.RejectedRecapErrorRecord rrer = pack.getRejectedRecapErrorRecord();
                ConfirmationEntity ce = ConfirmationEntity.builder()
                        .acquirerDXS_IIC(trim(fhr.getDxsCode()))
                        .acquirerISO_IIC(trim(fhr.getIIC()))
                        .versionIndicator(trim(fhr.getVersion()))
                        .originalFileDate(trim(fhr.getOriginalFileDate()))
                        .originalFileNum(trim(fhr.getOriginalFileNumber()))
                        .createTimestamp(trim(fhr.getTimestamp()))
                        .emptyFile(trim(fhr.getEmptyFileIndicator()))
                        .issuerDXS_IIC(trim(rrhr.getIssuerDxsCode()))
                        .issuerISO_IIC(trim(rrhr.getIssuerIIC()))
                        .recapNumber(trim(rrhr.getRecapNumber()))
                        .currencyCode(trim(rrhr.getCurrencyCode()))
                        .recapDate(trim(rrhr.getRecapDate()))
                        .type(trim(rrer.getType()))
                        .batchNumber(trim(rrer.getBatchNumber()))
                        .sequenceNum(trim(rrer.getSequenceNumber()))
                        .fieldName(trim(rrer.getFieldName()))
                        .fieldValue(trim(rrer.getFieldValue()))
                        .rejectionCode(trim(rrer.getRejectionCode()))
                        .rejectionMessage(trim(rrer.getRejectionMessage()))
                        .numberOfSettlementRecaps(trim(ftr.getNumberOfSettlementRecaps()))
                        .numberOfBatches(trim(ftr.getNumberOfBatches()))
                        .numberOfCharges(trim(ftr.getNumberOfCharges()))
                        .numberOfRejectedRecaps(trim(ftr.getNumberOfRejectedRecaps()))
                        .numberOfSuspendedRecaps(trim(ftr.getNumberOfSuspendedRecaps()))
                        .netSettlementAmountOfFileTrailer(trim(ftr.getNetSettlementAmount()))
                        .grossSettlementAmountOfFileTrailer(trim(ftr.getGrossSettlementAmount()))
                        .filename(filename)
                        .build();
                ceList.add(ce);
            });
        });

        confirmationEntityRepository.saveAll(ceList);
    }

    private String trim(String str) {
        return StringUtils.trim(str);
    }
}
