package com.universe.wallet.enums;

/**
 * 审核类型
 * @author Pullwind
 */

public enum AuditType {
    /**
     * 充值
     */
    PASS(1, "通过"),
    /**
     * 提现
     */
    REJECT(2, "拒绝"),

    ;
    private final int code;
    private final String value;

    AuditType(int code, String value) {
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
