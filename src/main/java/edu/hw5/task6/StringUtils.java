package edu.hw5.task6;

import java.util.regex.Pattern;

public final class StringUtils {
    private StringUtils() {
    }

    public static boolean isSubsequenceOf(String sub, String string) {
        if (sub == null && string == null) {
            return true;
        }
        if (sub == null || string == null) {
            return false;
        }

        Pattern pattern = Pattern.compile(sub);
        return pattern.matcher(string).find();
    }
}
