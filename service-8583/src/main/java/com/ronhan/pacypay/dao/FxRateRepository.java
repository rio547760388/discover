package com.ronhan.pacypay.dao;

import com.ronhan.pacypay.pojo.entity.FxRate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/9/30
 **/
public interface FxRateRepository extends JpaRepository<FxRate, Long> {
}
