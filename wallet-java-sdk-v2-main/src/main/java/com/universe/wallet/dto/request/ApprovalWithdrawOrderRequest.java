package com.universe.wallet.dto.request;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "店家订单号", required = true)
    private String merchantOrderNo;
    /**
     * 审批状态 1: 通过 2: 拒绝     * @param state
     */
    @ApiModelProperty(value = "状态 1: 通过 2: 拒绝", required = true)
    private int state;
}
