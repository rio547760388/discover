package com.ronhan.pacypay.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/7/7
 **/
@Data
@Entity
@Table(name = "BATCH_DETAIL")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * field 3
     */
    @Column(name = "RECAP_NO", length = 10)
    private String recapNo;

    /**
     * field 5
     */
    @Column(name = "BATCH_NO", length = 10)
    private String batchNo;

    /**
     * field 6
     */
    @Column(name = "SEQUENCE_NO", length = 10)
    private String sequenceNo;

    /**
     * field 7
     */
    @Column(name = "CARD_NO", length = 32)
    private String cardNo;

    /**
     * field 8
     */
    @Column(name = "CHARGE_AMOUNT", precision = 16, scale = 4)
    private Double chargeAmount;

    /**
     * field 9
     */
    @Column(name = "CHARGE_DATE", length = 10)
    private String chargeDate;

    /**
     * field 10
     */
    @Column(name = "DATE_TYPE", length = 10)
    private String dateType;

    /**
     * field 11
     */
    @Column(name = "CHARGE_TYPE", length = 10)
    private String chargeType;

    /**
     * field 12
     */
    @Column(name = "MEMBER_NAME", length = 100)
    private String memberName;

    /**
     * field 13
     */
    @Column(name = "MEMBER_CITY", length = 100)
    private String memberCity;

    /**
     * field 14
     */
    @Column(name = "GEOCODE", length = 10)
    private String geocode;

    /**
     * field 15
     */
    @Column(name = "ACTION_CODE", length = 10)
    private String actionCode;

    /**
     * field 16
     */
    @Column(name = "TYPE_CHARGE", length = 10)
    private String typeCharge;

    /**
     * field 17
     */
    @Column(name = "REFERENCE_NO", length = 32)
    private String referenceNo;

    /**
     * field 18
     */
    @Column(name = "APPROVAL_CODE", length = 10)
    private String approvalCode;

    /**
     * field 19
     */
    @Column(name = "ACQUIRER_UNIQUE_REFERENCE_ID", length = 32)
    private String acquirerUniqueReferenceID;

    /**
     * 22
     */
    @Column(name = "ESTABLISHMENT_CODE", length = 10)
    private String establishmentCode;

    /**
     * field 23
     */
    @Column(name = "ESTABLISHMENT_STREET", length = 100)
    private String establishmentStreet;

    /**
     * field 24
     */
    @Column(name = "ESTABLISHMENT_SCP", length = 50)
    private String establishmentSCP;

    /**
     * field 25
     */
    @Column(name = "ESTABLISHMENT_ZIP", length = 20)
    private String establishmentZip;

    /**
     * field 26
     */
    @Column(name = "ESTABLISHMENT_PHONE", length = 20)
    private String establishmentPhone;

    /**
     * field 28
     */
    @Column(name = "MCC", length = 10)
    private String mcc;

    /**
     * field 29
     */
    @Column(name = "TAX1", precision = 16, scale = 4)
    private Double tax1;

    /**
     * field 30
     */
    @Column(name = "TAX2", precision = 16, scale = 4)
    private Double tax2;

    /**
     * field 31
     */
    @Column(name = "ORIGINAL_TICKET_NUMBER", length = 20)
    private String originalTicketNumber;

    /**
     * field 38
     */
    @Column(name = "CARDHOLDER_PRESENT", length = 1)
    private String cardholderPresent;

    /**
     * field 39
     */
    @Column(name = "CARD_PRESENT", length = 1)
    private String cardPresent;

    /**
     * field 40
     */
    @Column(name = "CARD_DATA_INPUT_METHOD", length = 1)
    private String cardDataInputMethod;

    /**
     * filed 41
     */
    @Column(name = "ECI", length = 1)
    private String eci;

    /**
     * field 42
     */
    @Column(name = "CAVV", length = 10)
    private String cavv;

    /**
     * field 43
     */
    @Column(name = "NETWORK_REFERENCE_ID", length = 20)
    private String referenceId;

    /**
     * filed 44
     */
    @Column(name = "CARD_DATA_INPUT_CAPACITY", length = 1)
    private String cardDataInputCapacity;

    /**
     * field 45
     */
    @Column(name = "SURCHARGE_FEE", length = 20)
    private String surchargeFee;

    /**
     * filed 46
     */
    @Column(name = "TERMINAL_TYPE", length = 1)
    private String terminalType;

    /**
     * field 47
     */
    @Column(name = "ACQUIRER_GEOCODE", length = 3)
    private String acquirerGeocode;

    /**
     * TRANS_RECORD.ID
     */
    @Column(name = "TRANS_ID")
    private Long transId;

    @Column(name = "BATCH_RECORD_ID")
    private Long batchRecordId;
}
