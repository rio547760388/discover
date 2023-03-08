package com.ronhan.pacypay.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/11/18
 **/
@Data
public class FxRateDto {

    private Date processingDate;

    private String sellCurrency;

    private String buyCurrency;

    private Double rate;

    private Date settlementDate;

    private Date optTime;

    private String premium;
}
