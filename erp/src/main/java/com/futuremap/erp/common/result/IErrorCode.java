package com.futuremap.erp.common.result;

/**
 * @author ken
 * @title IErrorCode
 * @description 封装API的错误码
 * @date 2020/9/30 17:18
 */
public interface IErrorCode {
    /**
     * 错误码
     * @return long
     */
    long getCode();
    /**
     * 错误信息
     * @return String
     */
    String getMessage();
}
