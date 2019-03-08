package com.kolasinski.piotr.filter.httpfilter.user;

import com.kolasinski.piotr.filter.client.authorization.user.UserAuthorizationClient;
import com.kolasinski.piotr.filter.jwt.*;
import com.kolasinski.piotr.filter.role.Role;
import com.kolasinski.piotr.filter.role.RoleType;
import com.kolasinski.piotr.filter.user.User;
import com.kolasinski.piotr.filter.user.role.UserRole;
import com.netflix.util.Pair;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HttpResponseService {
    private final JwtTokenService jwtTokenService;
    private final JwtTokenHeaderExtractor jwtTokenHeaderExtractor;
    private final JwtTokenProperties jwtTokenProperties;
    private final JwtTokenBlacklistService jwtTokenBlacklistService;
    private final UserAuthorizationClient userAuthorizationClient;

    @Autowired
    public HttpResponseService(JwtTokenService jwtTokenService, JwtTokenHeaderExtractor jwtTokenHeaderExtractor, JwtTokenProperties jwtTokenProperties, JwtTokenBlacklistService jwtTokenBlacklistService, UserAuthorizationClient userAuthorizationClient) {
        this.jwtTokenService = jwtTokenService;
        this.jwtTokenHeaderExtractor = jwtTokenHeaderExtractor;
        this.jwtTokenProperties = jwtTokenProperties;
        this.jwtTokenBlacklistService = jwtTokenBlacklistService;
        this.userAuthorizationClient = userAuthorizationClient;
    }

    public String getUserEmail(RequestContext ctx) {
        List<Pair<String, String>> headers = ctx.getZuulResponseHeaders();
        for (Pair<String, String> header : headers) {
            if (header.first().equals("userEmail")) {
                return header.second();
            }
        }
        return null;
    }

    public void prepareLoginResponse(String userEmail, HttpServletResponse response) {
        User user = userAuthorizationClient.findOneByEmail(userEmail);
        List<RoleType> roles = user.getUserRoles().stream().map(UserRole::getRole).map(Role::getType).collect(Collectors.toList());
        JwtToken accessToken = jwtTokenService.createAccessToken(user.getEmail(), roles);
        JwtToken refreshToken = jwtTokenService.createRefreshToken(user.getEmail(), roles);

        response.setHeader(jwtTokenProperties.getAccessTokenHeader(), accessToken.getToken());
        response.setHeader(jwtTokenProperties.getRefreshTokenHeader(), refreshToken.getToken());
    }

    public void deleteAccessToken(HttpServletRequest request) {
        String accessToken = jwtTokenHeaderExtractor.extractAccessToken(request);
        jwtTokenBlacklistService.addTokenToBlacklist(accessToken);
    }

    public void deleteRefreshToken(HttpServletRequest request) {
        String refreshToken = jwtTokenHeaderExtractor.extractRefreshToken(request);
        jwtTokenBlacklistService.addTokenToBlacklist(refreshToken);
    }
}
