package com.ronhan.iso8583.discover.sftp.files;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/11
 **/
@Data
public class Recap {

    @Getter
    @Validated
    public static class RecapHeader {

        @NotBlank
        private String TRANS = "FRRC";

        @NotBlank
        private String FUNCD = "UX";

        @NotBlank
        private String SFTER;

        @NotBlank
        private String RCPNO;

        @NotBlank
        private String DFTER;

        @NotBlank
        private String CURKY;

        @NotBlank
        private String RCPDT;

        private String STLCUR;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.DFTER = str;
        }

        public void setCURKY(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.CURKY = str;
        }

        public void setRCPDT(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RCPDT = str;
        }

        public void setSTLCUR(String str) {
            if (str != null  && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.STLCUR = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(CURKY, "") + '>'
                    + Objects.toString(RCPDT, "") + '>'
                    + Objects.toString(STLCUR, "");
        }
    }

    @Getter
    @Validated
    public static class BatchHeader {
        @NotBlank
        private String TRANS = "FRRC";

        @NotBlank
        private String FUNCD = "UH";

        @NotBlank
        private String SFTER;

        @NotBlank
        private String RCPNO;

        @NotBlank
        private String DFTER;

        @NotBlank
        private String BATCH;

        @NotBlank
        private String RCPDT;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setRCPDT(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RCPDT = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(RCPDT, "");
        }
    }

    @Getter
    @Validated
    public static class ChargeDetail {
        /**
         * Transaction Code. To Diners Club: FRRC
         */
        @NotBlank
        private String TRANS = "FRRC";

        /**
         * Function Code XD
         */
        @NotBlank
        private String FUNCD = "XD";

        /**
         * Sending Institution Identification Code
         */
        @NotBlank
        private String SFTER;

        /**
         * Recap Number
         */
        @NotBlank
        private String RCPNO;

        /**
         * Receiving Institution Identification Code
         */
        @NotBlank
        private String DFTER;

        /**
         * Batch Number
         */
        @NotBlank
        private String BATCH;

        /**
         * Sequence within the batch
         */
        @NotBlank
        private String SEQNO;

        /**
         * Card Number used at merchant. This may be a Digital Token, Virtual Card
         * or Card Number (PAN).
         * Note: If tokenized transactions (transactions using a Digital Token or a
         * Virtual Card) cannot be mapped to a Real Card Number (PAN), the recap
         * containing the tokenized transaction will be rejected.
         */
        @NotBlank
        private String ACCT;

        /**
         * Charge Amount. In currency of charge
         */
        @NotBlank
        private String CAMTR;

        /**
         * Charge date YYMMDD
         */
        @NotBlank
        private String CHGDT;

        /**
         * Date Type. ‘TS’ if DE 9 is the merchant–provided Sale Date; ‘TP’ if DE 9
         * contains the Processing Date (merchant did not provide Sale Date)
         */
        @NotBlank
        private String DATYP;

        /**
         * Charge Type (DXS format). Refer to the Data Tables available on DCI
         * InfoNet.
         */
        @NotBlank
        private String CHTYP;

        /**
         * Member Establishment Name
         */
        @NotBlank
        private String ESTAB;

        /**
         * Member Establishment City
         */
        @NotBlank
        private String LCITY;

        /**
         * Geographic Area Code. Refer to Data Tables available on DCI InfoNet
         * (see GEOCODES document - ISO GEO Code Numeric column).
         */
        @NotBlank
        private String GEOCD;

        /**
         * Action Code. Refer to the DCI Authorizations Transaction Manual. Xpress
         * DE 39—Action Code
         */
        private String APPCD;

        /**
         * Type of Charge. Indicates debit or credit; and charge acquisition—paper
         * backup; electronically; or internet.
         */
        @NotBlank
        private String TYPCH;

        /**
         * Reference Number. Uniquely identifies the charge for the Acquirer.
         */
        @NotBlank
        private String REFNO;

        /**
         * Authorization Number must be provided if present in Xpress Authorization
         * Response message DE 38—Approval Code
         */
        private String ANBR;

        /**
         * Member Establishment Number. This is the acquirer's unique reference
         * for their merchant. For Merchant Presented Mode (MPM) QR Code
         * Transactions, this will be the Merchant Primary Account Number (MPAN)
         */
        @NotBlank
        private String SENUM;

        /**
         * For DCI Use Only
         */
        private String BLCUR;

        /**
         * For DCI Use Only.
         */
        private String BLAMT;

        /**
         * International Establishment Code. Refer to the Data Tables available on
         * DCI InfoNet. The system amends invalid codes to null. Must be present, if
         * the Merchant has been assigned an INTES Code
         */
        private String INTES;

        /**
         * Establishment Street Address
         */
        @NotBlank
        private String ESTST;

        /**
         * Establishment State/County/Province
         */
        private String ESTCO;

        /**
         * Establishment Zip Code
         */
        private String ESTZP;

        /**
         * Establishment Phone Number
         */
        private String ESTPN;

        /**
         * Merchant Specific Code (reserved for future use)
         */
        private String MSCCD;

        /**
         * Merchant Classification Code—Refer to Data Tables available on DCI
         * InfoNet (see MCC ABC Mapping document - MCC column).
         */
        @NotBlank
        private String MCCCD;

        /**
         * Tax 1 Amount (in Currency of charge)
         */
        private String TAX1;

        /**
         * Tax 2 Amount (in Currency of charge)
         */
        private String TAX2;

        /**
         * Original Ticket or Document Number. Used for refund transactions, etc.
         */
        private String ORIGD;

        /**
         * Customer Reference Number (e.g., Employee ID, Cost Code, etc.)
         */
        private String CUSRF1;

        /**
         * Customer Reference Number2 (e.g., Employee ID, Cost Code, etc.).
         */
        private String CUSRF2;

        /**
         * Customer Reference Number3 (e.g., Employee ID, Cost Code, etc.).
         */
        private String CUSRF3;

        /**
         * Customer Reference Number4 (e.g., Employee ID, Cost Code, etc.).
         */
        private String CUSRF4;

        /**
         * Customer Reference Number5 (e.g., Employee ID, Cost Code, etc.).
         */
        private String CUSRF5;

        /**
         * Customer Reference Number6 (e.g., Employee ID, Cost Code, etc.).
         */
        private String CUSRF6;

        /**
         * Card Holder Present Indicator obtained in Xpress Authorization (Move
         * Position 5 of Xpress DE 22 to this field)
         * Valid Values are:
         * Value Meaning
         * 0 Cardholder present
         * 1 Cardholder not present—unspecified reason
         * 2 Cardholder not present—mail order request
         * 3 Cardholder not present—telephone request
         * 4 Cardholder not present—standing order/Merchant-Initiated
         * Transactions (such as Recurring Payments)
         * 9 Cardholder not present—Internet
         * S Unknown if Cardmember present
         */
        private String CHOLDP;

        /**
         * Card Present Indicator obtained in Xpress Authorization (Move Position 6
         * of Xpress DE 22 to this field)
         * Valid values are:
         * Value Meaning
         * 0 The card was not present.
         * 1 The card was present.
         * 8 Unknown
         */
        private String CARDP;

        /**
         * Card Input Data Method used in Xpress Authorization (Move Position 7 of
         * Xpress DE 22 to this field).
         * Valid values are:
         * Value Meaning
         * 0 Unspecified
         * 1 Manual entry
         * 2 Magnetic stripe read
         * 3 Bar code
         * 4 OCR
         * 5 ICC
         * 6 Key entered
         * 9 PAN captured from magnetic stripe of a chip card “Chip Fallback”
         * B Stored Card Account
         * S RFID - Chip Card Data
         * T RFID—Magnetic Stripe Data
         * U Contactless Interface Change
         * V mCommerce
         */
        private String CPTRM;

        /**
         * Electronic Commerce and Payments Indicator populated with the same
         * value from Xpress Authorization Data Element 122, Position 2.
         * Valid values are:
         * Value Meaning
         * 4 In-App Authentication
         * 5 Secure E-commerce transaction
         * 6 Non-authenticated w/merchant attempt
         * 7 Non-authenticated transaction
         * 8 Non-secure transaction
         * 9 Reserved for future use
         */
        private String ECI;

        /**
         * CAVV value populated with the same value from Xpress Authorization
         * Data Element 122, Positions 9–12
         */
        private String CAVV;

        /**
         * Network Reference ID (NRID) created during Authorizations and this
         * should be the same value as provided in Xpress Authorization DE 123 of
         * the 1110 Authorization Response Message or 1130 Authorization Advice
         * Response Message
         */
        private String NRID;

        /**
         * Card Data Input Capability indicator from Xpress Authorization Data
         * Element 22, position 1 as provided in the 1100 or 1200 Authorization
         * Request Message
         * Valid values in Position 1:
         * Value Meaning
         * 0 Unknown entry point
         * 1 Manual entry
         * 2 Magnetic stripe read
         * 5 ICC Read
         * 6 Key entered at POS
         * 8 Contactless
         * 9 Hybrid
         */
        private String CRDINP;

