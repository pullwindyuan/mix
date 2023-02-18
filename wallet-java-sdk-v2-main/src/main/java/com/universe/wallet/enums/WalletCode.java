package com.universe.wallet.enums;

/**
 * 交易类型
 * @author Pullwind
 */

public enum WalletCode {
    /**
     *成功
     */
    SUCCESS(0, "成功"),
    /**
     *无效请求
     */
    INVALID_REQUEST(1001, "无效请求"),
    /**
     *无效的请求参数
     */
    INVALID_REQUEST_PARAMS(1002, "无效的请求参数"),
    /**
     *无效的签名
     */
    INVALID_SIGNATURE(1003, "无效的签名"),
    /**
     *抢单失败
     */

    FAILED_TO_SNATCH(2001, "抢单失败"),
    /**
     *当前订单已经被抢,请更换金额
     */
    ORDER_HAVE_SNATCHED(2002, "当前订单已经被抢,请更换金额"),
    /**
     *当前有进行中订单未完成
     */
    EXISTS_UNFINISHED_ORDER(2004, "当前有进行中订单未完成"),
    /**
     *匹配订单号不能为空
     */
    EMPTY_MATCH_ORDER_NO(2005, "匹配订单号不能为空"),
    /**
     *不存在当前没有可用金额，请稍后重试
     */
    NOT_EXIST_AVAILABLE_AMOUNT(2006, "不存在当前没有可用金额，请稍后重试"),
    /**
     *金额只支持小数点后两位
     */
    ONLY_SUPPORTS_TWO_DECIMAL(2007, "金额只支持小数点后两位"),
    /**
     *没有进行中的订单
     */
    NOT_EXIST_IN_PROGRESS_ORDER(2008, "没有进行中的订单"),
    /**
     *通道维护中
     */

    CHANNEL_IN_MAINTENANCE(3001, "通道维护中"),
    /**
     *订单状态异常
     */
    ABNORMAL_ORDER_STATUS(3002, "订单状态异常"),
    /**
     *账号被冻结
     */
    ACCOUNT_IS_FROZEN(3003, "账号被冻结"),
    /**
     *找不到资源
     */
    NOT_FOUND_RESOURCE(3004, "找不到资源"),
    /**
     *资源已存在
     */
    ALREADY_EXISTS_RESOURCE(3005, "资源已存在"),
    /**
     *资源版本冲突，请重试
     */
    RESOURCE_VERSION_CONFLICT(3006, "资源版本冲突，请重试"),
    /**
     *请求过多系统忙碌中
     */
    SYSTEM_IS_BUSY(3007, "请求过多系统忙碌中"),
    /**
     *地区IP 不在服务支援范围内
     */
    UNSUPPORTED_IP(3008, "地区IP 不在服务支援范围内"),
    /**
     *系统错误
     */
    SYSTEM_ERROR(3009, "系统错误"),
    /**
     *解密错误
     */
    DECRYPTION_ERROR(3010, "解密错误"),
    /**
     *系统维护中
     */
    SYSTEM_IN_MAINTENANCE(3011, "系统维护中"),
    /**
     *账号异常
     */
    ABNORMAL_ACCOUNT(3012, "账号异常"),
    /**
     *锁定资金不足
     */
    INSUFFICIENT_LOCKED_AMOUNT(3013, "锁定资金不足"),
    /**
     *保证金不足
     */
    INSUFFICIENT_MARGIN(3014, "保证金不足"),
    /**
     *汇率获取错误
     */
    EXCHANGE_RATE_ERROR(3015, "汇率获取错误"),
    /**
     *银行卡号错误
     */
    WRONG_BANK_CARD_NUMBER(3016, "银行卡号错误"),
    /**
     *创建提现单失败
     */
    FAILED_TO_CREATE_WITHDRAWAL_ORDER(3017, "创建提现单失败"),
    /**
     *当前站点非免审站点
     */
    NOT_AN_AUDIT_EXEMPT_SITE(3018, "当前站点非免审站点"),
    /**
     *当前站点为免审站点
     */
    AN_AUDIT_EXEMPT_SITE(3019, "当前站点为免审站点"),
    ;

    private final int code;
    private final String value;

    WalletCode(int code, String value) {
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
