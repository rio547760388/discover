package com.ronhan.pacypay.service.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ronhan.iso8583.discover.sftp.files.Recap;
import com.ronhan.pacypay.dao.BatchDetailRepository;
import com.ronhan.pacypay.dao.BatchRecordRepository;
import com.ronhan.pacypay.dao.RecapRecordRepository;
import com.ronhan.pacypay.dao.TransRecordRepository;
import com.ronhan.pacypay.pojo.entity.*;
import com.ronhan.pacypay.service.TransRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/29
 **/
@Service
public class TransRecordServiceImpl implements TransRecordService {
    @Autowired
    private TransRecordRepository transRecordRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private RecapRecordRepository recapRecordRepository;

    @Autowired
    private BatchRecordRepository batchRecordRepository;

    @Autowired
    private BatchDetailRepository batchDetailRepository;

    @Override
    public void save(TransRecord transRecord) {
        transRecordRepository.save(transRecord);
    }

    @Override
    public List<Tuple> countUnSettledTrans(LocalDateTime start, LocalDateTime end) {
        QTransRecord q = QTransRecord.transRecord;
        return queryFactory.select(q.issuerDxs, q.count())
                .from(q)
                .where(q.importTime.goe(start)
                        .and(q.importTime.loe(end))
                        .and(q.transStatus.eq(1))
                        .and(q.settled.eq(0))
                        .and(q.captured.eq(1)))
                .groupBy(q.issuerDxs)
                .fetch();
    }

