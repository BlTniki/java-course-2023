package edu.hw5.task4;

import java.util.regex.Pattern;

public final class PasswordValidationUtils {
    private final static Pattern VALID_PASSWORD_REGEX = Pattern.compile("^.*([~!@#$%^&*|]).*$");

    private PasswordValidationUtils() {
    }

    public static boolean isPasswordValid(String password) {
        return password != null && VALID_PASSWORD_REGEX.matcher(password).matches();
    }
}
