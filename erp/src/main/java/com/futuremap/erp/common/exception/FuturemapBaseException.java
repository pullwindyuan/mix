/*
 * Copyright © 2017-2021 http://www.futuremap.com.cn/ All rights reserved.
 *
 * Statement: This document's code after sufficiently has not permitted does not have
 * any way dissemination and the change, once discovered violates the stipulation, will
 * receive the criminal sanction.
 * Address: Building A, block 1F,  Tian'an Yungu Industrial Park phase I,
 *          Xuegang Road, Bantian street, Longgang District, Shenzhen
 * Tel: 0755-22674916
 */
package com.futuremap.erp.common.exception;

import com.futuremap.erp.common.result.IErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 * @title FuturemapBaseException
 * @description TODO
 * @date 2021/6/23 10:53
 */
 @Data
 @EqualsAndHashCode(callSuper = false)
public class FuturemapBaseException extends RuntimeException{
    private  Long code;
    private  String message;
    private IErrorCode errorCode;

    public FuturemapBaseException(String message) {
        super(message);
        this.message = message;
    }

    public FuturemapBaseException(Long code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public FuturemapBaseException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


}
