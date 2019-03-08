package com.kolasinski.piotr.filter.client.authorization.exception;

import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.filter.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthenticationHttpErrorException extends ExceptionHandlerResponse {

    public AuthenticationHttpErrorException(String message) {
        super(message, ExceptionHandlerResponseCode.UNAUTHORIZED_HTTP_ERROR);
    }

    public AuthenticationHttpErrorException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.UNAUTHORIZED_HTTP_ERROR, args);
    }
}
