package com.kolasinski.piotr.services.exception.exceptionhandler;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class ExceptionHandlerResponse extends RuntimeException {
    private String message;
    private ExceptionHandlerResponseCode code;
    private Instant timestamp;
    private final List<String> args;

    public ExceptionHandlerResponse(String message, ExceptionHandlerResponseCode code) {
        super(message);
        this.message = message;
        this.code = code;
        this.timestamp = Instant.now();
        args = new ArrayList<>();
    }

    public ExceptionHandlerResponse(String message, ExceptionHandlerResponseCode code, List<String> args) {
        super(message);
        this.message = message;
        this.code = code;
        this.timestamp = Instant.now();
        this.args = args;
    }
}
