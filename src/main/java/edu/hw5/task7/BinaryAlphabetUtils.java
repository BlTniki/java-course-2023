package edu.hw5.task7;

import java.util.regex.Pattern;

public final class BinaryAlphabetUtils {
    // ^[01]{2,}0$
    // (^0$)|(^1$)|(^0[01]*0$)|(^1[01]*1$)
    // ^[01]{1,3}$
    private final static Pattern AT_LEAST_3_LETTERS_AND_ENDS_WITH_0 = Pattern.compile(
        "^[01]{2,}0$"
    );
    private final static Pattern STARTS_AND_ENDS_WITH_SAME_LETTER = Pattern.compile(
        "(^0$)|(^1$)|(^0[01]*0$)|(^1[01]*1$)"
    );
    private final static Pattern SIZE_BETWEEN_1_AND_3 = Pattern.compile(
        "^[01]{1,3}$"
    );

    private BinaryAlphabetUtils() {
    }

    public static boolean isAtLeast3LettersAndEndsWith0(String string) {
        return string != null && AT_LEAST_3_LETTERS_AND_ENDS_WITH_0.matcher(string).matches();
    }

    public static boolean isStartsAndEndsWithSameLetter(String string) {
        return string != null && STARTS_AND_ENDS_WITH_SAME_LETTER.matcher(string).matches();
    }

    public static boolean isSizeBetween1And3(String string) {
        return string != null && SIZE_BETWEEN_1_AND_3.matcher(string).matches();
    }
}
