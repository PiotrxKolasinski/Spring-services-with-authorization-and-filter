package com.kolasinski.piotr.services.util.exception;

import com.kolasinski.piotr.services.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.services.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequestHeaderNotFoundException extends ExceptionHandlerResponse {

    public RequestHeaderNotFoundException(String message) {
        super(message, ExceptionHandlerResponseCode.REQUEST_HEADER_NOT_FOUND);
    }

    public RequestHeaderNotFoundException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.REQUEST_HEADER_NOT_FOUND, args);
    }
}
