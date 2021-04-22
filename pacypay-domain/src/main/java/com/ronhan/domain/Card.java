package com.ronhan.domain;

import lombok.Data;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/8
 **/
@Data
public class Card {

    private Long number;

    private String expireYear;

    private String expireMonth;

    private String cvv;
}
