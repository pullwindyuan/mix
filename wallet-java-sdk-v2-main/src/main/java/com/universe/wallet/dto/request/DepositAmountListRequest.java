package com.universe.wallet.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取充值金额列表请求参数
 * @author Pullwind
 */
@Data
public class DepositAmountListRequest {
    @ApiModelProperty(value = "充值方式 （1 银行卡 2 支付宝 3 微信）", required = true)
    private int paymentMethod;
    @ApiModelProperty(value = "金额梯度 （1 小额 2 中额 3 大额）", required = true)
    private int amountType;
}
