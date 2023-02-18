package com.universe.wallet.DTO.request;

import lombok.Data;

/**
 * 创建提现订单的请求参数封装
 * @author Pullwind
 */
@Data
public class CreateWithdrawOrderRequest {
    /**
     * 商户输入的提现订单号
     */
    private String merchantOrderNo;
    /**
     * 支付方式 1: 银行卡。目前只有这么一种，后面可能扩展
     */
    private int paymentMethod;
    /**
     * 	收款人
     */
    private String bankAccountName;
    /**
     *银行卡号 16 17 19 位
     */
    private String bankAccount;
    /**
     *银行名称
     */
    private String bankName;
    /**
     *	开户地址 (银行支行), 可以为空
     */
    private String bankBranch;
    /**
     *提现金额 (只支持整数金额)
     */
    private String amount;
}
