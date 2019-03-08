package com.kolasinski.piotr.authorization.validation;

import com.kolasinski.piotr.authorization.validation.exception.PasswordValidatorException;
import org.passay.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PasswordValidator {

    public void isValid(String password) {
        org.passay.PasswordValidator validator = new org.passay.PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));

        if (!result.isValid()) {
            throw new PasswordValidatorException("Password validator", validator.getMessages(result));
        }
    }
}