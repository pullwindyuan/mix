package com.universe.wallet.enums;

/**
 * 交易类型
 * @author Pullwind
 */

public enum TradeType {
    /**
     * 充值
     */
    DEPOSIT(1, "充值"),
    /**
     * 提现
     */
    WITHDRAW(2, "提现"),

    ;
    private final int code;
    private final String value;

    TradeType(int code, String value) {
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
