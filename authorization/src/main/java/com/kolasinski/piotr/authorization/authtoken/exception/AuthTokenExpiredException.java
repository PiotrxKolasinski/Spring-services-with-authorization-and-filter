package com.kolasinski.piotr.authorization.authtoken.exception;

import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.CONFLICT)
public class AuthTokenExpiredException extends ExceptionHandlerResponse {

    public AuthTokenExpiredException(String message) {
        super(message, ExceptionHandlerResponseCode.AUTH_TOKEN_EXPIRED);
    }

    public AuthTokenExpiredException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.AUTH_TOKEN_EXPIRED, args);
    }
}
