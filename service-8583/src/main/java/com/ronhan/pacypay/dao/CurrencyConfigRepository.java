package com.ronhan.pacypay.dao;

import com.ronhan.pacypay.pojo.entity.CurrencyConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2022/7/7
 **/
public interface CurrencyConfigRepository extends JpaRepository<CurrencyConfig, Long> {
}
