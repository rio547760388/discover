package com.ronhan.pacypay.pojo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/7/7
 **/
@Data
@Entity
@Table(name = "BATCH_RECORD")
public class BatchRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RECAP_NO", length = 10)
    private String recapNo;

    @Column(name = "BATCH_NO", length = 10)
    private String batchNo;

    @Column(name = "CREDIT_COUNT", length = 10)
    private Integer creditCount;

    @Column(name = "CREDIT_AMOUNT", precision = 16, scale = 4)
    private Double creditAmount;

    @Column(name = "DEBIT_COUNT", length = 10)
    private Integer debitCount;

    @Column(name = "DEBIT_AMOUNT", precision = 16, scale = 4)
    private Double debitAmount;

    @Column(name = "RECAP_ID")
    private Long recapId;
}
