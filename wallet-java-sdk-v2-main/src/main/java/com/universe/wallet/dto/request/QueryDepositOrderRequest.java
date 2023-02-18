package com.universe.wallet.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建充值订单的请求参数封装
 * @author Pullwind
 */
@Data
public class QueryDepositOrderRequest {
    /**
     * 要查询的的充值订单号
     */
    @ApiModelProperty(value = "加店家充值订单号")
    private String merchantOrderNo;
}
