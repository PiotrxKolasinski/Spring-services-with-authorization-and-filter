package com.kolasinski.piotr.filter.jwt;

import com.kolasinski.piotr.filter.role.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class JwtTokenService {
    private final JwtTokenProperties jwtTokenProperties;

    @Autowired
    public JwtTokenService(JwtTokenProperties jwtTokenProperties) {
        this.jwtTokenProperties = jwtTokenProperties;
    }

    public JwtToken createAccessToken(String subject, List<RoleType> roles) {
        Instant currentTime = Instant.now();
        Instant expirationTime = currentTime.plusSeconds(jwtTokenProperties.getExpirationTimeAccessToken());

        return getJwtToken(currentTime, expirationTime, prepareClaims(subject, roles, JwtTokenScopes.ACCESS_TOKEN));
    }

    public JwtToken createRefreshToken(String subject, List<RoleType> roles) {
        Instant currentTime = Instant.now();
        Instant expirationTime = currentTime.plusSeconds(jwtTokenProperties.getExpirationTimeRefreshToken());

        return getJwtToken(currentTime, expirationTime, prepareClaims(subject, roles, JwtTokenScopes.REFRESH_TOKEN));
    }

    private Claims prepareClaims(String userEmail, List<RoleType> roles, JwtTokenScopes refreshToken) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("scope", refreshToken);
        claims.put("roles", roles);
        return claims;
    }

    private JwtToken getJwtToken(Instant currentTime, Instant expirationTime, Claims claims) {
        String token = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(claims)
                .setIssuedAt(Date.from(currentTime))
                .setIssuer(jwtTokenProperties.getIssuer())
                .setExpiration(Date.from(expirationTime))
                .signWith(SignatureAlgorithm.HS256, getSecret())
                .compact();

        return new JwtToken(token, claims);
    }

    public Claims getBody(String token) {
        return Jwts.parser()
                .setSigningKey(getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    private String getSecret() {
        return Base64.getEncoder().encodeToString(jwtTokenProperties.getSecret().getBytes());
    }
}
