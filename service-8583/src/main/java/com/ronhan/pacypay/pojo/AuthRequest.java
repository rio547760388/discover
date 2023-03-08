package com.ronhan.pacypay.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/4/27
 **/
@Data
public class AuthRequest {
    @ApiModelProperty("卡号")
    @Pattern(regexp = "\\d{12,19}", message = "invalid card")
    @NotEmpty(message = "invalid card")
    private String cardNo;

    @ApiModelProperty("交易金额(单位元)")
    @Pattern(regexp = "\\d{1,9}(\\.\\d{0,3})?", message = "invalid amount")
    @NotEmpty(message = "invalid amount")
    private String amount;

    @ApiModelProperty("交易时间yyMMddHHmmss")
    @NotEmpty(message = "invalid transactionTime")
    @Pattern(regexp = "\\d{12}", message = "invalid transactionTime")
    private String transactionTime;

    @ApiModelProperty("交易币种")
    @Pattern(regexp = "[A-Z]{3}", message = "invalid currency")
    @NotEmpty(message = "invalid currency")
    private String currency;

    @ApiModelProperty("卡过期月份")
    @Pattern(regexp = "\\d{2}", message = "invalid expiryMonth")
    @NotEmpty(message = "invalid expiryMonth")
    private String expiryMonth;

    @ApiModelProperty("卡过期年份")
    @Pattern(regexp = "\\d{2}", message = "invalid expiryYear")
    @NotEmpty(message = "invalid expiryYear")
    private String expiryYear;

    @ApiModelProperty("cvv")
    @Pattern(regexp = "\\d{3}", message = "invalid cvv")
    @NotEmpty(message = "invalid cvv")
    private String cvv;

    /*@ApiModelProperty("机构识别码")
    private String iic;*/

    @ApiModelProperty("mcc")
    @Pattern(regexp = "\\d{4}", message = "invalid mcc")
    @NotEmpty(message = "invalid mcc")
    private String mcc;

    @ApiModelProperty("商户号")
    @NotEmpty(message = "invalid acceptorId")
    @Pattern(regexp = "([a-zA-Z0-9]+[\\s#-]?)+", message = "invalid acceptorId")
    @Size(min = 1, max = 15, message = "invalid acceptorId")
    private String acceptorId;

    @ApiModelProperty("商户名称（英文）")
    @Pattern(regexp = "[a-zA-Z0-9\\s\\p{Punct}]{1,36}", message = "invalid acceptorName")
    @NotEmpty(message = "invalid acceptorName")
    private String acceptorName;

    @ApiModelProperty("商户地址")
    @Pattern(regexp = "[a-zA-Z0-9\\s]{1,36}", message = "invalid acceptorStreet")
    private String acceptorStreet;

    @ApiModelProperty("商户城市")
    @Pattern(regexp = "[a-zA-Z0-9\\s]{1,26}", message = "invalid acceptorCity")
    @NotEmpty(message = "不能为空")
    private String acceptorCity;

    @ApiModelProperty("商户邮编")
    @Pattern(regexp = "[a-zA-Z\\d]{0,10}", message = "invalid postalCode")
    private String postalCode;

    @ApiModelProperty("商户地区")
    @Pattern(regexp = "[a-zA-Z\\d]{0,3}", message = "invalid acceptorRegion")
    private String acceptorRegion;

    @ApiModelProperty("商户国家")
    @Pattern(regexp = "[A-Z]{2,3}", message = "invalid acceptorCountry")
    @NotEmpty(message = "invalid acceptorCountry")
    private String acceptorCountry;

    @ApiModelProperty("交易发起国家")
    @Pattern(regexp = "[A-Z]{2,3}", message = "invalid originatorCountry")
    @NotEmpty(message = "invalid originatorCountry")
    private String originatorCountry;

    @NotEmpty(message = "invalid uniqueId")
    private String uniqueId;

    @ApiModelProperty("0-手工captur，1-自动capture")
    private Integer autoCapture;

    @ApiModelProperty("discover charge type")
    private String chargeType;

    @ApiModelProperty("discover intes")
    private String intes;

    @ApiModelProperty("DE22 pos7 v-In app")
    private String inApp;

    @ApiModelProperty("1 - DE3 auth for credit")
    private String authForCredit;

    @ApiModelProperty("3D 参数")
    private ThreeDSecure threeDSecure;

    public String toAcLocation() {
        return String.format("%s\\%s\\%s\\%s%s%s", Objects.toString(acceptorName, ""),
                Objects.toString(acceptorStreet, ""),
                Objects.toString(acceptorCity, ""),
                String.format("%-10s", Objects.toString(postalCode, "")),
                String.format("%3s", Objects.toString(acceptorRegion, "")),
                String.format("%3s", Objects.toString(acceptorCountry, "")));
    }
}
