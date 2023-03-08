package com.ronhan.pacypay.pojo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2022/7/6
 **/
@Table(name = "currency_config")
@Entity
@Data
public class CurrencyConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trans_currency", length = 3)
    private String transCurrency;

    @Column(name = "settlement_currency", length = 3)
    private String settlementCurrency;

    @Column(name = "default_settlement_currency", length = 1)
    private Integer defaultSettlementCurrency;
}