    @Override
    public List<TransRecord> getTrans(LocalDateTime start, LocalDateTime end, String dxs, String cur, long offset, long limit) {
        QTransRecord q = QTransRecord.transRecord;

        return queryFactory.select(q)
                .from(q)
                .where(q.importTime.goe(start)
                        .and(q.importTime.loe(end))
                        .and(q.transStatus.eq(1))
                        .and(q.settled.eq(0))
                        .and(StringUtils.isEmpty(dxs) ? q.issuerDxs.isNull() : q.issuerDxs.eq(dxs))
                        //.and(q.currency.eq(cur)))
                ).orderBy(new OrderSpecifier<>(Order.ASC, q.id))
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTrans(List<TransRecord> transRecords) {
        if (transRecords.size() > 0) {
            List<Long> ids = transRecords.stream().map(TransRecord::getId).collect(Collectors.toList());
            QTransRecord q = QTransRecord.transRecord;

            int times = ids.size() % 100 != 0 ? ids.size() / 100 + 1 : ids.size() / 100;
            for (int i = 0; i < times; i++) {
                int from = i * 100;
                int cnt = Math.min(100, ids.size() - i * 100);
                queryFactory.update(q)
                        .set(q.settled, 1)
                        .where(q.id.in(ids.subList(from, from + cnt)))
                        .execute();
            }
        }
    }

    @Override
    public CycleRange getCardCycleRange(String cycleRange) {
        QCycleRange q = QCycleRange.cycleRange1;
        return queryFactory.select(q)
                .from(q)
                .where(q.cycleRange.eq(cycleRange))
                .fetchOne();
    }

    @Override
    public TransRecord getOriginalTrans(String uniqueId) {
        QTransRecord q = QTransRecord.transRecord;
        return queryFactory.select(q)
                .from(q)
                .where(q.uniqueId.eq(uniqueId))
                .fetchOne();
    }

    @Override
    public Integer getRecapNo(String sender, String receiver) {
        QRecapRecord q = QRecapRecord.recapRecord;
        return queryFactory.select(q.recapNo)
                .from(q)
                .where(q.sender.eq(sender).and(q.receiver.eq(receiver)))
                .orderBy(new OrderSpecifier<>(Order.DESC, q.id))
                .limit(1)
                .fetchOne();
    }

    @Override
    public RecapRecord saveRecap(Recap.RecapHeader recapHeader, Recap.RecapTrailer recapTrailer) {
        RecapRecord recapRecord = new RecapRecord();
        recapRecord.setRecapNo(Integer.parseInt(recapTrailer.getRCPNO()));
        recapRecord.setSender(recapTrailer.getSFTER());
        recapRecord.setReceiver(recapTrailer.getDFTER());
        recapRecord.setCurrency(recapHeader.getCURKY());
        recapRecord.setRecapDate(recapHeader.getRCPDT());
        recapRecord.setCreditCount(Integer.parseInt(recapTrailer.getRCNCR()));
        recapRecord.setCreditAmount(Double.parseDouble(recapTrailer.getRCACR()));
        recapRecord.setDebitCount(Integer.parseInt(recapTrailer.getRCNDR()));
        recapRecord.setDebitAmount(Double.parseDouble(recapTrailer.getRCADR()));
        recapRecord.setDRate(Double.parseDouble(recapTrailer.getDRATE()));
        recapRecord.setRecapNetAmount(Double.parseDouble(recapTrailer.getRNAMT()));
        if (recapTrailer.getACRKY() != null) {
            recapRecord.setAlternateSettleCur(recapTrailer.getACRKY());
        }
        if (recapTrailer.getAGAMT() != null) {
            recapRecord.setAlternateSettleGrossAmount(Double.parseDouble(recapTrailer.getAGAMT()));
        }
        if (recapTrailer.getACAMT() != null) {
            recapRecord.setAlternateSettleNetAmount(Double.parseDouble(recapTrailer.getACAMT()));
        }
        return recapRecordRepository.save(recapRecord);
    }

    @Override
    public BatchRecord saveBatch(Recap.BatchTrailer batch, RecapRecord recapRecord) {
        BatchRecord batchRecord = new BatchRecord();
        batchRecord.setRecapNo(batch.getRCPNO());
        batchRecord.setBatchNo(batch.getBATCH());
        batchRecord.setCreditCount(Integer.parseInt(batch.getBTNCR()));
        batchRecord.setCreditAmount(Double.parseDouble(batch.getBTACR()));
        batchRecord.setDebitCount(Integer.parseInt(batch.getBTNDR()));
        batchRecord.setDebitAmount(Double.parseDouble(batch.getBTADR()));
        batchRecord.setRecapId(recapRecord.getId());
        return batchRecordRepository.save(batchRecord);
    }

    @Override
    public void saveBatchDetail(List<Recap.ChargeDetail> detail, BatchRecord batchRecord) {
        List<BatchDetail> details = detail.stream()
                .map(e -> BatchDetail.builder()
                        .batchRecordId(batchRecord.getId())
                        .transId(e.getTransId())
                        .recapNo(e.getRCPNO())
                        .batchNo(e.getBATCH())
                        .sequenceNo(e.getSEQNO())
                        .cardNo(e.getACCT())
                        .chargeAmount(parseD(e.getCAMTR()))
                        .chargeDate(e.getCHGDT())
                        .dateType(e.getDATYP())
                        .chargeType(e.getCHTYP())
                        .memberName(e.getESTAB())
                        .memberCity(e.getLCITY())
                        .geocode(e.getGEOCD())
                        .actionCode(e.getAPPCD())
                        .typeCharge(e.getTYPCH())
                        .referenceNo(e.getREFNO())
                        .approvalCode(e.getANBR())
                        .acquirerUniqueReferenceID(e.getSENUM())
                        .establishmentCode(e.getINTES())
                        .establishmentStreet(e.getESTST())
                        .establishmentSCP(e.getESTCO())
                        .establishmentZip(e.getESTZP())
                        .establishmentPhone(e.getESTPN())
                        .mcc(e.getMCCCD())
                        .tax1(parseD(e.getTAX1()))
                        .tax2(parseD(e.getTAX2()))
                        .originalTicketNumber(e.getORIGD())
                        .cardholderPresent(e.getCHOLDP())
                        .cardPresent(e.getCARDP())
                        .cardDataInputMethod(e.getCPTRM())
                        .eci(e.getECI())
                        .cavv(e.getCAVV())
                        .referenceId(e.getNRID())
                        .cardDataInputCapacity(e.getCRDINP())
                        .surchargeFee(e.getSURFEE())
                        .terminalType(e.getTRMTYP())
                        .acquirerGeocode(e.getAQGEO())
                        .build())
                .collect(Collectors.toList());
        batchDetailRepository.saveAll(details);
    }

    @Override
    public long countTransByUniqueId(String uniqueId) {
        QTransRecord q = QTransRecord.transRecord;
        return queryFactory.select(q.count())
                .from(q)
                .where(q.uniqueId.eq(uniqueId))
                .fetchCount();
    }

    @Override
    public TransRecord getTransByDiscoverUniqueId(String localTransTime, String traceNumber) {
        QTransRecord q = QTransRecord.transRecord;
        return queryFactory.select(q)
                .from(q)
                .where(q.transactionTime.eq(localTransTime).and(q.traceNumber.eq(traceNumber)))
                .fetchOne();
    }

    private Double parseD(String d) {
        try {
            return Double.parseDouble(d);
        } catch (Exception e) {
            return null;
        }
    }
}
