package com.ronhan.pacypay.pojo;

import lombok.Data;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/11/17
 **/
@Data
public class FxRateQuery {

    private String sellCurrency;

    private String buyCurrency;

    private String optTimeStart;

    private String optTimeEnd;

    private long pageSize = 10;

    private long pageNum = 1;
}
