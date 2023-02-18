package com.universe.wallet.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 抢单充值请求参数
 * @author Pullwind
 */
@Data
public class DepositOrderSnatchRequest {
    @ApiModelProperty(value = "店家订单号 最长32位", required = true)
    private String merchantOrderNo;
    @ApiModelProperty(value = "充值金额 (支持2位小数)", required = true)
    private String amount;
    @ApiModelProperty(value = "支付方式: 1银行卡 2支付宝 3 微信", required = true)
    private int paymentMethod;
}
