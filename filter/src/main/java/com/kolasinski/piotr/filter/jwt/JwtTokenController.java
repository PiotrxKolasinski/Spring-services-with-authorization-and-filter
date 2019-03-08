package com.kolasinski.piotr.filter.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/jwt-tokens")
public class JwtTokenController {
    private final UserJwtTokenRenewingService userJwtTokenRenewingService;
    private final JwtTokenProperties jwtTokenProperties;

    @Autowired
    public JwtTokenController(UserJwtTokenRenewingService userJwtTokenRenewingService, JwtTokenProperties jwtTokenProperties) {
        this.userJwtTokenRenewingService = userJwtTokenRenewingService;
        this.jwtTokenProperties = jwtTokenProperties;
    }

    @GetMapping("/users/refresh-access-token")
    public ResponseEntity renewAccessToken(HttpServletRequest request) {
        JwtToken newJwtToken = userJwtTokenRenewingService.renewAccessToken(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add(jwtTokenProperties.getAccessTokenHeader(), newJwtToken.getToken());
        return new ResponseEntity(headers, HttpStatus.OK);
    }
}
