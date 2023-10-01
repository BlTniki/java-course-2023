package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CountNumberUtils {
    private final static Logger LOGGER = LogManager.getLogger();

    /**
     * Counts number of digits in given number.
     * @param number in which to count the digits
     * @return number of digits
     */
    public static int countDigits(int number) {
        int tmpNumber = number;
        int count = 1;

        while (Math.abs(tmpNumber) > 9) {
            tmpNumber /= 10;
            count++;
        }

        LOGGER.trace("In %d count %d digits".formatted(number, count));

        return count;
    }
}
