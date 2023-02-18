package com.futuremap.erp.common;

import com.futuremap.erp.common.exception.ApiException;
import com.futuremap.erp.common.exception.BadRequestException;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.common.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author ken
 * @title GlobalExceptionHandler
 * @description 全局异常处理
 * @date 2020/9/30 17:46
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler. class);

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public BaseResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return BaseResult.failed(e.getErrorCode());
        }
        return BaseResult.failed(e.getMessage());
    }


    /**
     * 处理验证失败异常
     * @param e MethodArgumentNotValidException
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResult handleValidException(MethodArgumentNotValidException e) {
        logger.info("MethodArgumentNotValidException:{}", e.getMessage());
        return handleValidException(e.getBindingResult());
    }

    /**
     * 处理验证失败异常
     * @param e BindException
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public BaseResult handleValidException(BindException e) {
        logger.info("BindException:{}", e.getMessage());
        return handleValidException(e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = BadRequestException.class)
    public BaseResult handleBadRequestException(Exception e) {
        logger.error(e.getMessage(),e);
        return BaseResult.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public BaseResult handle(Throwable e) {
        logger.error(e.getMessage(),e);
        return BaseResult.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = FuturemapBaseException.class)
    public BaseResult handle(FuturemapBaseException e) {
        if (e.getCode() != null) {
            return BaseResult.failed(e.getCode(),e.getMessage());
        }
        return BaseResult.failed(e.getMessage());
    }

    /**
     * 处理验证失败异常
     * @param bindingResult
     * @return
     */
    private BaseResult handleValidException(BindingResult bindingResult) {
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return BaseResult.validateFailed(message);
    }
}
