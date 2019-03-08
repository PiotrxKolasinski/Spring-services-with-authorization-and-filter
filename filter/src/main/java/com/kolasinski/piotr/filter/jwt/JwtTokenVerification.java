package com.kolasinski.piotr.filter.jwt;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.kolasinski.piotr.filter.jwt.exception.JwtTokenAuthFailedException;
import com.kolasinski.piotr.filter.role.RoleType;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtTokenVerification {
    private final JwtTokenService jwtTokenService;
    private final JwtTokenBlacklistService jwtTokenBlacklistService;

    @Autowired
    public JwtTokenVerification(JwtTokenService jwtTokenService, JwtTokenBlacklistService jwtTokenBlacklistService) {
        this.jwtTokenService = jwtTokenService;
        this.jwtTokenBlacklistService = jwtTokenBlacklistService;
    }

    public void checkAccessToken(String accessToken, List<RoleType> roles) {
        if (!isAuthenticated(accessToken, JwtTokenScopes.ACCESS_TOKEN, roles)) {
            throw new JwtTokenAuthFailedException("Jwt authentication failed");
        }
    }

    public void checkAuthentication(String accessToken, String refreshToken, List<RoleType> roles) {
        if (!isAuthenticated(accessToken, JwtTokenScopes.ACCESS_TOKEN, roles) || !isAuthenticated(refreshToken, JwtTokenScopes.REFRESH_TOKEN, roles) || !checkSubjects(accessToken, refreshToken)) {
            throw new JwtTokenAuthFailedException("Jwt authentication failed");
        }
    }

    private boolean isAuthenticated(String token, JwtTokenScopes scope, List<RoleType> roles) {
        if (jwtTokenBlacklistService.isTokenInBlackList(token)) {
            return false;
        }

        Claims claims = jwtTokenService.getBody(token);
        String tokenScope = claims.get("scope", String.class);
        List tokenRoles = claims.get("roles", List.class);

        boolean tokenScopeValid = tokenScope != null &&
                !tokenScope.isEmpty() &&
                tokenScope.equals(scope.toString());
        boolean tokenRolesValid = tokenRoles != null &&
                !tokenRoles.isEmpty() &&
                tokenRoles.containsAll(roles.stream().map(Functions.toStringFunction()::apply).collect(Collectors.toList()));

        return tokenScopeValid && tokenRolesValid;
    }

    private boolean checkSubjects(String accessToken, String refreshToken) {
        String accessTokenSubject = jwtTokenService.getBody(accessToken).getSubject();
        String refreshTokenSubject = jwtTokenService.getBody(refreshToken).getSubject();
        return accessTokenSubject.equals(refreshTokenSubject);
    }
}
