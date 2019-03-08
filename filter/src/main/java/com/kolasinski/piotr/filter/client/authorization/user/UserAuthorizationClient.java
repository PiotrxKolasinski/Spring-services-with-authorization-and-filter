package com.kolasinski.piotr.filter.client.authorization.user;

import com.kolasinski.piotr.filter.client.authorization.exception.AuthenticationHttpErrorException;
import com.kolasinski.piotr.filter.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class UserAuthorizationClient {
    private final String USER_AUTHENTICATION_URL = "/internal/users";

    @Value("${custom.authorization.url}")
    private String authorizationUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public UserAuthorizationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User findOneByEmail(String email) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(authorizationUrl + USER_AUTHENTICATION_URL)
                .queryParam("email", email);
        try {
            return restTemplate.getForEntity(builder.toUriString(), User.class).getBody();
        } catch (Exception ex) {
            throw new AuthenticationHttpErrorException("Http exception");
        }
    }
}
