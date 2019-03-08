package com.kolasinski.piotr.filter.exception;

import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.PROXY_AUTHENTICATION_REQUIRED)
public class FilterProxyConnectionFailedException extends ExceptionHandlerResponse {

    public FilterProxyConnectionFailedException(String message) {
        super(message, ExceptionHandlerResponseCode.FILTER_PROXY_CONNECTION_FAILED);
    }

    public FilterProxyConnectionFailedException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.FILTER_PROXY_CONNECTION_FAILED, args);
    }
}
