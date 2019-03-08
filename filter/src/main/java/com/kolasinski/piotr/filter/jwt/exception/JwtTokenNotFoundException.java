package com.kolasinski.piotr.filter.jwt.exception;

import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JwtTokenNotFoundException extends ExceptionHandlerResponse {

    public JwtTokenNotFoundException(String message) {
        super(message, ExceptionHandlerResponseCode.JWT_TOKEN_NOT_FOUND);
    }

    public JwtTokenNotFoundException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.JWT_TOKEN_NOT_FOUND, args);
    }
}
