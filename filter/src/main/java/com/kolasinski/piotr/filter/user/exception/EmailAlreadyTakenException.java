package com.kolasinski.piotr.filter.user.exception;

import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyTakenException extends ExceptionHandlerResponse {

    public EmailAlreadyTakenException(String message) {
        super(message, ExceptionHandlerResponseCode.ENTITY_NOT_FOUND);
    }

    public EmailAlreadyTakenException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.ENTITY_NOT_FOUND, args);
    }
}
