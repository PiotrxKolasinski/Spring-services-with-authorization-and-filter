package com.kolasinski.piotr.authorization.exception.exceptionhandler;

public enum ExceptionHandlerResponseCode {
    EMAIL_ALREADY_TAKEN,

    PASSWORD_VALIDATOR,

    WRONG_CREDENTIALS,
    USER_NOT_ACTIVE,
    ENTITY_NOT_FOUND,
    FILTER_PROXY_CONNECTION_FAILED,

    REQUEST_HEADER_NOT_FOUND,

    AUTH_TOKEN_EXPIRED,

    EMAIL_MESSAGING
}
