package com.ronhan.pacypay.dao;

import com.ronhan.pacypay.pojo.entity.TransRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/29
 **/
@Repository
public interface TransRecordRepository extends JpaRepository<TransRecord, Long> {
}
