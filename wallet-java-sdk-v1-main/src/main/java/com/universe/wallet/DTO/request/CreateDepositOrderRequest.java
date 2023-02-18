package com.universe.wallet.DTO.request;

import lombok.Data;

/**
 * 创建充值订单的请求参数封装
 * @author Pullwind
 */
@Data
public class CreateDepositOrderRequest {
    /**
     * 商户输入的充值订单号
     */
    private String merchantOrderNo;
    /**
     * 支付方式 1: 银行卡。目前只有这么一种，后面可能扩展
     */
    private int paymentMethod;
    /**
     *充值金额 (只支持整数金额)
     */
    private String amount;
}
