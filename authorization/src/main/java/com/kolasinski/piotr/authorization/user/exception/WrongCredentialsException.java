package com.kolasinski.piotr.authorization.user.exception;

import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongCredentialsException extends ExceptionHandlerResponse {

    public WrongCredentialsException(String message) {
        super(message, ExceptionHandlerResponseCode.WRONG_CREDENTIALS);
    }

    public WrongCredentialsException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.WRONG_CREDENTIALS, args);
    }
}
