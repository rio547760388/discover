package com.ronhan.iso8583.discover.sftp.files;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/30
 **/
@Data
public class IMR {

    public static final String[] title = {
            "Acquirer ID", "Merchant Record Type", "Merchant Parent Group ID",
            "Merchant Parent Group Name", "Merchant Identifier", "Terminal Identifier",
            "D-PAS Enabled", "Merchant Open Date", "Merchant Close Date",
            "Target Merchant Indicator", "Merchant Tier", "Merchant Category Classification (MCC) code",
            "Merchant DBA Name", "Business Legal Name", "Address Line 1",
            "Address Line 2", "Address Line 3", "Postal Code",
            "City", "State / Province", "Country",
            "Merchant Phone Number", "Merchant Web Address", "File Layout Version",
            "Interchange Merchant Identifier", "Contactless", "Native Language",
            "Merchant DBA Name Native", "Address Line 1 Native", "Address Line 2 Native",
            "Address Line 3 Native", "State / Province Native", "Postal Code Native",
            "City Native", "DCI-MPAN"
    };

    @NotEmpty
    private String acquirerID;

    @NotEmpty
    private String merchantRecordType;

    private String merchantParentGroupID;

    private String merchantParentGroupName;

    @NotEmpty
    private String merchantIdentifier;

    private String terminalIdentifier;

    @NotEmpty
    private String DPasEnabled;

    @NotEmpty
    private String merchantOpenDate;

    private String merchantCloseDate;

    private String targetMerchantIndicator;

    private String merchantTier;

    @NotEmpty
    private String mcc;

    @NotEmpty
    private String merchantDBAName;

    @NotEmpty
    private String businessLegalName;

    @NotEmpty
    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String postalCode;

    @NotEmpty
    private String city;

    private String state;

    @NotEmpty
    private String country;

    private String merchantPhoneNumber;

    private String merchantWebAddress;

    @NotEmpty
    private String fileLayoutVersion;

    private String interchangeMerchantIdentifier;

    @NotEmpty
    private String contactless = "N";

    private String nativeLanguage;

    private String merchantDBANameNative;

    private String addressLine1Native;

    private String addressLine2Native;

    private String addressLine3Native;

    private String stateNative;

    private String postalCodeNative;

    private String cityNative;

    private String DCIMpan;
}
