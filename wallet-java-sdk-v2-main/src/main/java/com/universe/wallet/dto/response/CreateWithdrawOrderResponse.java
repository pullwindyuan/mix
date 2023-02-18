package com.universe.wallet.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建提现订单的返回数据
 * @author Pullwind
 */
@Data
public class CreateWithdrawOrderResponse {
    /**
     * 把店家会员转跳到这URL
     */
    @ApiModelProperty(value = "把店家会员转跳到这URL")
    private String url;
}
