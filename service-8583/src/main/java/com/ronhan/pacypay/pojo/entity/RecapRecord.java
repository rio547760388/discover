package com.ronhan.pacypay.pojo.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/7/7
 **/
@Data
@Entity
@Table(name = "RECAP_RECORD")
public class RecapRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RECAP_NO", length = 10)
    private Integer recapNo;

    @Column(name = "SENDER", length = 10)
    private String sender;

    @Column(name = "RECEIVER", length = 10)
    private String receiver;

    @Column(name = "CURRENCY", length = 3)
    private String currency;

    @Column(name = "RECAP_DATE", length = 6)
    private String recapDate;

    @Column(name = "CREDIT_COUNT", length = 10)
    private Integer creditCount;

    @Column(name = "CREDIT_AMOUNT", precision = 16, scale = 4)
    private Double creditAmount;

    @Column(name = "DEBIT_COUNT", length = 10)
    private Integer debitCount;

    @Column(name = "DEBIT_AMOUNT", precision = 16, scale = 4)
    private Double debitAmount;

    @Column(name = "D_RATE", precision = 16, scale = 4)
    private Double dRate;

    @Column(name = "RECAP_NET_AMOUNT", precision = 16, scale = 4)
    private Double recapNetAmount;

    @Column(name = "ALTERNATE_SETTLE_CUR", length = 3)
    private String alternateSettleCur;

    @Column(name = "ALTERNATE_SETTLE_GROSS_AMOUNT", precision = 16, scale = 4)
    private Double alternateSettleGrossAmount;

    @Column(name = "ALTERNATE_SETTLE_NET_AMOUNT", precision = 16, scale = 4)
    private Double alternateSettleNetAmount;

}
