package com.universe.wallet.enums;

/**
 * 交易类型
 * @author Pullwind
 */

public enum OrderStatus {
    /**
     * 待匹配
     */
    MATCH(2, "待匹配"),
    /**
     * 待付款
     */
    PAYING(3, "待付款"),
    /**
     * 付款延时
     */
    PAYING_DELAY(4, "付款延时"),
    /**
     * 待放行
     */
    PENDING_RELEASE(5, "待放行"),
    /**
     * 放行延时
     */
    PENDING_RELEASE_DELAY(6, "放行延时"),
    /**
     * 投诉
     */
    COMPLAINT(7, "投诉"),
    /**
     * 维权
     */
    RIGHTS_PROTECTION(8, "维权"),
    /**
     * 交易成功
     */
    TRANSACTION_SUCCESSFUL(9, "交易成功"),
    /**
     * 交易失败
     */
    TRANSACTION_FAILED(10, "交易失败"),
    ;


    private final int status;
    private final String value;

    OrderStatus(int status, String value) {
        this.value = value;
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public String getValue() {
        return this.value;
    }

}
