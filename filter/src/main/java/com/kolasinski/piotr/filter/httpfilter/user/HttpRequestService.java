package com.kolasinski.piotr.filter.httpfilter.user;

import com.kolasinski.piotr.filter.client.authorization.user.UserAuthorizationClient;
import com.kolasinski.piotr.filter.httpfilter.HttpRequestAuthorizationType;
import com.kolasinski.piotr.filter.jwt.JwtTokenHeaderExtractor;
import com.kolasinski.piotr.filter.jwt.JwtTokenService;
import com.kolasinski.piotr.filter.jwt.JwtTokenVerification;
import com.kolasinski.piotr.filter.httpfilter.exception.UnauthorizedHttpRequestException;
import com.kolasinski.piotr.filter.role.RoleType;
import com.kolasinski.piotr.filter.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.kolasinski.piotr.filter.role.RoleType.ROLE_USER;

@Service
public class HttpRequestService {
    private final List<VerificationRequest> accessTokenAndRefreshTokenVerificationRequests = new ArrayList<>(Arrays.asList(
            new VerificationRequest("auth/logout", HttpMethod.POST),
            new VerificationRequest("auth", HttpMethod.DELETE)
    ));

    private final List<VerificationRequest> permitAllRequests = new ArrayList<>(Arrays.asList(
            new VerificationRequest("auth/login", HttpMethod.POST),
            new VerificationRequest("auth/registration", HttpMethod.POST),
            new VerificationRequest("auth/confirming-email/", HttpMethod.GET)
    ));

    private final JwtTokenHeaderExtractor jwtTokenHeaderExtractor;
    private final JwtTokenVerification jwtTokenVerification;
    private final JwtTokenService jwtTokenService;
    private final UserAuthorizationClient userAuthorizationClient;

    @Autowired
    public HttpRequestService(JwtTokenHeaderExtractor jwtTokenHeaderExtractor, JwtTokenVerification jwtTokenVerification, JwtTokenService jwtTokenService, UserAuthorizationClient userAuthorizationClient) {
        this.jwtTokenHeaderExtractor = jwtTokenHeaderExtractor;
        this.jwtTokenVerification = jwtTokenVerification;
        this.jwtTokenService = jwtTokenService;
        this.userAuthorizationClient = userAuthorizationClient;
    }

    public HttpRequestAuthorizationType getAuthorizationType(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());

        for (VerificationRequest verificationRequest: accessTokenAndRefreshTokenVerificationRequests) {
            if (requestPath.contains(verificationRequest.getUri()) && verificationRequest.getMethod().equals(requestMethod)) {
                 return HttpRequestAuthorizationType.ACCESS_TOKEN_AND_REFRESH_TOKEN_VERIFICATION;
            }
        }

        for (VerificationRequest verificationRequest: permitAllRequests) {
            if (requestPath.contains(verificationRequest.getUri()) && verificationRequest.getMethod().equals(requestMethod)) {
                return HttpRequestAuthorizationType.PERMIT_ALL;
            }
        }

        return HttpRequestAuthorizationType.ACCESS_TOKEN_VERIFICATION;
    }

    public User verifyAccessTokenAndGetUser(HttpServletRequest request) {
        String accessToken = jwtTokenHeaderExtractor.extractAccessToken(request);

        jwtTokenVerification.checkAccessToken(accessToken, new ArrayList<>(Collections.singletonList(ROLE_USER)));

        return getUser(accessToken);
    }

    public User verifyAccessTokenAndRefreshTokenAndGetUser(HttpServletRequest request) {
        String accessToken = jwtTokenHeaderExtractor.extractAccessToken(request);
        String refreshToken = jwtTokenHeaderExtractor.extractRefreshToken(request);

        jwtTokenVerification.checkAuthentication(accessToken, refreshToken, new ArrayList<>(Collections.singletonList(ROLE_USER)));

        return getUser(accessToken);
    }

    private User getUser(String accessToken) {
        String userEmail = jwtTokenService.getBody(accessToken).getSubject();
        User user  = userAuthorizationClient.findOneByEmail(userEmail);

        if (user == null) {
            throw new UnauthorizedHttpRequestException("Unauthorized exception");
        }

        return user;
    }

    @Data
    @AllArgsConstructor
    private class VerificationRequest {
        private String uri;
        private HttpMethod method;
    }
}
