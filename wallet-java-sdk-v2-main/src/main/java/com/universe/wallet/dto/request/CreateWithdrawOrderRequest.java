package com.universe.wallet.dto.request;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "店家订单号", required = true)
    private String merchantOrderNo;
    /**
     * 支付方式 1: 银行卡, 2: 微信支付， 3： 支付宝
     */
    @ApiModelProperty(value = "支付方式：1银行卡 2支付宝 3微信 4支付宝二维码 5微信二维码", required = true)
    private int paymentMethod;
    /**
     * 	收款人
     */
    @ApiModelProperty(value = "收款人", required = true)
    private String accountName;
    /**
     *银行卡号，支付宝，微信号。支付方式为2，3，且qrCodeUrl为空，则必传。
     */
    @ApiModelProperty(value = "银行卡号，支付宝，微信号。支付方式为2，3，且qrCodeUrl为空，则必传。")
    private String account;
    /**
     *银行名称
     */
    @ApiModelProperty(value = "银行名称，支付方式为银行卡，则必传")
    private String bankName;
    /**
     *	开户地址 (银行支行), 可以为空
     */
    @ApiModelProperty(value = "开户地址 (银行支行)")
    private String bankBranch;
    /**
     *提现金额 (只支持整数金额)
     */
    @ApiModelProperty(value = "提现金额 (只支持整数金额)", required = true)
    private String amount;
    /**
     * 	支付二维码链接。支付方式为2，3，且account为空，则必传。
     */
    @ApiModelProperty(value = "支付二维码链接。支付方式为2，3，且account为空，则必传。")
    private String qrCodeUrl;
}
