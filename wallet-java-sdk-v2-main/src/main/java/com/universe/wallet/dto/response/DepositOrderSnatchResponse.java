package com.universe.wallet.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 抢单充值返回数据
 * @author Pullwind
 */
@Data
public class DepositOrderSnatchResponse {
    @ApiModelProperty(value = "跳转链接")
    private String url;
}
