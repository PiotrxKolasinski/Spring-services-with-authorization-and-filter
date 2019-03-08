package com.kolasinski.piotr.filter.jwt;

import com.kolasinski.piotr.filter.jwt.exception.JwtTokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtTokenHeaderExtractor {
    private final JwtTokenProperties jwtTokenProperties;

    @Autowired
    public JwtTokenHeaderExtractor(JwtTokenProperties jwtTokenProperties) {
        this.jwtTokenProperties = jwtTokenProperties;
    }

    public String extractAccessToken(HttpServletRequest request) {
        try {
            return request.getHeader(jwtTokenProperties.getAccessTokenHeader());
        }  catch (Exception e) {
            throw new JwtTokenNotFoundException("Cannot find required header");
        }
    }

    public String extractRefreshToken(HttpServletRequest request) {
        try {
            return request.getHeader(jwtTokenProperties.getRefreshTokenHeader());
        }  catch (Exception e) {
            throw new JwtTokenNotFoundException("Cannot find required header");
        }
    }
}
