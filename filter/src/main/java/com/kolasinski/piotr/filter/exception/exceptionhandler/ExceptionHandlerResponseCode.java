package com.kolasinski.piotr.filter.exception.exceptionhandler;

public enum ExceptionHandlerResponseCode {
    UNAUTHORIZED_HTTP_REQUEST,
    UNAUTHORIZED_HTTP_ERROR,

    WRONG_CREDENTIALS,
    ENTITY_NOT_FOUND,
    FILTER_PROXY_CONNECTION_FAILED,

    JWT_TOKEN_AUTH_FAILED,
    JWT_TOKEN_NOT_FOUND
}
