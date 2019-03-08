package com.kolasinski.piotr.filter.jwt;

import org.springframework.stereotype.Service;

@Service
public class JwtTokenBlacklistService {
    public void addTokenToBlacklist(String token) {
        JwtTokenBlacklist.tokens.add(token);
    }

    public boolean isTokenInBlackList(String token) {
        return JwtTokenBlacklist.tokens.contains(token);
    }
}
