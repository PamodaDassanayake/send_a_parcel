package com.api.sendaparcel.api.util;

import java.util.regex.Pattern;

public class Validation {

    private static final String STRONG_PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    // a digit must occur at least once
    // a lower case letter must occur at least once
    // an upper case letter must occur at least once
    // a special character must occur at least once
    // no whitespace allowed in the entire string
    public static boolean isStrongPassword(String password) {
        return Pattern.matches(STRONG_PASSWORD_REGEX, password);
    }

}
