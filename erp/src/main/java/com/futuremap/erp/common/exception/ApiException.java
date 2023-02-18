package com.futuremap.erp.common.exception;


import com.futuremap.erp.common.result.IErrorCode;

/**
 * @author ken
 * @title ApiException
 * @description 自定义API异常
 * @date 2020/9/30 17:40
 */
public class ApiException extends RuntimeException {

    /**
     * 错误码接口
     */
    private IErrorCode errorCode;

    /**
     * 构造方法
     * @param errorCode 错误码接口实现
     */
    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     * @param errorCode 错误码接口实现
     * @param message   提示信息
     */
    public ApiException(IErrorCode errorCode, String message) {
        super(errorCode.getMessage() + message);
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     * @param message 错误信息
     */
    public ApiException(String message) {
        super(message);
    }

    /**
     * 构造方法
     * @param cause   错误堆栈
     */
    public ApiException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造方法
     * @param message 错误信息
     * @param cause   错误堆栈
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * get 方法
     * @return IErrorCode
     */
    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
