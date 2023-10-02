package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CountNumberUtils {
    private final static Logger LOGGER = LogManager.getLogger();

    private final static int NUMBER_BASE = 10;

    private CountNumberUtils() {
    }

    /**
     * Counts number of digits in given number.
     * @param number in which to count the digits
     * @return number of digits
     */
    public static int countDigits(int number) {
        int tmpNumber = number;
        int count = 1;

        while (Math.abs(tmpNumber) > NUMBER_BASE - 1) {
            tmpNumber /= NUMBER_BASE;
            count++;
        }

        LOGGER.trace("In %d count %d digits".formatted(number, count));

        return count;
    }
}
