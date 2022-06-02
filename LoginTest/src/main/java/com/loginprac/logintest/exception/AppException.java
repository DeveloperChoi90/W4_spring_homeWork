package com.loginprac.logintest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//====================================================================
/*
 * 요청이 유효하지 않거나 예상치 못한 상황이 발생하면 API에서 예외가 발생
 * */
//====================================================================


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
