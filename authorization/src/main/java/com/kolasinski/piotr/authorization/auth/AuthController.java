package com.kolasinski.piotr.authorization.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.kolasinski.piotr.authorization.user.User;
import com.kolasinski.piotr.authorization.user.UserViews;
import com.kolasinski.piotr.authorization.util.Pair;
import com.kolasinski.piotr.authorization.util.UserHeaderExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserHeaderExtractor userHeaderExtractor;

    @Autowired
    public AuthController(AuthService authService, UserHeaderExtractor userHeaderExtractor) {
        this.authService = authService;
        this.userHeaderExtractor = userHeaderExtractor;
    }

    @PostMapping("/login")
    @JsonView(UserViews.Public.class)
    public ResponseEntity login(@RequestBody AuthLogin authLogin) {
        Pair<User, HttpHeaders> loginResponse = authService.login(authLogin);
        return new ResponseEntity<>(loginResponse.getVar1(), loginResponse.getVar2(), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity registerAccount(@RequestBody @Valid AuthRegistration authRegistration) {
        authService.register(authRegistration);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/confirming-email/{token}")
    public ResponseEntity confirmEmail(@PathVariable String token) {
        authService.confirmEmail(token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        long userId = userHeaderExtractor.extractUserId(request);
        authService.logout(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteAccount(HttpServletRequest request) {
        long userId = userHeaderExtractor.extractUserId(request);
        authService.delete(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}