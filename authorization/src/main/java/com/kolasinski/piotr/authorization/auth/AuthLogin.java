package com.kolasinski.piotr.authorization.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthLogin {
    private String email;
    private String password;
}
