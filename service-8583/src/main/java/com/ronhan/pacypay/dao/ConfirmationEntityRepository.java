package com.ronhan.pacypay.dao;

import com.ronhan.pacypay.pojo.entity.ConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/9/2
 **/
@Repository
public interface ConfirmationEntityRepository extends JpaRepository<ConfirmationEntity, Long> {
}
