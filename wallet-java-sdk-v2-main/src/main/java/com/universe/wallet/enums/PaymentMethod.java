package com.universe.wallet.enums;

/**
 * 充值方式
 * @author Pullwind
 */

public enum PaymentMethod {
    /**
     *银行卡
     */
    BANK(1, "银行卡"),

    /**
     *微信支付
     */
    WXPAY(2, "微信支付"),
    /**
     *支付宝
     */
    ALIPAY(3, "支付宝"),
    ;
    private final int code;
    private final String value;

    PaymentMethod(int code, String value) {
        this.value = value;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }

}