        /**
         * Surcharge Fee as with the same value as provided in Xpress
         * Authorization DE46, positions 2–11 as provided in the 1100 Authorization
         * Request Message.
         * See Data Dictionary for format rules as SURFEE has a different format to
         * DE46.
         */
        private String SURFEE;

        /**
         * POS Terminal Type indicator from Authorization Request Message DE 62
         * DF01 tag value.
         * Valid values in Position 1:
         * Value Meaning
         * M Mobile POS
         */
        private String TRMTYP;

        /**
         * Acquirer Geographic Area Code. This must be the Geographic Area Code
         * where the acquirer of this charge detail, ISO Standard 3166, is located.
         * Refer to Data Tables available on DCI InfoNet (see GEOCODES
         * document - ISO GEO Code Numeric column).
         */
        @NotBlank
        private String AQGEO;

        /**
         * CAVV Validation Result
         * From authorization DE 44 Position 6-7 as provided in
         * the authorization message
         * 01=CAVV passed verification- authentication
         * 02=CAVV failed verification -authentication
         * 03=CAVV passed validation—attempt
         * 04=CAVV failed validation—attempt
         * 05=CAVV not validated, issuer not participating in
         * CAVV validation
         * 06=CAVV Unable to perform authentication
         */
        private String CVVRST;

        /**
         * Authentication Data Type
         * 2 - ProtectBuy
         */
        private String AUTYP;

        /**
         * ProtectBuy Authentication Result
         * Code
         * 00 - Authentication successful (Status Y)
         * 05 - Authentication could not be performed (Status U)
         * 07 - Acquirer attempt (status A)
         * (proof of authentication attempt
         * generated for non-participating
         * issuer or cardholder)
         * 08 - Acquirer attempt, issuer ACS not available (Status A) proof of
         * authentication attempt generated for participating issuer with server
         * unavailable
         * 09 - Authentication failed (status N)
         */
        private String AURCDE;

        /**
         * ProtectBuy Second Factor
         * Authentication Result Code
         * 00 - 3DS 1.0.2 or prior, All authentication methods No second factor
         * authentication
         * 01 - 3DS 2.0 Challenge flow using static passcode
         * 02 - 3DS 2.0 Challenge flow using one time passcode (OTP*) via
         * SMS method
         * 03 - 3DS 2.0 Challenge flow using OTP* via key fob or card
         * reader method
         * 04 – 3DS 2.0 Challenge flow using OTP* via app method
         * 05 – 3DS 2.0 Challenge flow using OTP* via any other method
         * 06 – 3DS 2.0 Challenge flow using knowledge-based authentication
         * (KBA) method
         * 07 – 3DS 2.0 Challenge flow using out of band (OOB**) authentication
         * with biometric method
         * 08 - 3DS 2.0 Challenge flow using OOB authentication with App login
         * method
         * 09’– 3DS 2.0 Challenge flow using OOB authentication with any
         * other method
         * 10 – 3DS 2.0 Challenge flow using any other authentication
         * method
         * 11 - Reserved for future use for D-PAS
         * 12 - Reserved for future use for D-PAS
         * 97 – 3DS 2.0 frictionless flow risk-based authentication (RBA***) review
         * 98 – 3DS 2.0 Attempts server responding
         * 99 – 3DS 2.0 frictionless flow, RBA***
         * 3DS = 3-D Secure
         * * OTP = one time pass code
         * ** OOB = Out-of-band
         * ***RBA = risk-based
         * authentication
         */
        private String SECFAR;

        /**
         * ProtectBuy CAVV Indicator
         * 01 - CAVV key set 1
         * 02- CAVV key set 2
         * (Certification/Test)
         * 03 - 09 Reserved
         * 10 - Attempt server Discover key
         * #1(Prod)
         * 11 - Attempt server Discover key
         * #2(Test)
         * 12 - 99 Reserved
         */
        private String CVVIND;

        /**
         * ProtectBuy Authentication Tracking Number
         * This is a 16-digit code generated
         * by the issuer's ACS or Attempt
         * ACS to identify the transaction.
         */
        private String AUTHTR;

        /**
         * ProtectBuy Version and Authentication Action
         * This is a 2-digit code, first digit identifies a version; the second digit
         * identifies the
         * authentication action.
         * First Digit:
         * 0 - Authentication action and cardholder IP address not present.
         * 1 - Authentication action and cardholder IP address present.
         * Second Digit:
         * 0 - Standard authentication performed (no ADS or FYP performed).
         * 1 - ADS-registration authentication performed.
         * 2 - Forgot your password (FYP)-re-registration/re-authorization
         * performed.
         */
        private String VERACT;

        /**
         * ProtectBuy IP Address in Hex Format
         * These positions identify the client
         * IP address submitted in the authorization message from ACS.
         */
        private String IPADDR;

        /**
         * SCA Exemption Indicator
         * Populated with the same value from Xpress Authorization DE
         * 106-Dataset 67-Tag 01
         * Indicates that a Card Transaction is exempt from, or otherwise outside the
         * jurisdiction of PSD2 SCA requirements due to one of the reasons
         * provided:
         * 01 - Low Value Amount Transaction
         * 02 -Delegated Authentication
         * 03 - Secure Corporate Transaction
         * 04 - Transaction Risk Analysis (TRA)
         */
        private String SCAEXE;

        /**
         * Transaction Indicator
         * Populated with the same value from Xpress Authorization DE 62 Tag
         * DF05 (used for Merchant-Initiated Transactions)
         * A - Re-authorize for Full Amount
         * D - Delayed Card Sale
         * E - Resubmission of Card Sale
         * G - Transit Aggregated Transaction
         * I - Incremental Authorization
         * N- No-Show Charge
         * P - Partial/Split Shipment
         * R - Recurring Payment
         * S - Installment Payment
         * U - Unscheduled Payment
         */
        private String TRAIND;

        /**
         * Original network reference ID
         * Populated with the same value from Xpress Authorization DE
         * 106-Dataset 64-Tag 01
         */
        private String ORNRID;

        /**
         * TradeRecord.id
         */
        @Getter
        @Setter
        private Long transId;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setACCT(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 19) {
                throw new IllegalArgumentException(str + " length > 19");
            }
            this.ACCT = str;
        }

