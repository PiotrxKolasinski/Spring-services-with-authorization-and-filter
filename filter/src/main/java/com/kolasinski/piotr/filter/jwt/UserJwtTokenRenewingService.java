package com.kolasinski.piotr.filter.jwt;

import com.kolasinski.piotr.filter.client.authorization.user.UserAuthorizationClient;
import com.kolasinski.piotr.filter.role.Role;
import com.kolasinski.piotr.filter.role.RoleType;
import com.kolasinski.piotr.filter.user.User;
import com.kolasinski.piotr.filter.user.role.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.kolasinski.piotr.filter.role.RoleType.ROLE_USER;

@Service
public class UserJwtTokenRenewingService {
    private final JwtTokenService jwtTokenService;
    private final JwtTokenHeaderExtractor headerExtractor;
    private final JwtTokenBlacklistService jwtTokenBlacklistService;
    private final UserAuthorizationClient userAuthorizationClient;
    private final JwtTokenVerification jwtTokenVerification;

    @Autowired
    public UserJwtTokenRenewingService(JwtTokenService jwtTokenService, JwtTokenHeaderExtractor headerExtractor,
                                       JwtTokenBlacklistService jwtTokenBlacklistService, UserAuthorizationClient userAuthorizationClient,
                                       JwtTokenVerification jwtTokenVerification) {
        this.jwtTokenService = jwtTokenService;
        this.headerExtractor = headerExtractor;
        this.jwtTokenBlacklistService = jwtTokenBlacklistService;
        this.userAuthorizationClient = userAuthorizationClient;
        this.jwtTokenVerification = jwtTokenVerification;
    }

    public JwtToken renewAccessToken(HttpServletRequest request) {
        String accessToken = headerExtractor.extractAccessToken(request);
        String refreshToken = headerExtractor.extractRefreshToken(request);
        jwtTokenVerification.checkAuthentication(accessToken, refreshToken, new ArrayList<>(Collections.singletonList(ROLE_USER)));

        User user = userAuthorizationClient.findOneByEmail(jwtTokenService.getBody(accessToken).getSubject());
        List<RoleType> roles = user.getUserRoles().stream().map(UserRole::getRole).map(Role::getType).collect(Collectors.toList());

        JwtToken jwtAccessToken = jwtTokenService.createAccessToken(user.getEmail(), roles);
        jwtTokenBlacklistService.addTokenToBlacklist(accessToken);
        return jwtAccessToken;
    }
}
