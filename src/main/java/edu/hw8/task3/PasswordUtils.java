package edu.hw8.task3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class PasswordUtils {
    private final static int PASSWORD_RADIX = 62;

    private PasswordUtils() {
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static char getCharByItsNumber(@Range(from = 0, to = PASSWORD_RADIX - 1) int num) {
        if (num < 10) {
            // char 0-9
            return (char) (48 + num);
        }
        if (num < 36) {
            // char a-z
            return (char) (97 + num - 10);
        }
        // char A-Z
        return (char) (65 + num - 36);
    }

    /**
     * Generates password based on its decimal number.
     * Basically its converting number from 10 radix to 62 radix (012..89abc...yzABC...YZ).
     * @param num 10 radix number
     * @param minSize add zeros to the left if 62 radix number to short
     * @return 62 radix number as string
     */
    public static @NotNull String getPasswordByItsNumber(
        @Range(from = 0, to = Long.MAX_VALUE) long num,
        @Range(from = 0, to = Integer.MAX_VALUE) int minSize
    ) {
        StringBuilder builder = new StringBuilder();
        long tmp = num;
        while (tmp > 0) {
            builder.append(getCharByItsNumber((int) (tmp % PASSWORD_RADIX)));
            tmp /= PASSWORD_RADIX;
        }
        while (builder.length() < minSize) {
            builder.append(getCharByItsNumber(0));
        }
        builder.reverse();
        return builder.toString();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static @NotNull String encodeToMD5(@NotNull String input) {
        MessageDigest md5;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] byteData = md5.digest(input.getBytes());

        // Convert byte array to hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteData) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
