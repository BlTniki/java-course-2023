package edu.hw3.task1;

import org.jetbrains.annotations.NotNull;

public final class AtbashUtils {
    private final static int ALPHABET_BOUNDARY_START = 'A';
    private final static int ALPHABET_BOUNDARY_END = 'Z';
    private final static int ALPHABET_BOUNDARY_SIZE = ALPHABET_BOUNDARY_END - ALPHABET_BOUNDARY_START + 1;

    private AtbashUtils() {
    }

    /**
     * Mirror latin alphabet char index and returns new char.
     * @param in char to mirror. Mirror only latin letters
     * @return char at mirrored index if char is latin letter or original char
     */
    public static char mirrorChar(final char in) {
        if (!Character.isLetter(in)) {
            return in;
        }

        final boolean isLower = Character.isLowerCase(in);
        final char cleanIn = Character.toUpperCase(in);
        final int inAlphabetIndex = cleanIn - ALPHABET_BOUNDARY_START;

        final int mirroredIndex = (ALPHABET_BOUNDARY_SIZE - 1 - inAlphabetIndex) + ALPHABET_BOUNDARY_START;

        if (isLower) {
            return Character.toLowerCase((char) mirroredIndex);
        }
        return (char) mirroredIndex;
    }

    /**
     * Encode string with the Atbash cipher. Only Latin letters will be encoded.
     * @param input to encode
     * @return encoded string
     */
    public static String atbash(@NotNull final String input) {
        return input.chars()
            .map(i -> mirrorChar((char) i))
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}
