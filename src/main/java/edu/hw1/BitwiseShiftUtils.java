package edu.hw1;

public final class BitwiseShiftUtils {
    private final static String NON_POSITIVE_EXCEPTION = "Some of the arguments are non-positive";

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
            throw new IllegalArgumentException(NON_POSITIVE_EXCEPTION);
        }

        final var digitsArray = Integer.toBinaryString(n).toCharArray();
        var output = new StringBuilder();
        // remove redundant shifts, so it won't overflow on edge cases
        int shiftCleaned = shift % digitsArray.length;

        for (int i = 0; i < digitsArray.length; i++) {
            int index = (i + shiftCleaned) % digitsArray.length;
            output.append(digitsArray[index]);
        }

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
            throw new IllegalArgumentException(NON_POSITIVE_EXCEPTION);
        }

        final var digitsArray = Integer.toBinaryString(n).toCharArray();
        var output = new StringBuilder();
        // remove redundant shifts, so it won't overflow on edge cases
        int shiftCleaned = shift % digitsArray.length;

        for (int i = 0; i < digitsArray.length; i++) {
            output.append(digitsArray[(i + digitsArray.length - shiftCleaned) % digitsArray.length]);
        }

        return Integer.valueOf(output.toString(), 2);
    }
}
