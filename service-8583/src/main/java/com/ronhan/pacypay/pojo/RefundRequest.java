package com.ronhan.pacypay.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/25
 **/
@Data
public class RefundRequest {
    //@ApiModelProperty("0-部分退款，1-全额退款")
    //@NotEmpty(message = "不能为空")
    //private String fullRefund;

    @ApiModelProperty("退款金额")
    @NotEmpty(message = "不能为空")
    private String amount;

    @ApiModelProperty("退款币种")
    @NotEmpty(message = "不能为空")
    private String currency;

    @NotEmpty(message = "不能为空")
    private String uniqueId;

    @ApiModelProperty("原始交易uniqueId")
    @NotEmpty(message = "不能为空")
    private String originalId;
}
