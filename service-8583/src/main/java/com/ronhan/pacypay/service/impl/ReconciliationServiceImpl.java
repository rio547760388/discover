package com.ronhan.pacypay.service.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ronhan.pacypay.dao.ConfirmationEntityRepository;
import com.ronhan.pacypay.pojo.Reconciliation;
import com.ronhan.pacypay.pojo.entity.ConfirmationEntity;
import com.ronhan.pacypay.pojo.entity.QBatchDetail;
import com.ronhan.pacypay.pojo.entity.QConfirmationEntity;
import com.ronhan.pacypay.pojo.entity.QTransRecord;
import com.ronhan.pacypay.service.ReconciliationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/12/10
 **/
@Service
public class ReconciliationServiceImpl implements ReconciliationService {
    @Autowired
    private ConfirmationEntityRepository confirmationEntityRepository;

    @Autowired
    private JPAQueryFactory factory;

    @Override
    public List<Reconciliation> fetchData(String start, String end) {
        QConfirmationEntity q = QConfirmationEntity.confirmationEntity;
        QTransRecord qt = QTransRecord.transRecord;
        QBatchDetail qd = QBatchDetail.batchDetail;

        List<Reconciliation> reconciliationList = new ArrayList<>();

        List<Tuple> list = factory.select(q, qt.uniqueId)
                .from(q, qt, qd)
                .where(q.networkReferenceId.eq(qd.referenceId)
                        .and(q.recapNumber.eq(qd.recapNo))
                        .and(q.batchNumber.eq(qd.batchNo))
                        .and(q.sequenceNum.eq(qd.sequenceNo))
                        .and(q.referenceNum.eq(qd.referenceNo))
                        .and(qt.id.eq(qd.transId))
                        .and(q.recapDate.between(start, end)))
                .fetch();

        list.forEach(e -> {
            ConfirmationEntity ce = e.get(0, ConfirmationEntity.class);
            String uniqueId = e.get(qt.uniqueId);
            Reconciliation r = new Reconciliation();
            BeanUtils.copyProperties(ce, r);
            r.setUniqueId(uniqueId);

            reconciliationList.add(r);
        });

        return reconciliationList;
    }
}
