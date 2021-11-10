package com.ronhan.pacypay.pojo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/7/12
 **/
@Data
@Entity
@Table(name = "CYCLE_RANGE")
public class CycleRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CYCLE_RANGE", length = 10)
    private String cycleRange;

    @Column(name = "ISSUER_NAME", length = 100)
    private String issuerName;

    @Column(name = "ISO_IIC", length = 20)
    private String isoIIC;

    @Column(name = "DXS_IIC", length = 10)
    private String dxsIIC;

    @Column(name = "COUNTRY_CODE", length = 3)
    private String countryCode;

    @Column(name = "PAN_LENGTH", length = 2)
    private Integer panLength;

    @Column(name = "CURRENCY_NUMERIC", length = 3)
    private String currencyNumeric;
}
