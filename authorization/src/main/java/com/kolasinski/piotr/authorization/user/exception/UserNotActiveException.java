package com.kolasinski.piotr.authorization.user.exception;

import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotActiveException extends ExceptionHandlerResponse {

    public UserNotActiveException(String message) {
        super(message, ExceptionHandlerResponseCode.USER_NOT_ACTIVE);
    }

    public UserNotActiveException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.USER_NOT_ACTIVE, args);
    }
}
