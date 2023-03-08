package com.ronhan.pacypay.service;

import com.querydsl.core.Tuple;
import com.ronhan.iso8583.discover.sftp.files.Recap;
import com.ronhan.pacypay.pojo.entity.BatchRecord;
import com.ronhan.pacypay.pojo.entity.CycleRange;
import com.ronhan.pacypay.pojo.entity.RecapRecord;
import com.ronhan.pacypay.pojo.entity.TransRecord;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/29
 **/
public interface TransRecordService {

    /**
     * 保存交易记录
     * @param transRecord
     */
    void save(TransRecord transRecord);

    /**
     *
     * @param start
     * @param end
     * @return
     */
    List<Tuple> countUnSettledTrans(LocalDateTime start, LocalDateTime end);

    /**
     * electronic interchange
     * @param start
     * @param end
     * @return
     */
    List<TransRecord> getTrans(LocalDateTime start, LocalDateTime end, String dxs, long offset, long limit, String... cur);

    /**
     * updateTrans
     * @param transRecords
     */
    void updateTrans(List<TransRecord> transRecords);

    /**
     * 返回银行卡bin
     * @param cycleRange
     * @return
     */
    CycleRange getCardCycleRange(String cycleRange);

    /**
     * 返回待退款订单
     * @param uniqueId
     * @return
     */
    TransRecord getOriginalTrans(String uniqueId);

    /**
     * 返回recap no
     * @param sender
     * @param receiver
     * @return
     */
    Integer getRecapNo(String sender, String receiver);

    /**
     * save recap
     * @param recapHeader
     * @param recapTrailer
     * @return
     */
    RecapRecord saveRecap(Recap.RecapHeader recapHeader, Recap.RecapTrailer recapTrailer);

    /**
     * save batch
     * @param batch
     * @param recapRecord
     * @return
     */
    BatchRecord saveBatch(Recap.BatchTrailer batch, RecapRecord recapRecord);

    /**
     * save detail
     * @param detail
     * @param batchRecord
     */
    void saveBatchDetail(List<Recap.ChargeDetail> detail, BatchRecord batchRecord);

    /**
     * 查询重复订单
     * @param uniqueId
     * @return
     */
    long countTransByUniqueId(String uniqueId);

    /**
     * 通过发送给discover的联合唯一索引查询交易
     * @param localTransTime
     * @param traceNumber
     * @return
     */
    TransRecord getTransByDiscoverUniqueId(String localTransTime, String traceNumber);
}
