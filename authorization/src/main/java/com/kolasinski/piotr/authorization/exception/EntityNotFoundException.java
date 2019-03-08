package com.kolasinski.piotr.authorization.exception;

import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends ExceptionHandlerResponse {

    public EntityNotFoundException(String message) {
        super(message, ExceptionHandlerResponseCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.ENTITY_NOT_FOUND, args);
    }
}
