package com.kolasinski.piotr.authorization.user.exception;

import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyTakenException extends ExceptionHandlerResponse {

    public EmailAlreadyTakenException(String message) {
        super(message, ExceptionHandlerResponseCode.EMAIL_ALREADY_TAKEN);
    }

    public EmailAlreadyTakenException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.EMAIL_ALREADY_TAKEN, args);
    }
}
