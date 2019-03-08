package com.kolasinski.piotr.filter.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtTokenProperties {

    @Value("${custom.security.issuer}")
    private String issuer;

    @Value("${custom.security.secret}")
    private String secret;

    @Value("${custom.jwt-token.x-auth-access-token}")
    private String accessTokenHeader;

    @Value("${custom.jwt-token.x-auth-refresh-token}")
    private String refreshTokenHeader;

    @Value("${custom.jwt-token.expiration-time-access-token}")
    private long expirationTimeAccessToken;

    @Value("${custom.jwt-token.expiration-time-refresh-token}")
    private long expirationTimeRefreshToken;
}
