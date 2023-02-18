package com.futuremap.erp.common.result;

/**
 * @author ken
 * @title ResultCode
 * @description 常用API操作码
 * @date 2020/9/30 17:21
 */
public enum ResultCode implements IErrorCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    FAILED(-1, "操作失败"),
    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(1000404, "参数检验失败"),
    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(1000401, "暂未登录或token已经过期"),
    /**
     * 没有相关权限
     */
    FORBIDDEN(1000403, "没有相关权限");

    /**
     * 错误码
     * @return long
     */
    private long code;
    /**
     * 错误信息
     * @return String
     */
    private String message;

    /**
     * 构造方法
     * @param code    错误码
     * @param message 错误信息
     */
    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * get 方法
     * @return  long
     */
    @Override
    public long getCode() {
        return code;
    }

    /**
     * get 方法
     * @return  String
     */
    @Override
    public String getMessage() {
        return message;
    }
}
