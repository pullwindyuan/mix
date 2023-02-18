package com.universe.wallet.DTO.request;

import lombok.Data;

/**
 * 创建提现订单的请求参数封装
 * @author Pullwind
 */
@Data
public class ApprovalWithdrawOrderRequest {
    /**
     * 待审核的提现订单号
     */
    private String merchantOrderNo;
    /**
     * 审批状态 1: 通过 2: 拒绝     * @param state
     */
    private int state;
}
