package com.kolasinski.piotr.authorization.util;

import com.kolasinski.piotr.authorization.util.exception.RequestHeaderNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserHeaderExtractor {

    public long extractUserId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getHeader("userId"));
        }  catch (Exception e) {
            throw new RequestHeaderNotFoundException("Cannot find required header");
        }
    }
}
