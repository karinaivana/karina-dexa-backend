package com.karinaDexaTest.employeespring.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailUtils {
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";

    public static boolean isValidEmail(String email) {
        if (email == null) return false;

        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
}
