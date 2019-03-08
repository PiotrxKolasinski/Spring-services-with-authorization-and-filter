package com.kolasinski.piotr.authorization.auth;

import com.kolasinski.piotr.authorization.authtoken.AuthToken;
import com.kolasinski.piotr.authorization.authtoken.AuthTokenService;
import com.kolasinski.piotr.authorization.authtoken.AuthTokenType;
import com.kolasinski.piotr.authorization.email.EmailService;
import com.kolasinski.piotr.authorization.exception.EntityNotFoundException;
import com.kolasinski.piotr.authorization.user.User;
import com.kolasinski.piotr.authorization.user.UserService;
import com.kolasinski.piotr.authorization.user.exception.EmailAlreadyTakenException;
import com.kolasinski.piotr.authorization.user.exception.UserNotActiveException;
import com.kolasinski.piotr.authorization.user.exception.WrongCredentialsException;
import com.kolasinski.piotr.authorization.util.Pair;
import com.kolasinski.piotr.authorization.validation.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.logging.Logger;

@Service
public class AuthService {
    private final static Logger logger = Logger.getLogger(AuthService.class.getName());

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthTokenService authTokenService;
    private final PasswordValidator passwordValidator;

    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder, EmailService emailService,
                       AuthTokenService authTokenService, PasswordValidator passwordValidator) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.authTokenService = authTokenService;
        this.passwordValidator = passwordValidator;
    }

    public Pair<User, HttpHeaders> login(AuthLogin authLogin) {
        User user = userService.findOneByEmail(authLogin.getEmail());

        checkCredentials(user, authLogin);

        if (!user.isActive()) {
            throw new UserNotActiveException("User with email " + user.getEmail() + " is not active");
        }

        HttpHeaders headers = doLoginHttpHeaders(user.getEmail());
        logger.info("[AuthService] login user with email: " + user.getEmail());
        return new Pair<>(user, headers);
    }

    private void checkCredentials(User user, AuthLogin authLogin) {
        if (user == null || !passwordEncoder.matches(authLogin.getPassword(), user.getPassword())) {
            throw new WrongCredentialsException("Wrong credentials");
        }
    }

    private HttpHeaders doLoginHttpHeaders(String userEmail) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("userEmail", userEmail);
        return headers;
    }

    public void register(AuthRegistration authRegistration) {
        User existingUser = userService.findOneByEmail(authRegistration.getEmail());

        if (existingUser != null) {
            throw new EmailAlreadyTakenException("Email already taken");
        }

        passwordValidator.isValid(authRegistration.getPassword());

        User user = userService.save(authRegistration);
        AuthToken authToken = authTokenService.createToken(user.getEmail(), AuthTokenType.ACCOUNT_ACTIVATION);
        emailService.sendEmailWithAuthToken(user.getEmail(), authToken, "en");
    }

    public void confirmEmail(String token) {
        AuthToken authToken = authTokenService.checkToken(token, AuthTokenType.ACCOUNT_ACTIVATION);
        User user = userService.findOneByEmail(authToken.getUserEmail());

        if (user == null) {
            throw new EntityNotFoundException("Cannot find user with email: " + authToken.getUserEmail());
        }

        userService.activateUserAccount(user.getId());
        authTokenService.delete(authToken.getId());
        logger.info("[AuthService] confirm user account with email: " + user.getEmail());
    }

    public void logout(long userId) {
        logger.info("[AuthService] logout user with id: " + userId);
    }

    public void delete(long userId) {
        User user = userService.findOne(userId);

        if (user == null) {
            throw new EntityNotFoundException("Cannot find user with request subject");
        }

        userService.deleteByIdAndEmail(user.getId(), user.getEmail());

        logger.info("[AuthService] deleteByIdAndEmail user account with email: " + user.getEmail());
    }
}
