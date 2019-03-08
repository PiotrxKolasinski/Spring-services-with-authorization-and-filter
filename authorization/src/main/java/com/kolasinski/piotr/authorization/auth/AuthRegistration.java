package com.kolasinski.piotr.authorization.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegistration {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
