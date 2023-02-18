package com.universe.wallet.DTO.request;

import lombok.Data;

/**
 * 创建提现订单的请求参数封装
 * @author Pullwind
 */
@Data
public class QueryWithdrawOrderRequest {
    /**
     * 要查询的的提现订单号
     */
    private String merchantOrderNo;
}
