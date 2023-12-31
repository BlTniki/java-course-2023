package edu.hw1;

public final class CountNumberUtils {
    private final static int NUMBER_BASE = 10;

    private CountNumberUtils() {
    }

    /**
     * Counts number of digits in given number.
     *
     * @param number in which to count the digits
     * @return number of digits
     */
    public static int countDigits(final int number) {
        int tmpNumber = number;
        int count = 1;

        while (tmpNumber >= NUMBER_BASE || tmpNumber <= (-1) * NUMBER_BASE) {
            tmpNumber /= NUMBER_BASE;
            count++;
        }

        return count;
    }
}
