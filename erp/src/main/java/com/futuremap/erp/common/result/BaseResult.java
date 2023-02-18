package com.futuremap.erp.common.result;

/**
 * @author ken
 * @title BaseResult
 * @description 基础返回对象
 * @date 2020/9/30 17:16
 */
public class BaseResult<T> {

    /**
     * 状态码
     */
    private long code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 数据封装
     */
    private T data;

    protected BaseResult() {
    }

    protected BaseResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }
    /**
     * 成功返回结果
     *
     */
    public static <T> BaseResult<T> success() {
        return new BaseResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> BaseResult<T> success(T data, String message) {
        return new BaseResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> BaseResult<T> failed(IErrorCode errorCode) {
        return new BaseResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> BaseResult<T> failed(IErrorCode errorCode, String message) {
        return new BaseResult<T>(errorCode.getCode(), message, null);
    }
    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> BaseResult<T> failed(Long errorCode, String message) {
        return new BaseResult<T>(errorCode, message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> BaseResult<T> failed(String message) {
        return new BaseResult<T>( ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> BaseResult<T> failed() {
        return failed( ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> BaseResult<T> validateFailed() {
        return failed( ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> BaseResult<T> validateFailed(String message) {
        return new BaseResult<T>( ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> BaseResult<T> unauthorized(T data) {
        return new BaseResult<T>( ResultCode.UNAUTHORIZED.getCode(),  ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> BaseResult<T> forbidden(T data) {
        return new BaseResult<T>( ResultCode.FORBIDDEN.getCode(),  ResultCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * get 方法
     * @return  long
     */
    public long getCode() {
        return code;
    }
    /**
     * set 方法
     */
    public void setCode(long code) {
        this.code = code;
    }
    /**
     * get 方法
     * @return  String
     */
    public String getMessage() {
        return message;
    }
    /**
     * set 方法
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * get 方法
     * @return  T
     */
    public T getData() {
        return data;
    }
    /**
     * set 方法
     */
    public void setData(T data) {
        this.data = data;
    }
}
