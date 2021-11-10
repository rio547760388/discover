package com.ronhan.pacypay.dao;

import com.ronhan.pacypay.pojo.entity.RecapRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/7/13
 **/
@Repository
public interface RecapRecordRepository extends JpaRepository<RecapRecord, Long> {
}
