package edu.hw1;

import org.jetbrains.annotations.NotNull;

public final class MyStringUtils {
    private MyStringUtils() {
    }

    /**
     * Fixing string that was mixed up.
     *
     * @param input string to fix
     * @return fixed string
     */
    public static String fixString(@NotNull final String input) {
        char[] charArray = input.toCharArray();

        for (int i = 0; i < charArray.length - 1; i += 2) {
            char temp = charArray[i];
            charArray[i] = charArray[i + 1];
            charArray[i + 1] = temp;
        }

        return new String(charArray);
    }
}