        public void setCAMTR(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 16) {
                throw new IllegalArgumentException(str + " length > 16");
            }
            this.CAMTR = str;
        }

        public void setCHGDT(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.CHGDT = str;
        }

        public void setDATYP(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DATYP = str;
        }

        public void setCHTYP(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.CHTYP = str;
        }

        public void setESTAB(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 36) {
                throw new IllegalArgumentException(str + " length > 36");
            }
            this.ESTAB = str;
        }

        public void setLCITY(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 26) {
                throw new IllegalArgumentException(str + " length > 26");
            }
            this.LCITY = str;
        }

        public void setGEOCD(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.GEOCD = str;
        }

        public void setAPPCD(String str) {
            if (str != null  && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.APPCD = str;
        }

        public void setTYPCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.TYPCH = str;
        }

        public void setREFNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 8) {
                throw new IllegalArgumentException(str + " length > 8");
            }
            this.REFNO = str;
        }

        public void setANBR(String str) {
            if (str != null  && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.ANBR = str;
        }

        public void setSENUM(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 19) {
                throw new IllegalArgumentException(str + " length > 19");
            }
            this.SENUM = str;
        }

        public void setINTES(String str) {
            if (str != null  && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.INTES = str;
        }

        public void setESTST(String str) {
            //Objects.requireNonNull(str);
            if (str != null && str.length() > 35) {
                throw new IllegalArgumentException(str + " length > 35");
            }
            this.ESTST = str;
        }

        public void setESTCO(String str) {
            if (str != null  && str.length() > 20) {
                throw new IllegalArgumentException(str + " length > 20");
            }
            this.ESTCO = str;
        }

        public void setESTZP(String str) {
            if (str != null && str.length() > 11) {
                throw new IllegalArgumentException(str + " length > 11");
            }
            this.ESTZP = str;
        }

        public void setESTPN(String str) {
            if (str != null && str.length() > 20) {
                throw new IllegalArgumentException(str + " length > 20");
            }
            this.ESTPN = str;
        }

        public void setMSCCD(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.MSCCD = str;
        }

        public void setMCCCD(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.MCCCD = str;
        }

        public void setTAX1(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.TAX1 = str;
        }

        public void setTAX2(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.TAX2 = str;
        }

        public void setORIGD(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.ORIGD = str;
        }

        public void setCUSRF1(String str) {
            if (str != null && str.length() > 30) {
                throw new IllegalArgumentException(str + " length > 30");
            }
            this.CUSRF1 = str;
        }

        public void setCUSRF2(String str) {
            if (str != null && str.length() > 30) {
                throw new IllegalArgumentException(str + " length > 30");
            }
            this.CUSRF2 = str;
        }

        public void setCUSRF3(String str) {
            if (str != null && str.length() > 30) {
                throw new IllegalArgumentException(str + " length > 30");
            }
            this.CUSRF3 = str;
        }

        public void setCUSRF4(String str) {
            if (str != null && str.length() > 30) {
                throw new IllegalArgumentException(str + " length > 30");
            }
            this.CUSRF4 = str;
        }

        public void setCUSRF5(String str) {
            if (str != null && str.length() > 30) {
                throw new IllegalArgumentException(str + " length > 30");
            }
            this.CUSRF5 = str;
        }

        public void setCUSRF6(String str) {
            if (str != null && str.length() > 30) {
                throw new IllegalArgumentException(str + " length > 30");
            }
            this.CUSRF6 = str;
        }

        public void setCHOLDP(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.CHOLDP = str;
        }

        public void setCARDP(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.CARDP = str;
        }

        public void setCPTRM(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.CPTRM = str;
        }

        public void setECI(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.ECI = str;
        }

        public void setCAVV(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.CAVV = str;
        }

        public void setNRID(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.NRID = str;
        }

        public void setCRDINP(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.CRDINP = str;
        }

        public void setSURFEE(String str) {
            if (str != null && str.length() > 10) {
                throw new IllegalArgumentException(str + " length > 10");
            }
            this.SURFEE = str;
        }

        public void setTRMTYP(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.TRMTYP = str;
        }

        public void setAQGEO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.AQGEO = str;
        }

        public void setCVVRST(String str) {
            if (str != null && str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.CVVRST = str;
        }

        public void setAUTYP(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.AUTYP = str;
        }

        public void setAURCDE(String str) {
            if (str != null && str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.AURCDE = str;
        }

        public void setSECFAR(String str) {
            if (str != null && str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SECFAR = str;
        }

        public void setCVVIND(String str) {
            if (str != null && str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.CVVIND = str;
        }

        public void setAUTHTR(String str) {
            if (str != null && str.length() > 16) {
                throw new IllegalArgumentException(str + " length > 16");
            }
            this.AUTHTR = str;
        }

        public void setVERACT(String str) {
            if (str != null && str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.VERACT = str;
        }

        public void setIPADDR(String str) {
            if (str != null && str.length() > 8) {
                throw new IllegalArgumentException(str + " length > 8");
            }
            this.IPADDR = str;
        }

        public void setSCAEXE(String str) {
            if (str != null && str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SCAEXE = str;
        }

        public void setTRAIND(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.TRAIND = str;
        }

        public void setORNRID(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.ORNRID = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(ACCT, "") + '>'
                    + Objects.toString(CAMTR, "") + '>'
                    + Objects.toString(CHGDT, "") + '>'
                    + Objects.toString(DATYP, "") + '>'
                    + Objects.toString(CHTYP, "") + '>'
                    + Objects.toString(ESTAB, "") + '>'
                    + Objects.toString(LCITY, "") + '>'
                    + Objects.toString(GEOCD, "") + '>'
                    + Objects.toString(APPCD, "") + '>'
                    + Objects.toString(TYPCH, "") + '>'
                    + Objects.toString(REFNO, "") + '>'
                    + Objects.toString(ANBR, "") + '>'
                    + Objects.toString(SENUM, "") + '>'
                    + Objects.toString(BLCUR, "") + '>'
                    + Objects.toString(BLAMT, "") + '>'
                    + Objects.toString(INTES, "") + '>'
                    + Objects.toString(ESTST, "") + '>'
                    + Objects.toString(ESTCO, "") + '>'
                    + Objects.toString(ESTZP, "") + '>'
                    + Objects.toString(ESTPN, "") + '>'
                    + Objects.toString(MSCCD, "") + '>'
                    + Objects.toString(MCCCD, "") + '>'
                    + Objects.toString(TAX1, "") + '>'
                    + Objects.toString(TAX2, "") + '>'
                    + Objects.toString(ORIGD, "") + '>'
                    + Objects.toString(CUSRF1, "") + '>'
                    + Objects.toString(CUSRF2, "") + '>'
                    + Objects.toString(CUSRF3, "") + '>'
                    + Objects.toString(CUSRF4, "") + '>'
                    + Objects.toString(CUSRF5, "") + '>'
                    + Objects.toString(CUSRF6, "") + '>'
                    + Objects.toString(CHOLDP, "") + '>'
                    + Objects.toString(CARDP, "") + '>'
                    + Objects.toString(CPTRM, "") + '>'
                    + Objects.toString(ECI, "") + '>'
                    + Objects.toString(CAVV, "") + '>'
                    + Objects.toString(NRID, "") + '>'
                    + Objects.toString(CRDINP, "") + '>'
                    + Objects.toString(SURFEE, "") + '>'
                    + Objects.toString(TRMTYP, "") + '>'
                    + Objects.toString(AQGEO, "") + '>'
                    + Objects.toString(CVVRST, "") + '>'
                    + Objects.toString(AUTYP, "") + '>'
                    + Objects.toString(AURCDE, "") + '>'
                    + Objects.toString(SECFAR, "") + '>'
                    + Objects.toString(CVVIND, "") + '>'
                    + Objects.toString(AUTHTR, "") + '>'
                    + Objects.toString(VERACT, "") + '>'
                    + Objects.toString(IPADDR, "") + '>'
                    + Objects.toString(SCAEXE, "") + '>'
                    + Objects.toString(TRAIND, "") + '>'
                    + Objects.toString(ORNRID, "")
                    ;
        }
    }

    @Getter
    @Validated
    public static class BatchTrailer {
        private String TRANS = "FRRC";

        private String FUNCD = "UT";

        private String SFTER;

        private String RCPNO;

        private String DFTER;

        private String BATCH;

        private String BTNCR;

        private String BTACR;

        private String BTNDR;

        private String BTADR;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setBTNCR(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 7) {
                throw new IllegalArgumentException(str + " length > 7");
            }
            this.BTNCR = str;
        }

        public void setBTACR(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 16) {
                throw new IllegalArgumentException(str + " length > 16");
            }
            this.BTACR = str;
        }

        public void setBTNDR(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 7) {
                throw new IllegalArgumentException(str + " length > 7");
            }
            this.BTNDR = str;
        }

        public void setBTADR(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 16) {
                throw new IllegalArgumentException(str + " length > 16");
            }
            this.BTADR = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(BTNCR, "") + '>'
                    + Objects.toString(BTACR, "") + '>'
                    + Objects.toString(BTNDR, "") + '>'
                    + Objects.toString(BTADR, "");
        }
    }

    @Getter
    @Validated
    public static class RecapTrailer {
        private String TRANS = "FRRC";

        private String FUNCD = "UY";

        private String SFTER;

        private String RCPNO;

        private String DFTER;

        private String RCNCR;

        private String RCACR;

        private String RCNDR;

        private String RCADR;

        private String DRATE;

        private String RNAMT;

        private String ACRKY;

        private String AGAMT;

        private String ACAMT;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.DFTER = str;
        }

        public void setRCNCR(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 7) {
                throw new IllegalArgumentException(str + " length > 7");
            }
            this.RCNCR = str;
        }

        public void setRCACR(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 16) {
                throw new IllegalArgumentException(str + " length > 16");
            }
            this.RCACR = str;
        }

        public void setRCNDR(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 7) {
                throw new IllegalArgumentException(str + " length > 7");
            }
            this.RCNDR = str;
        }

        public void setRCADR(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 16) {
                throw new IllegalArgumentException(str + " length > 16");
            }
            this.RCADR = str;
        }

        public void setDRATE(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.DRATE = str;
        }

        public void setRNAMT(String str) {
            if (str == null) {
                String val = (new BigDecimal(RCADR).subtract(new BigDecimal(RCACR)))
                        .multiply(new BigDecimal(1).subtract(new BigDecimal(DRATE).setScale(3, RoundingMode.HALF_EVEN).divide(new BigDecimal(100), 3, RoundingMode.HALF_EVEN)))
                        .setScale(3, RoundingMode.HALF_EVEN)
                        .toPlainString();
                if (val.length() > 16) {
                    val = val.substring(0, 16);
                }
                this.RNAMT = val;
                return;
            } else if (str.length() > 16) {
                throw new IllegalArgumentException(str + " length > 16");
            }
            this.RNAMT = str;
        }

        public void setACRKY(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.ACRKY = str;
        }

        public void setAGAMT(String str) {
            if (str != null && str.length() > 16) {
                throw new IllegalArgumentException(str + " length > 16");
            }
            this.AGAMT = str;
        }

        public void setACAMT(String str) {
            if (str != null && str.length() > 16) {
                throw new IllegalArgumentException(str + " length > 16");
            }
            this.ACAMT = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(RCNCR, "") + '>'
                    + Objects.toString(RCACR, "") + '>'
                    + Objects.toString(RCNDR, "") + '>'
                    + Objects.toString(RCADR, "") + '>'
                    + Objects.toString(DRATE, "") + '>'
                    + Objects.toString(RNAMT, "") + '>'
                    + Objects.toString(ACRKY, "") + '>'
                    + Objects.toString(AGAMT, "") + '>'
                    + Objects.toString(ACAMT, "");
        }
    }

    @Getter
    public static class AirlineAdditionalDetailRecord {
        private String TRANS = "RFRC";

        private String FUNCD = "XA";

        private String SFTER;

        private String RCPNO;

        private String DFTER;

        private String BATCH;

        private String SEQNO;

        private String SUSEQ;

        private String AIRCD;

        private String AIRRF;

        private String AGAFE;

        private String AGARF;

        private String AGADS;

        private String IATCD;

        private String IATNM;

        private String AGAD1;

        private String AGAD2;

        private String AGAD3;

        private String AGCTY;

        private String AGSTA;

        private String AGZIP;

        private String AGGCD;

        private String PNODC;

        private String PNOAC;

        private String PNONO;

        private String RESSY;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setAIRCD(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.AIRCD = str;
        }

        public void setAIRRF(String str) {
            if (str != null && str.length() > 20) {
                throw new IllegalArgumentException(str + " length > 20");
            }
            this.AIRRF = str;
        }

        public void setAGAFE(String str) {
            if (str != null && str.length() > 12) {
                throw new IllegalArgumentException(str + " length > 12");
            }
            this.AGAFE = str;
        }

        public void setAGARF(String str) {
            if (str != null && str.length() > 20) {
                throw new IllegalArgumentException(str + " length > 20");
            }
            this.AGARF = str;
        }

        public void setAGADS(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.AGADS = str;
        }

        public void setIATCD(String str) {
            if (str != null && str.length() > 8) {
                throw new IllegalArgumentException(str + " length > 8");
            }
            this.IATCD = str;
        }

        public void setIATNM(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.IATNM = str;
        }

        public void setAGAD1(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.AGAD1 = str;
        }

        public void setAGAD2(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.AGAD2 = str;
        }

        public void setAGAD3(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.AGAD3 = str;
        }

        public void setAGCTY(String str) {
            if (str != null && str.length() > 30) {
                throw new IllegalArgumentException(str + " length > 30");
            }
            this.AGCTY = str;
        }

        public void setAGSTA(String str) {
            if (str != null && str.length() > 30) {
                throw new IllegalArgumentException(str + " length > 30");
            }
            this.AGSTA = str;
        }

        public void setAGZIP(String str) {
            if (str != null && str.length() > 10) {
                throw new IllegalArgumentException(str + " length > 10");
            }
            this.AGZIP = str;
        }

        public void setAGGCD(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.AGGCD = str;
        }

        public void setPNODC(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.PNODC = str;
        }

        public void setPNOAC(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.PNOAC = str;
        }

        public void setPNONO(String str) {
            if (str != null && str.length() > 12) {
                throw new IllegalArgumentException(str + " length > 12");
            }
            this.PNONO = str;
        }

        public void setRESSY(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RESSY = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(AIRCD, "") + '>'
                    + Objects.toString(AIRRF, "") + '>'
                    + Objects.toString(AGAFE, "") + '>'
                    + Objects.toString(AGARF, "") + '>'
                    + Objects.toString(AGADS, "") + '>'
                    + Objects.toString(IATCD, "") + '>'
                    + Objects.toString(IATNM, "") + '>'
                    + Objects.toString(AGAD1, "") + '>'
                    + Objects.toString(AGAD2, "") + '>'
                    + Objects.toString(AGAD3, "") + '>'
                    + Objects.toString(AGCTY, "") + '>'
                    + Objects.toString(AGSTA, "") + '>'
                    + Objects.toString(AGZIP, "") + '>'
                    + Objects.toString(AGGCD, "") + '>'
                    + Objects.toString(PNODC, "") + '>'
                    + Objects.toString(PNOAC, "") + '>'
                    + Objects.toString(PNONO, "") + '>'
                    + Objects.toString(RESSY, "") + '>'
                    ;
        }
    }

    @Getter
    public static class AirlineRoutingDetailRecord {
        private String TRANS = "RFRC";

        private String FUNCD = "XB";

        private String SFTER;

        private String RCPNO;

        private String DFTER;

        private String BATCH;

        private String SEQNO;

        private String SUSEQ;

        private String TICNO;

        private String TICCD;

        private String PASNG;

        private String CARR1;

        private String FLNO1;

        private String DAPC1;

        private String DDTE1;

        private String DTIM1;

        private String AAPC1;

        private String ADTE1;

        private String ATIM1;

        private String FAMT1;

        private String BASI1;

        private String CLAS1;

        private String STOA1;

        private String CARR2;

        private String FLNO2;

        private String DAPC2;

        private String DDTE2;

        private String DTIM2;

        private String AAPC2;

        private String ADTE2;

        private String ATIM2;

        private String FAMT2;

        private String BASI2;

        private String CLAS2;

        private String STOA2;

        private String CARR3;

        private String FLNO3;

        private String DAPC3;

        private String DDTE3;

        private String DTIM3;

        private String AAPC3;

        private String ADTE3;

        private String ATIM3;

        private String FAMT3;

        private String BASI3;

        private String CLAS3;

        private String STOA3;

        private String CARR4;

        private String FLNO4;

        private String DAPC4;

        private String DDTE4;

        private String DTIM4;

        private String AAPC4;

        private String ADTE4;

        private String ATIM4;

        private String FAMT4;

        private String BASI4;

        private String CLAS4;

        private String STOA4;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setTICNO(String str) {
            if (str != null && str.length() > 10) {
                throw new IllegalArgumentException(str + " length > 10");
            }
            this.TICNO = str;
        }

        public void setTICCD(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.TICCD = str;
        }

        public void setPASNG(String str) {
            if (str != null && str.length() > 49) {
                throw new IllegalArgumentException(str + " length > 49");
            }
            this.PASNG = str;
        }

        public void setCARR1(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.CARR1 = str;
        }

        public void setFLNO1(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.FLNO1 = str;
        }

        public void setDAPC1(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.DAPC1 = str;
        }

        public void setDDTE1(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.DDTE1 = str;
        }

        public void setDTIM1(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.DTIM1 = str;
        }

        public void setAAPC1(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.AAPC1 = str;
        }

        public void setADTE1(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.ADTE1 = str;
        }

        public void setATIM1(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.ATIM1 = str;
        }

        public void setFAMT1(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.FAMT1 = str;
        }

        public void setBASI1(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.BASI1 = str;
        }

        public void setCLAS1(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.CLAS1 = str;
        }

        public void setSTOA1(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.STOA1 = str;
        }

        public void setCARR2(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.CARR2 = str;
        }

        public void setFLNO2(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.FLNO2 = str;
        }

        public void setDAPC2(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.DAPC2 = str;
        }

        public void setDDTE2(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.DDTE2 = str;
        }

        public void setDTIM2(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.DTIM2 = str;
        }

        public void setAAPC2(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.AAPC2 = str;
        }

        public void setADTE2(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.ADTE2 = str;
        }

        public void setATIM2(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.ATIM2 = str;
        }

        public void setFAMT2(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.FAMT2 = str;
        }

        public void setBASI2(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.BASI2 = str;
        }

        public void setCLAS2(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.CLAS2 = str;
        }

        public void setSTOA2(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.STOA2 = str;
        }

        public void setCARR3(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.CARR3 = str;
        }

        public void setFLNO3(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.FLNO3 = str;
        }

        public void setDAPC3(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.DAPC3 = str;
        }

        public void setDDTE3(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.DDTE3 = str;
        }

        public void setDTIM3(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.DTIM3 = str;
        }

        public void setAAPC3(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.AAPC3 = str;
        }

        public void setADTE3(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.ADTE3 = str;
        }

        public void setATIM3(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.ATIM3 = str;
        }

        public void setFAMT3(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.FAMT3 = str;
        }

        public void setBASI3(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.BASI3 = str;
        }

        public void setCLAS3(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.CLAS3 = str;
        }

        public void setSTOA3(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.STOA3 = str;
        }

        public void setCARR4(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.CARR4 = str;
        }

        public void setFLNO4(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.FLNO4 = str;
        }

        public void setDAPC4(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.DAPC4 = str;
        }

        public void setDDTE4(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.DDTE4 = str;
        }

        public void setDTIM4(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.DTIM4 = str;
        }

        public void setAAPC4(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.AAPC4 = str;
        }

        public void setADTE4(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.ADTE4 = str;
        }

        public void setATIM4(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.ATIM4 = str;
        }

        public void setFAMT4(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.FAMT4 = str;
        }

        public void setBASI4(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.BASI4 = str;
        }

        public void setCLAS4(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.CLAS4 = str;
        }

        public void setSTOA4(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.STOA4 = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(TICNO, "") + '>'
                    + Objects.toString(TICCD, "") + '>'
                    + Objects.toString(PASNG, "") + '>'
                    + Objects.toString(CARR1, "") + '>'
                    + Objects.toString(FLNO1, "") + '>'
                    + Objects.toString(DAPC1, "") + '>'
                    + Objects.toString(DDTE1, "") + '>'
                    + Objects.toString(DTIM1, "") + '>'
                    + Objects.toString(AAPC1, "") + '>'
                    + Objects.toString(ADTE1, "") + '>'
                    + Objects.toString(ATIM1, "") + '>'
                    + Objects.toString(FAMT1, "") + '>'
                    + Objects.toString(BASI1, "") + '>'
                    + Objects.toString(CLAS1, "") + '>'
                    + Objects.toString(STOA1, "") + '>'
                    + Objects.toString(CARR2, "") + '>'
                    + Objects.toString(FLNO2, "") + '>'
                    + Objects.toString(DAPC2, "") + '>'
                    + Objects.toString(DDTE2, "") + '>'
                    + Objects.toString(DTIM2, "") + '>'
                    + Objects.toString(AAPC2, "") + '>'
                    + Objects.toString(ADTE2, "") + '>'
                    + Objects.toString(ATIM2, "") + '>'
                    + Objects.toString(FAMT2, "") + '>'
                    + Objects.toString(BASI2, "") + '>'
                    + Objects.toString(CLAS2, "") + '>'
                    + Objects.toString(STOA2, "") + '>'
                    + Objects.toString(CARR3, "") + '>'
                    + Objects.toString(FLNO3, "") + '>'
                    + Objects.toString(DAPC3, "") + '>'
                    + Objects.toString(DDTE3, "") + '>'
                    + Objects.toString(DTIM3, "") + '>'
                    + Objects.toString(AAPC3, "") + '>'
                    + Objects.toString(ADTE3, "") + '>'
                    + Objects.toString(ATIM3, "") + '>'
                    + Objects.toString(FAMT3, "") + '>'
                    + Objects.toString(BASI3, "") + '>'
                    + Objects.toString(CLAS3, "") + '>'
                    + Objects.toString(STOA3, "") + '>'
                    + Objects.toString(CARR4, "") + '>'
                    + Objects.toString(FLNO4, "") + '>'
                    + Objects.toString(DAPC4, "") + '>'
                    + Objects.toString(DDTE4, "") + '>'
                    + Objects.toString(DTIM4, "") + '>'
                    + Objects.toString(AAPC4, "") + '>'
                    + Objects.toString(ADTE4, "") + '>'
                    + Objects.toString(ATIM4, "") + '>'
                    + Objects.toString(FAMT4, "") + '>'
                    + Objects.toString(BASI4, "") + '>'
                    + Objects.toString(CLAS4, "") + '>'
                    + Objects.toString(STOA4, "") + '>'
                    ;
        }
    }

    @Getter
    public static class ATMAdditionalDetailRecord {
        private String TRANS = "RFRC";

        private String FUNCD = "XC";

        private String SFTER;

        private String RCPNO;

        private String DFTER;

        private String BATCH;

        private String SEQNO;

        private String SUSEQ;

        private String UTCTM;

        private String UTCDT;

        private String LCTIM;

        private String LCDAT;

        private String ATMID;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setUTCTM(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.UTCTM = str;
        }

        public void setUTCDT(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.UTCDT = str;
        }

        public void setLCTIM(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.LCTIM = str;
        }

        public void setLCDAT(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.LCDAT = str;
        }

        public void setATMID(String str) {
            if (str != null && str.length() > 8) {
                throw new IllegalArgumentException(str + " length > 8");
            }
            this.ATMID = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(UTCTM, "") + '>'
                    + Objects.toString(UTCDT, "") + '>'
                    + Objects.toString(LCTIM, "") + '>'
                    + Objects.toString(LCDAT, "") + '>'
                    + Objects.toString(ATMID, "") + '>'
                    ;
        }
    }

    @Getter
    public static class CarRentalAdditionalDetailRecord {
        private String TRANS = "RFRC";
        private String FUNCD = "XV";
        private String SFTER;
        private String RCPNO;
        private String DFTER;
        private String BATCH;
        private String SEQNO;
        private String SUSEQ;
        private String RENNO;
        private String RENNM;
        private String RENCY;
        private String RENST;
        private String RENCO;
        private String RENDT;
        private String REMTM;
        private String RETCY;
        private String RETST;
        private String RETCO;
        private String RETDT;
        private String RETTM;
        private String RCCAR;
        private String RWRTE;
        private String RDRTE;
        private String RMRTE;
        private String RDIST;
        private String RFREM;
        private String RINSC;
        private String RFUEC;
        private String RMORK;
        private String ROWDC;
        private String RAUTC;
        private String RRMIC;
        private String REMIC;
        private String RLRTC;
        private String RTELC;
        private String ROTHC;
        private String RNOSH;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setRENNO(String str) {
            if (str != null && str.length() > 10) {
                throw new IllegalArgumentException(str + " length > 10");
            }
            this.RENNO = str;
        }

        public void setRENNM(String str) {
            if (str != null && str.length() > 49) {
                throw new IllegalArgumentException(str + " length > 49");
            }
            this.RENNM = str;
        }

        public void setRENCY(String str) {
            if (str != null && str.length() > 26) {
                throw new IllegalArgumentException(str + " length > 26");
            }
            this.RENCY = str;
        }

        public void setRENST(String str) {
            if (str != null && str.length() > 20) {
                throw new IllegalArgumentException(str + " length > 20");
            }
            this.RENST = str;
        }

        public void setRENCO(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RENCO = str;
        }

        public void setRENDT(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RENDT = str;
        }

        public void setREMTM(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.REMTM = str;
        }

        public void setRETCY(String str) {
            if (str != null && str.length() > 26) {
                throw new IllegalArgumentException(str + " length > 26");
            }
            this.RETCY = str;
        }

        public void setRETST(String str) {
            if (str != null && str.length() > 20) {
                throw new IllegalArgumentException(str + " length > 20");
            }
            this.RETST = str;
        }

        public void setRETCO(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RETCO = str;
        }

        public void setRETDT(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RETDT = str;
        }

        public void setRETTM(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RETTM = str;
        }

        public void setRCCAR(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RCCAR = str;
        }

        public void setRWRTE(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RWRTE = str;
        }

        public void setRDRTE(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RDRTE = str;
        }

        public void setRMRTE(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RMRTE = str;
        }

        public void setRDIST(String str) {
            if (str != null && str.length() > 5) {
                throw new IllegalArgumentException(str + " length > 5");
            }
            this.RDIST = str;
        }

        public void setRFREM(String str) {
            if (str != null && str.length() > 5) {
                throw new IllegalArgumentException(str + " length > 5");
            }
            this.RFREM = str;
        }

        public void setRINSC(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RINSC = str;
        }

        public void setRFUEC(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RFUEC = str;
        }

        public void setRMORK(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.RMORK = str;
        }

        public void setROWDC(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.ROWDC = str;
        }

        public void setRAUTC(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RAUTC = str;
        }

        public void setRRMIC(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RRMIC = str;
        }

        public void setREMIC(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.REMIC = str;
        }

        public void setRLRTC(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RLRTC = str;
        }

        public void setRTELC(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RTELC = str;
        }

        public void setROTHC(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.ROTHC = str;
        }

        public void setRNOSH(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.RNOSH = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(RENNO, "") + '>'
                    + Objects.toString(RENNM, "") + '>'
                    + Objects.toString(RENCY, "") + '>'
                    + Objects.toString(RENST, "") + '>'
                    + Objects.toString(RENCO, "") + '>'
                    + Objects.toString(RENDT, "") + '>'
                    + Objects.toString(REMTM, "") + '>'
                    + Objects.toString(RETCY, "") + '>'
                    + Objects.toString(RETST, "") + '>'
                    + Objects.toString(RETCO, "") + '>'
                    + Objects.toString(RETDT, "") + '>'
                    + Objects.toString(RETTM, "") + '>'
                    + Objects.toString(RCCAR, "") + '>'
                    + Objects.toString(RWRTE, "") + '>'
                    + Objects.toString(RDRTE, "") + '>'
                    + Objects.toString(RMRTE, "") + '>'
                    + Objects.toString(RDIST, "") + '>'
                    + Objects.toString(RFREM, "") + '>'
                    + Objects.toString(RINSC, "") + '>'
                    + Objects.toString(RFUEC, "") + '>'
                    + Objects.toString(RMORK, "") + '>'
                    + Objects.toString(ROWDC, "") + '>'
                    + Objects.toString(RAUTC, "") + '>'
                    + Objects.toString(RRMIC, "") + '>'
                    + Objects.toString(REMIC, "") + '>'
                    + Objects.toString(RLRTC, "") + '>'
                    + Objects.toString(RTELC, "") + '>'
                    + Objects.toString(ROTHC, "") + '>'
                    + Objects.toString(RNOSH, "") + '>'
                    ;
        }
    }

    @Getter
    public static class GasolineAdditionalDetailRecord {
        private String TRANS = "RFRC";
        private String FUNCD = "XG";
        private String SFTER;
        private String RCPNO;
        private String DFTER;
        private String BATCH;
        private String SEQNO;
        private String SUSEQ;
        private String GVLPN;
        private String GVID;
        private String GODOR;
        private String GODUN;
        private String GDRID;
        private String GFUUP;
        private String GFUPU;
        private String GFUUN;
        private String GFUPD;
        private String GFUAM;
        private String GOIUP;
        private String GOIUN;
        private String GOIAM;
        private String GMEPT;
        private String GMEAM;
        private String GCONO;
        private String GFLEE;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setGVLPN(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.GVLPN = str;
        }

        public void setGVID(String str) {
            if (str != null && str.length() > 20) {
                throw new IllegalArgumentException(str + " length > 20");
            }
            this.GVID = str;
        }

        public void setGODOR(String str) {
            if (str != null && str.length() > 7) {
                throw new IllegalArgumentException(str + " length > 7");
            }
            this.GODOR = str;
        }

        public void setGODUN(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.GODUN = str;
        }

        public void setGDRID(String str) {
            if (str != null && str.length() > 20) {
                throw new IllegalArgumentException(str + " length > 20");
            }
            this.GDRID = str;
        }

        public void setGFUUP(String str) {
            if (str != null && str.length() > 7) {
                throw new IllegalArgumentException(str + " length > 7");
            }
            this.GFUUP = str;
        }

        public void setGFUPU(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.GFUPU = str;
        }

        public void setGFUUN(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.GFUUN = str;
        }

        public void setGFUPD(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.GFUPD = str;
        }

        public void setGFUAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.GFUAM = str;
        }

        public void setGOIUP(String str) {
            if (str != null && str.length() > 7) {
                throw new IllegalArgumentException(str + " length > 7");
            }
            this.GOIUP = str;
        }

        public void setGOIUN(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.GOIUN = str;
        }

        public void setGOIAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.GOIAM = str;
        }

        public void setGMEPT(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.GMEPT = str;
        }

        public void setGMEAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.GMEAM = str;
        }

        public void setGCONO(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.GCONO = str;
        }

        public void setGFLEE(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.GFLEE = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(GVLPN, "") + '>'
                    + Objects.toString(GVID, "") + '>'
                    + Objects.toString(GODOR, "") + '>'
                    + Objects.toString(GODUN, "") + '>'
                    + Objects.toString(GDRID, "") + '>'
                    + Objects.toString(GFUUP, "") + '>'
                    + Objects.toString(GFUPU, "") + '>'
                    + Objects.toString(GFUUN, "") + '>'
                    + Objects.toString(GFUPD, "") + '>'
                    + Objects.toString(GFUAM, "") + '>'
                    + Objects.toString(GOIUP, "") + '>'
                    + Objects.toString(GOIUN, "") + '>'
                    + Objects.toString(GOIAM, "") + '>'
                    + Objects.toString(GMEPT, "") + '>'
                    + Objects.toString(GMEAM, "") + '>'
                    + Objects.toString(GCONO, "") + '>'
                    + Objects.toString(GFLEE, "") + '>'
                    ;
        }
    }

    @Getter
    public static class HotelAdditionalDetailRecord {
        private String TRANS = "RFRC";
        private String FUNCD = "XH";
        private String SFTER;
        private String RCPNO;
        private String DFTER;
        private String BATCH;
        private String SEQNO;
        private String SUSEQ;
        private String HCIDT;
        private String HCODT;
        private String HNOPT;
        private String HNAME;
        private String HRMTY;
        private String HPROG;
        private String HFOLI;
        private String HCRES;
        private String HRMRT;
        private String HRMTX;
        private String HNRTX;
        private String HPHAM;
        private String HRSAM;
        private String HMBAM;
        private String HRBAM;
        private String HGSAM;
        private String HLAAM;
        private String HPPAM;
        private String HREAM;
        private String HCAAM;
        private String HPKAM;
        private String HHCAM;
        private String HBCAM;
        private String HMVAM;
        private String HOTAM;
        private String HTIPS;
        private String HCONF;
        private String HAUDI;
        private String HBANQ;
        private String HINTE;
        private String HDEPA;
        private String HBAIN;
        private String HBAAM;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setHCIDT(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.HCIDT = str;
        }

        public void setHCODT(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.HCODT = str;
        }

        public void setHNOPT(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.HNOPT = str;
        }

        public void setHNAME(String str) {
            if (str != null && str.length() > 49) {
                throw new IllegalArgumentException(str + " length > 49");
            }
            this.HNAME = str;
        }

        public void setHRMTY(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.HRMTY = str;
        }

        public void setHPROG(String str) {
            if (str != null && str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.HPROG = str;
        }

        public void setHFOLI(String str) {
            if (str != null && str.length() > 10) {
                throw new IllegalArgumentException(str + " length > 10");
            }
            this.HFOLI = str;
        }

        public void setHCRES(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.HCRES = str;
        }

        public void setHRMRT(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HRMRT = str;
        }

        public void setHRMTX(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HRMTX = str;
        }

        public void setHNRTX(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HNRTX = str;
        }

        public void setHPHAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HPHAM = str;
        }

        public void setHRSAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HRSAM = str;
        }

        public void setHMBAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HMBAM = str;
        }

        public void setHRBAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HRBAM = str;
        }

        public void setHGSAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HGSAM = str;
        }

        public void setHLAAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HLAAM = str;
        }

        public void setHPPAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HPPAM = str;
        }

        public void setHREAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HREAM = str;
        }

        public void setHCAAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HCAAM = str;
        }

        public void setHPKAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HPKAM = str;
        }

        public void setHHCAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HHCAM = str;
        }

        public void setHBCAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HBCAM = str;
        }

        public void setHMVAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HMVAM = str;
        }

        public void setHOTAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HOTAM = str;
        }

        public void setHTIPS(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HTIPS = str;
        }

        public void setHCONF(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HCONF = str;
        }

        public void setHAUDI(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HAUDI = str;
        }

        public void setHBANQ(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HBANQ = str;
        }

        public void setHINTE(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HINTE = str;
        }

        public void setHDEPA(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HDEPA = str;
        }

        public void setHBAIN(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.HBAIN = str;
        }

        public void setHBAAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.HBAAM = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(HCIDT, "") + '>'
                    + Objects.toString(HCODT, "") + '>'
                    + Objects.toString(HNOPT, "") + '>'
                    + Objects.toString(HNAME, "") + '>'
                    + Objects.toString(HRMTY, "") + '>'
                    + Objects.toString(HPROG, "") + '>'
                    + Objects.toString(HFOLI, "") + '>'
                    + Objects.toString(HCRES, "") + '>'
                    + Objects.toString(HRMRT, "") + '>'
                    + Objects.toString(HRMTX, "") + '>'
                    + Objects.toString(HNRTX, "") + '>'
                    + Objects.toString(HPHAM, "") + '>'
                    + Objects.toString(HRSAM, "") + '>'
                    + Objects.toString(HMBAM, "") + '>'
                    + Objects.toString(HRBAM, "") + '>'
                    + Objects.toString(HGSAM, "") + '>'
                    + Objects.toString(HLAAM, "") + '>'
                    + Objects.toString(HPPAM, "") + '>'
                    + Objects.toString(HREAM, "") + '>'
                    + Objects.toString(HCAAM, "") + '>'
                    + Objects.toString(HPKAM, "") + '>'
                    + Objects.toString(HHCAM, "") + '>'
                    + Objects.toString(HBCAM, "") + '>'
                    + Objects.toString(HMVAM, "") + '>'
                    + Objects.toString(HOTAM, "") + '>'
                    + Objects.toString(HTIPS, "") + '>'
                    + Objects.toString(HCONF, "") + '>'
                    + Objects.toString(HAUDI, "") + '>'
                    + Objects.toString(HBANQ, "") + '>'
                    + Objects.toString(HINTE, "") + '>'
                    + Objects.toString(HDEPA, "") + '>'
                    + Objects.toString(HBAIN, "") + '>'
                    + Objects.toString(HBAAM, "") + '>'
                    ;
        }
    }

    @Getter
    public static class RailAdditionalDetailRecord {
        private String TRANS = "RFRC";
        private String FUNCD = "XR";
        private String SFTER;
        private String RCPNO;
        private String DFTER;
        private String BATCH;
        private String SEQNO;
        private String SUSEQ;
        private String RISTK;
        private String TICNO;
        private String TICCD;
        private String RAGCD;
        private String RAGNM;
        private String AGAD1;
        private String AGAD2;
        private String AGAD3;
        private String AGCTY;
        private String AGSTA;
        private String AGZIP;
        private String AGGCD;
        private String PNODC;
        private String PNOAC;
        private String PNONO;
        private String RRESY;
        private String PASNG;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setRISTK(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RISTK = str;
        }

        public void setTICNO(String str) {
            if (str != null && str.length() > 10) {
                throw new IllegalArgumentException(str + " length > 10");
            }
            this.TICNO = str;
        }

        public void setTICCD(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.TICCD = str;
        }

        public void setRAGCD(String str) {
            if (str != null && str.length() > 8) {
                throw new IllegalArgumentException(str + " length > 8");
            }
            this.RAGCD = str;
        }

        public void setRAGNM(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.RAGNM = str;
        }

        public void setAGAD1(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.AGAD1 = str;
        }

        public void setAGAD2(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.AGAD2 = str;
        }

        public void setAGAD3(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.AGAD3 = str;
        }

        public void setAGCTY(String str) {
            if (str != null && str.length() > 30) {
                throw new IllegalArgumentException(str + " length > 30");
            }
            this.AGCTY = str;
        }

        public void setAGSTA(String str) {
            if (str != null && str.length() > 30) {
                throw new IllegalArgumentException(str + " length > 30");
            }
            this.AGSTA = str;
        }

        public void setAGZIP(String str) {
            if (str != null && str.length() > 10) {
                throw new IllegalArgumentException(str + " length > 10");
            }
            this.AGZIP = str;
        }

        public void setAGGCD(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.AGGCD = str;
        }

        public void setPNODC(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.PNODC = str;
        }

        public void setPNOAC(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.PNOAC = str;
        }

        public void setPNONO(String str) {
            if (str != null && str.length() > 12) {
                throw new IllegalArgumentException(str + " length > 12");
            }
            this.PNONO = str;
        }

        public void setRRESY(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RRESY = str;
        }

        public void setPASNG(String str) {
            if (str != null && str.length() > 49) {
                throw new IllegalArgumentException(str + " length > 49");
            }
            this.PASNG = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(RISTK, "") + '>'
                    + Objects.toString(TICNO, "") + '>'
                    + Objects.toString(TICCD, "") + '>'
                    + Objects.toString(RAGCD, "") + '>'
                    + Objects.toString(RAGNM, "") + '>'
                    + Objects.toString(AGAD1, "") + '>'
                    + Objects.toString(AGAD2, "") + '>'
                    + Objects.toString(AGAD3, "") + '>'
                    + Objects.toString(AGCTY, "") + '>'
                    + Objects.toString(AGSTA, "") + '>'
                    + Objects.toString(AGZIP, "") + '>'
                    + Objects.toString(AGGCD, "") + '>'
                    + Objects.toString(PNODC, "") + '>'
                    + Objects.toString(PNOAC, "") + '>'
                    + Objects.toString(PNONO, "") + '>'
                    + Objects.toString(RRESY, "") + '>'
                    + Objects.toString(PASNG, "") + '>'
                    ;
        }
    }

    @Getter
    public static class RailRoutingDetailRecord {
        private String TRANS = "RFRC";
        private String FUNCD = "XL";
        private String SFTER;
        private String RCPNO;
        private String DFTER;
        private String BATCH;
        private String SEQNO;
        private String SUSEQ;
        private String RCACD1;
        private String RJNNO1;
        private String RDECY1;
        private String RDEDT1;
        private String RDETM1;
        private String RARCY1;
        private String RARDT1;
        private String RARTM1;
        private String RFAAM1;
        private String RBASI1;
        private String RCLAS1;

        private String RCACD2;
        private String RJNNO2;
        private String RDECY2;
        private String RDEDT2;
        private String RDETM2;
        private String RARCY2;
        private String RARDT2;
        private String RARTM2;
        private String RFAAM2;
        private String RBASI2;
        private String RCLAS2;

        private String RCACD3;
        private String RJNNO3;
        private String RDECY3;
        private String RDEDT3;
        private String RDETM3;
        private String RARCY3;
        private String RARDT3;
        private String RARTM3;
        private String RFAAM3;
        private String RBASI3;
        private String RCLAS3;

        private String RCACD4;
        private String RJNNO4;
        private String RDECY4;
        private String RDEDT4;
        private String RDETM4;
        private String RARCY4;
        private String RARDT4;
        private String RARTM4;
        private String RFAAM4;
        private String RBASI4;
        private String RCLAS4;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setRCACD1(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RCACD1 = str;
        }

        public void setRJNNO1(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RJNNO1 = str;
        }

        public void setRDECY1(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RDECY1 = str;
        }

        public void setRDEDT1(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RDEDT1 = str;
        }

        public void setRDETM1(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RDETM1 = str;
        }

        public void setRARCY1(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RARCY1 = str;
        }

        public void setRARDT1(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RARDT1 = str;
        }

        public void setRARTM1(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RARTM1 = str;
        }

        public void setRFAAM1(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RFAAM1 = str;
        }

        public void setRBASI1(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RBASI1 = str;
        }

        public void setRCLAS1(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.RCLAS1 = str;
        }

        public void setRCACD2(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RCACD2 = str;
        }

        public void setRJNNO2(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RJNNO2 = str;
        }

        public void setRDECY2(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RDECY2 = str;
        }

        public void setRDEDT2(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RDEDT2 = str;
        }

        public void setRDETM2(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RDETM2 = str;
        }

        public void setRARCY2(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RARCY2 = str;
        }

        public void setRARDT2(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RARDT2 = str;
        }

        public void setRARTM2(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RARTM2 = str;
        }

        public void setRFAAM2(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RFAAM2 = str;
        }

        public void setRBASI2(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RBASI2 = str;
        }

        public void setRCLAS2(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.RCLAS2 = str;
        }

        public void setRCACD3(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RCACD3 = str;
        }

        public void setRJNNO3(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RJNNO3 = str;
        }

        public void setRDECY3(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RDECY3 = str;
        }

        public void setRDEDT3(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RDEDT3 = str;
        }

        public void setRDETM3(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RDETM3 = str;
        }

        public void setRARCY3(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RARCY3 = str;
        }

        public void setRARDT3(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RARDT3 = str;
        }

        public void setRARTM3(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RARTM3 = str;
        }

        public void setRFAAM3(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RFAAM3 = str;
        }

        public void setRBASI3(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RBASI3 = str;
        }

        public void setRCLAS3(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.RCLAS3 = str;
        }

        public void setRCACD4(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RCACD4 = str;
        }

        public void setRJNNO4(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RJNNO4 = str;
        }

        public void setRDECY4(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RDECY4 = str;
        }

        public void setRDEDT4(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RDEDT4 = str;
        }

        public void setRDETM4(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RDETM4 = str;
        }

        public void setRARCY4(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RARCY4 = str;
        }

        public void setRARDT4(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.RARDT4 = str;
        }

        public void setRARTM4(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.RARTM4 = str;
        }

        public void setRFAAM4(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RFAAM4 = str;
        }

        public void setRBASI4(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RBASI4 = str;
        }

        public void setRCLAS4(String str) {
            if (str != null && str.length() > 1) {
                throw new IllegalArgumentException(str + " length > 1");
            }
            this.RCLAS4 = str;
        }

        @Override
        public String toString() {
            return ""       + Objects.toString(TRANS, "") + '>'
                    + Objects.toString(FUNCD, "") + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(RCACD1, "") + '>'
                    + Objects.toString(RJNNO1, "") + '>'
                    + Objects.toString(RDECY1, "") + '>'
                    + Objects.toString(RDEDT1, "") + '>'
                    + Objects.toString(RDETM1, "") + '>'
                    + Objects.toString(RARCY1, "") + '>'
                    + Objects.toString(RARDT1, "") + '>'
                    + Objects.toString(RARTM1, "") + '>'
                    + Objects.toString(RFAAM1, "") + '>'
                    + Objects.toString(RBASI1, "") + '>'
                    + Objects.toString(RCLAS1, "") + '>'
                    + Objects.toString(RCACD2, "") + '>'
                    + Objects.toString(RJNNO2, "") + '>'
                    + Objects.toString(RDECY2, "") + '>'
                    + Objects.toString(RDEDT2, "") + '>'
                    + Objects.toString(RDETM2, "") + '>'
                    + Objects.toString(RARCY2, "") + '>'
                    + Objects.toString(RARDT2, "") + '>'
                    + Objects.toString(RARTM2, "") + '>'
                    + Objects.toString(RFAAM2, "") + '>'
                    + Objects.toString(RBASI2, "") + '>'
                    + Objects.toString(RCLAS2, "") + '>'
                    + Objects.toString(RCACD3, "") + '>'
                    + Objects.toString(RJNNO3, "") + '>'
                    + Objects.toString(RDECY3, "") + '>'
                    + Objects.toString(RDEDT3, "") + '>'
                    + Objects.toString(RDETM3, "") + '>'
                    + Objects.toString(RARCY3, "") + '>'
                    + Objects.toString(RARDT3, "") + '>'
                    + Objects.toString(RARTM3, "") + '>'
                    + Objects.toString(RFAAM3, "") + '>'
                    + Objects.toString(RBASI3, "") + '>'
                    + Objects.toString(RCLAS3, "") + '>'
                    + Objects.toString(RCACD4, "") + '>'
                    + Objects.toString(RJNNO4, "") + '>'
                    + Objects.toString(RDECY4, "") + '>'
                    + Objects.toString(RDEDT4, "") + '>'
                    + Objects.toString(RDETM4, "") + '>'
                    + Objects.toString(RARCY4, "") + '>'
                    + Objects.toString(RARDT4, "") + '>'
                    + Objects.toString(RARTM4, "") + '>'
                    + Objects.toString(RFAAM4, "") + '>'
                    + Objects.toString(RBASI4, "") + '>'
                    + Objects.toString(RCLAS4, "") + '>'
                    ;
        }
    }

    @Getter
    public static class RestaurantAdditionalDetailRecord {
        private String TRANS = "RFRC";
        private String FUNCD = "XE";
        private String SFTER;
        private String RCPNO;
        private String DFTER;
        private String BATCH;
        private String SEQNO;
        private String SUSEQ;
        private String RFDAM;
        private String RBVAM;
        private String ROTAM;
        private String RTPAM;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setRFDAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RFDAM = str;
        }

        public void setRBVAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RBVAM = str;
        }

        public void setROTAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.ROTAM = str;
        }

        public void setRTPAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.RTPAM = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(RFDAM, "") + '>'
                    + Objects.toString(RBVAM, "") + '>'
                    + Objects.toString(ROTAM, "") + '>'
                    + Objects.toString(RTPAM, "") + '>'
                    ;
        }
    }

    @Getter
    public static class TelephoneAdditionalDetailRecord {
        private String TRANS = "RFRC";
        private String FUNCD = "XT";
        private String SFTER;
        private String RCPNO;
        private String DFTER;
        private String BATCH;
        private String SEQNO;
        private String SUSEQ;
        private String TCLDT;
        private String TCLTM;
        private String TCLDU;
        private String TFRNO;
        private String TFRCY;
        private String TFRCO;
        private String TTONO;
        private String TTOCY;
        private String TTOCO;
        private String TCLAM;
        private String TDIAM;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setTCLDT(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.TCLDT = str;
        }

        public void setTCLTM(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.TCLTM = str;
        }

        public void setTCLDU(String str) {
            if (str != null && str.length() > 5) {
                throw new IllegalArgumentException(str + " length > 5");
            }
            this.TCLDU = str;
        }

        public void setTFRNO(String str) {
            if (str != null && str.length() > 20) {
                throw new IllegalArgumentException(str + " length > 20");
            }
            this.TFRNO = str;
        }

        public void setTFRCY(String str) {
            if (str != null && str.length() > 26) {
                throw new IllegalArgumentException(str + " length > 26");
            }
            this.TFRCY = str;
        }

        public void setTFRCO(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.TFRCO = str;
        }

        public void setTTONO(String str) {
            if (str != null && str.length() > 20) {
                throw new IllegalArgumentException(str + " length > 20");
            }
            this.TTONO = str;
        }

        public void setTTOCY(String str) {
            if (str != null && str.length() > 26) {
                throw new IllegalArgumentException(str + " length > 26");
            }
            this.TTOCY = str;
        }

        public void setTTOCO(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.TTOCO = str;
        }

        public void setTCLAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.TCLAM = str;
        }

        public void setTDIAM(String str) {
            if (str != null && str.length() > 15) {
                throw new IllegalArgumentException(str + " length > 15");
            }
            this.TDIAM = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(TCLDT, "") + '>'
                    + Objects.toString(TCLTM, "") + '>'
                    + Objects.toString(TCLDU, "") + '>'
                    + Objects.toString(TFRNO, "") + '>'
                    + Objects.toString(TFRCY, "") + '>'
                    + Objects.toString(TFRCO, "") + '>'
                    + Objects.toString(TTONO, "") + '>'
                    + Objects.toString(TTOCY, "") + '>'
                    + Objects.toString(TTOCO, "") + '>'
                    + Objects.toString(TCLAM, "") + '>'
                    + Objects.toString(TDIAM, "") + '>'
                    ;
        }
    }

    @Getter
    public static class ChipCardAdditionalDetailRecord {
        private String TRANS = "RFRC";
        private String FUNCD = "XM";
        private String SFTER;
        private String RCPNO;
        private String DFTER;
        private String BATCH;
        private String SEQNO;
        private String SUSEQ;
        private String CPANSQN;
        private String CAIDT;
        private String CAIPFL;
        private String CATCTR;
        private String CACRG;
        private String CAUCN;
        private String CAMTA;
        private String CAMTO;
        private String CCRIF;
        private String CCVMR;
        private String CDEDF;
        private String CIDSN;
        private String CADA1;
        private String CADAT;
        private String CISRT;
        private String CTRMG;
        private String CTAVN;
        private String CTRMC;
        private String CTRMT;
        private String CTRMR;
        private String CTRND;
        private String CTRNT;
        private String CTRNC;
        private String CUNPN;

        public void setSFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.SFTER = str;
        }

        public void setRCPNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.RCPNO = str;
        }

        public void setDFTER(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.DFTER = str;
        }

        public void setBATCH(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.BATCH = str;
        }

        public void setSEQNO(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SEQNO = str;
        }

        public void setSUSEQ(String str) {
            Objects.requireNonNull(str);
            if (str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.SUSEQ = str;
        }

        public void setCPANSQN(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.CPANSQN = str;
        }

        public void setCAIDT(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.CAIDT = str;
        }

        public void setCAIPFL(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.CAIPFL = str;
        }

        public void setCATCTR(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.CATCTR = str;
        }

        public void setCACRG(String str) {
            if (str != null && str.length() > 16) {
                throw new IllegalArgumentException(str + " length > 16");
            }
            this.CACRG = str;
        }

        public void setCAUCN(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.CAUCN = str;
        }

        public void setCAMTA(String str) {
            if (str != null && str.length() > 12) {
                throw new IllegalArgumentException(str + " length > 12");
            }
            this.CAMTA = str;
        }

        public void setCAMTO(String str) {
            if (str != null && str.length() > 12) {
                throw new IllegalArgumentException(str + " length > 12");
            }
            this.CAMTO = str;
        }

        public void setCCRIF(String str) {
            if (str != null && str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.CCRIF = str;
        }

        public void setCCVMR(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.CCVMR = str;
        }

        public void setCDEDF(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.CDEDF = str;
        }

        public void setCIDSN(String str) {
            if (str != null && str.length() > 8) {
                throw new IllegalArgumentException(str + " length > 8");
            }
            this.CIDSN = str;
        }

        public void setCADA1(String str) {
            if (str != null && str.length() > 64) {
                throw new IllegalArgumentException(str + " length > 64");
            }
            this.CADA1 = str;
        }

        public void setCADAT(String str) {
            if (str != null && str.length() > 32) {
                throw new IllegalArgumentException(str + " length > 32");
            }
            this.CADAT = str;
        }

        public void setCISRT(String str) {
            if (str != null && str.length() > 50) {
                throw new IllegalArgumentException(str + " length > 50");
            }
            this.CISRT = str;
        }

        public void setCTRMG(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.CTRMG = str;
        }

        public void setCTAVN(String str) {
            if (str != null && str.length() > 4) {
                throw new IllegalArgumentException(str + " length > 4");
            }
            this.CTAVN = str;
        }

        public void setCTRMC(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.CTRMC = str;
        }

        public void setCTRMT(String str) {
            if (str != null && str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.CTRMT = str;
        }

        public void setCTRMR(String str) {
            if (str != null && str.length() > 10) {
                throw new IllegalArgumentException(str + " length > 10");
            }
            this.CTRMR = str;
        }

        public void setCTRND(String str) {
            if (str != null && str.length() > 6) {
                throw new IllegalArgumentException(str + " length > 6");
            }
            this.CTRND = str;
        }

        public void setCTRNT(String str) {
            if (str != null && str.length() > 2) {
                throw new IllegalArgumentException(str + " length > 2");
            }
            this.CTRNT = str;
        }

        public void setCTRNC(String str) {
            if (str != null && str.length() > 3) {
                throw new IllegalArgumentException(str + " length > 3");
            }
            this.CTRNC = str;
        }

        public void setCUNPN(String str) {
            if (str != null && str.length() > 8) {
                throw new IllegalArgumentException(str + " length > 8");
            }
            this.CUNPN = str;
        }

        @Override
        public String toString() {
            return TRANS + '>'
                    + FUNCD + '>'
                    + Objects.toString(SFTER, "") + '>'
                    + Objects.toString(RCPNO, "") + '>'
                    + Objects.toString(DFTER, "") + '>'
                    + Objects.toString(BATCH, "") + '>'
                    + Objects.toString(SEQNO, "") + '>'
                    + Objects.toString(SUSEQ, "") + '>'
                    + Objects.toString(CPANSQN, "") + '>'
                    + Objects.toString(CAIDT, "") + '>'
                    + Objects.toString(CAIPFL, "") + '>'
                    + Objects.toString(CATCTR, "") + '>'
                    + Objects.toString(CACRG, "") + '>'
                    + Objects.toString(CAUCN, "") + '>'
                    + Objects.toString(CAMTA, "") + '>'
                    + Objects.toString(CAMTO, "") + '>'
                    + Objects.toString(CCRIF, "") + '>'
                    + Objects.toString(CCVMR, "") + '>'
                    + Objects.toString(CDEDF, "") + '>'
                    + Objects.toString(CIDSN, "") + '>'
                    + Objects.toString(CADA1, "") + '>'
                    + Objects.toString(CADAT, "") + '>'
                    + Objects.toString(CISRT, "") + '>'
                    + Objects.toString(CTRMG, "") + '>'
                    + Objects.toString(CTAVN, "") + '>'
                    + Objects.toString(CTRMC, "") + '>'
                    + Objects.toString(CTRMT, "") + '>'
                    + Objects.toString(CTRMR, "") + '>'
                    + Objects.toString(CTRND, "") + '>'
                    + Objects.toString(CTRNT, "") + '>'
                    + Objects.toString(CTRNC, "") + '>'
                    + Objects.toString(CUNPN, "") + '>'
                    ;
        }
    }
}
