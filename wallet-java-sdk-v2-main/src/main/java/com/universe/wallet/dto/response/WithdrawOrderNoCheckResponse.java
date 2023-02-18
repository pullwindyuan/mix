package com.universe.wallet.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 发起免审订单接口返回数据
 * @author Pullwind
 */
@Data
public class WithdrawOrderNoCheckResponse {
    /**
     * 把店家会员转跳到这URL
     */
    @ApiModelProperty(value = "把店家会员转跳到这URL")
    private String url;
}
