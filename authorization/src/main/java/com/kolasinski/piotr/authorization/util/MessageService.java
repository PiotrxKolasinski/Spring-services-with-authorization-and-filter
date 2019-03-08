package com.kolasinski.piotr.authorization.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {
    private final MessageSource messageSource;

    @Autowired
    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String message, String locale) {
        Locale requestLocale = new Locale(locale,locale.toUpperCase());
        return messageSource.getMessage(message, null, requestLocale);
    }

    public String getMessage(String message, Object[] args, String locale) {
        Locale requestLocale = new Locale(locale, locale.toUpperCase());
        return messageSource.getMessage(message, args, requestLocale);
    }
}