package com.kolasinski.piotr.authorization.email.exception;

import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponse;
import com.kolasinski.piotr.authorization.exception.exceptionhandler.ExceptionHandlerResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EmailMessagingException extends ExceptionHandlerResponse {

    public EmailMessagingException(String message) {
        super(message, ExceptionHandlerResponseCode.EMAIL_MESSAGING);
    }

    public EmailMessagingException(String message, List<String> args) {
        super(message, ExceptionHandlerResponseCode.EMAIL_MESSAGING, args);
    }
}
