package com.kolasinski.piotr.authorization.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleException(ExceptionHandlerResponse ex) {
        ex.printStackTrace();
        HttpStatus status = getStatus(ex);
        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("status", status.toString());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("code", ex.getCode());
        errorResponse.put("timestamp", ex.getTimestamp().toString());
        errorResponse.put("args", ex.getArgs());

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception ex) {
        ex.printStackTrace();
        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("status", HttpStatus.BAD_REQUEST);
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("timestamp", Instant.now().toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private HttpStatus getStatus(Exception ex) {
        return Stream.of(ex.getClass().getAnnotations())
                .filter(annotation -> annotation instanceof ResponseStatus)
                .map(ResponseStatus.class::cast)
                .map(ResponseStatus::value)
                .findFirst().orElse(HttpStatus.BAD_REQUEST);
    }
}
