package com.ronhan.pacypay.service;

import com.ronhan.pacypay.pojo.Reconciliation;

import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/12/10
 **/
public interface ReconciliationService {
    List<Reconciliation> fetchData(String start, String end);
}
