package com.futuremap.erp.common.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends RuntimeException {
    private final String code;
    private final String message;

    public InternalServerErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        code = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        message = "内部服务器错误";
    }

    public InternalServerErrorException(String message) {
        super(message);
        code = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        this.message = message;
    }

    public InternalServerErrorException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
