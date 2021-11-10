package com.ronhan.pacypay.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/7/26
 **/
@Data
public class CaptureRequest {
    @NotEmpty(message = "不能为空")
    private String uniqueId;
}
