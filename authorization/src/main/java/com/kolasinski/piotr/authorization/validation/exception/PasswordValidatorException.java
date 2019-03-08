package com.kolasinski.piotr.authorization.validation.exception;

import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PasswordValidatorException extends ExceptionHandlerResponse {
    public PasswordValidatorException(String message) {
        super(message, ExceptionHandlerResponseCode.PASSWORD_VALIDATOR);
    }

    public PasswordValidatorException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.PASSWORD_VALIDATOR, args);
    }
}
