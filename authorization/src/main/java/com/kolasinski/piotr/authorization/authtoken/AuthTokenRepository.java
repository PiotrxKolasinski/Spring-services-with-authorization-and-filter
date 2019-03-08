package com.kolasinski.piotr.authorization.authtoken;

import org.springframework.data.repository.CrudRepository;

public interface AuthTokenRepository extends CrudRepository<AuthToken, Long> {
    AuthToken findByTokenAndType(String token, AuthTokenType type);

    AuthToken findByTypeAndUserEmail(AuthTokenType type, String email);
}
