package com.universe.wallet.enums;

/**
 * 金额梯度
 * @author Pullwind
 */

public enum AmountType {
    /**
     *小额
     */
    LITTLE(1, "小额"),
    /**
     *中额
     */
    MIDDLE(2, "中额"),
    /**
     *大额
     */
    LARGE(3, "大额"),
    ;
    private final int code;
    private final String value;

    AmountType(int code, String value) {
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
