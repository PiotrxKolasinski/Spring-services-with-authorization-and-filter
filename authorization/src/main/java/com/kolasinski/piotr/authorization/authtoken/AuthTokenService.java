package com.kolasinski.piotr.authorization.authtoken;

import com.kolasinski.piotr.authorization.authtoken.exception.AuthTokenExpiredException;
import com.kolasinski.piotr.authorization.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class AuthTokenService {
    private final static Logger logger = Logger.getLogger(AuthTokenService.class.getName());

    @Value("${custom.auth-token.expiration-time}")
    private long tokenExpirationTime;

    private final AuthTokenRepository authTokenRepository;

    @Autowired
    public AuthTokenService(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    public AuthToken createToken(String userEmail, AuthTokenType type) {
        deleteIfExists(userEmail, type);

        UUID secretKey = UUID.randomUUID();
        String token = createHash(secretKey + userEmail);

        AuthToken authToken = authTokenRepository.save(
                AuthToken.builder()
                        .type(type)
                        .userEmail(userEmail)
                        .token(token)
                        .createDate(Instant.now())
                        .build()
        );
        logger.info("[AuthToken] save auth token with id: " + authToken.getId());
        return authToken;
    }

    public AuthToken checkToken(String token, AuthTokenType type) {
        AuthToken authToken = authTokenRepository.findByTokenAndType(token, type);

        if (authToken == null) {
            throw new EntityNotFoundException("Cannot find auth token with token: " + token + " and type: " + type);
        }

        if (isTokenExpired(authToken)) {
            throw new AuthTokenExpiredException("Auth token is expired");
        }

        return authToken;
    }

    public void delete(long id) {
        authTokenRepository.deleteById(id);
        logger.info("[AuthToken] deleteByIdAndEmail auth token with id: " + id);
    }

    private void deleteIfExists(String userEmail, AuthTokenType type) {
        AuthToken authToken = authTokenRepository.findByTypeAndUserEmail(type, userEmail);
        if (authToken != null) {
            authTokenRepository.delete(authToken);
            logger.info("[AuthToken] deleteByIdAndEmail auth token with id: " + authToken.getId());
        }
    }

    private boolean isTokenExpired(AuthToken authToken) {
        return authToken.getCreateDate().getEpochSecond() < Instant.now().getEpochSecond() - tokenExpirationTime;
    }

    private String createHash(String value) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");
            md5.update((value).getBytes());
            return DatatypeConverter.printHexBinary(md5.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("No such algorithm", ex);
        }
    }
}
