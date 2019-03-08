package com.kolasinski.piotr.filter.httpfilter.exception;

import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedHttpRequestException extends ExceptionHandlerResponse {

    public UnauthorizedHttpRequestException(String message) {
        super(message, ExceptionHandlerResponseCode.UNAUTHORIZED_HTTP_REQUEST);
    }

    public UnauthorizedHttpRequestException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.UNAUTHORIZED_HTTP_REQUEST, args);
    }
}
