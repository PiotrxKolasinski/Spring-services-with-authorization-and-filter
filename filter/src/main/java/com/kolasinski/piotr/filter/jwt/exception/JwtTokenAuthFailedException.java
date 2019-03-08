package com.kolasinski.piotr.filter.jwt.exception;

import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtTokenAuthFailedException extends ExceptionHandlerResponse {

    public JwtTokenAuthFailedException(String message) {
        super(message, ExceptionHandlerResponseCode.JWT_TOKEN_AUTH_FAILED);
    }

    public JwtTokenAuthFailedException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.JWT_TOKEN_AUTH_FAILED, args);
    }
}
