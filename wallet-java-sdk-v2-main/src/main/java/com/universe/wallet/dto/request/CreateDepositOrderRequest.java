package com.universe.wallet.dto.request;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "店家订单号", required = true)
    private String merchantOrderNo;
    /**
     * 支付方式 1: 银行卡。目前只有这么一种，后面可能扩展
     */
    @ApiModelProperty(value = "支付方式: 1银行卡 2支付宝 3微信", required = true)
    private int paymentMethod;
    /**
     *充值金额 (只支持整数金额)
     */
    @ApiModelProperty(value = "充值金额 (只支援整数金额)", required = true)
    private String amount;
    /**
     * 匹配单号
     */
    @ApiModelProperty(value = "匹配单号", required = true)
    private String orderNo;
}
