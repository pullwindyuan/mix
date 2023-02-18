package com.universe.wallet.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取充值金额列表返回数据
 * @author Pullwind
 */
@Data
public class DepositAmountListResponse {
    @ApiModelProperty(value = "支付方式 1：银行卡，2：微信，3：支付宝")
    private int paymentMethod;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "金额")
    private String amount;
}
