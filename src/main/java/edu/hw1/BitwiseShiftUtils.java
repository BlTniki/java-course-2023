package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BitwiseShiftUtils {
    private final static Logger LOGGER = LogManager.getLogger();

    private BitwiseShiftUtils() {
    }

    /**
     * Cyclic bitwise shift to the left.
     *
     * @param n     to shift. Only positive numbers allowed
     * @param shift how many shifts. to the left. Only positive numbers allowed
     * @return shifted number
     * @throws IllegalArgumentException if some of the arguments are non-positive
     */
    public static int rotateLeft(int n, int shift) {
        if (shift <= 0 && n <= 0) {
            throw new IllegalArgumentException("Some of the arguments are non-positive");
        }

        final var digitsArray = Integer.toBinaryString(n).toCharArray();
        var output = new StringBuilder();

        for (int i = 0; i < digitsArray.length; i++) {
            output.append(digitsArray[(i + shift) % digitsArray.length]);
        }

        LOGGER.trace("%s shifted to %s".formatted(Integer.toBinaryString(n), output.toString()));

        return Integer.valueOf(output.toString(), 2);
    }

    /**
     * Cyclic bitwise shift to the right.
     *
     * @param n     to shift. Only positive numbers allowed
     * @param shift how many shifts. to the right. Only positive numbers allowed
     * @return shifted number
     * @throws IllegalArgumentException if some of the arguments are non-positive
     */
    public static int rotateRight(int n, int shift) {
        if (shift <= 0 && n <= 0) {
            throw new IllegalArgumentException("Some of the arguments are non-positive");
        }

        final var digitsArray = Integer.toBinaryString(n).toCharArray();
        var output = new StringBuilder();

        for (int i = 0; i < digitsArray.length; i++) {
            output.append(digitsArray[(i + digitsArray.length - shift) % digitsArray.length]);
        }

        LOGGER.trace("%s shifted to %s".formatted(Integer.toBinaryString(n), output.toString()));

        return Integer.valueOf(output.toString(), 2);
    }
}
