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
package com.futuremap.erp.common;

import com.futuremap.erp.common.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * @title GlobalDataHandler
 * @description 统一返回数据处理
 * @date 2021/4/8 18:52
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.futuremap")
public class GlobalDataHandler implements ResponseBodyAdvice<Object> {

    public static final String BASE_RESULT_CLASS = BaseResult.class.getName();

    @Override
    public Object beforeBodyWrite(Object arg0, MethodParameter arg1, MediaType arg2,
                                  Class<? extends HttpMessageConverter<?>> arg3, ServerHttpRequest request, ServerHttpResponse response) {

        final String returnTypeName = arg1.getParameterType().getName();
        ServletServerHttpResponse servletServerHttpResponse = (ServletServerHttpResponse) response;
        HttpServletResponse res = servletServerHttpResponse.getServletResponse();
        if(res.getStatus() == HttpStatus.OK.value()){
            // 可判断方法返回类型，做出相应返回处理。
            if ("void".equals(returnTypeName)){
                return BaseResult.success();
            }
            if ("java.lang.String".equals(returnTypeName)   ){
                return arg0;
            }
            if (!BASE_RESULT_CLASS.equals(returnTypeName)   ){
                return BaseResult.success(arg0);
            }
        }

        return arg0;
    }

    @Override
    public boolean supports(MethodParameter arg0, Class<? extends HttpMessageConverter<?>> arg1) {

//        final String returnTypeName = arg0.getParameterType().getName();
//
//        return !BASE_RESULT_CLASS.equals(returnTypeName);
        return true;
    }



}