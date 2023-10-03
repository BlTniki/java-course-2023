package edu.hw1;

import java.util.Arrays;


public final class KaprekarUtils {
    private final static int KAPREKAR_CONSTANT = 6174;
    private final static int ALLOWED_DIGIT_COUNT = 4;
    private final static int NUMBER_BASE = 10;

    private KaprekarUtils() {
    }

    /**
     * Sort given number digits in asc or desc order.
     * @param num to sort
     * @param desc sorting order
     * @return number with sorted digits
     */
    public static int sortDigitsInNumber(int num, boolean desc) {
        //sort
        int[] sortedDigits = PalindromeUtils.convertNumberToDigitsIntArray(num);
        Arrays.sort(sortedDigits);

        if (desc) {
            for (int i = 0; i < sortedDigits.length / 2; i++) {
                int temp = sortedDigits[i];
                sortedDigits[i] = sortedDigits[sortedDigits.length - 1 - i];
                sortedDigits[sortedDigits.length - 1 - i] = temp;
            }
        }

        // build number
        int sortedNum = 0;
        for (int i = 0; i < sortedDigits.length; i++) {
            sortedNum += sortedDigits[i] * (int) Math.pow(NUMBER_BASE, (sortedDigits.length - 1) - i );
        }

        return sortedNum;
    }

    /**
     * Applying the Kaprekar function to a number.
     * If output number less than 1000 function will add zeros to the number right.
     * @param num to apply
     * @return the Kaprekar function output
     */
    public static int kaprekarFunc(int num) {
        int newNum =  sortDigitsInNumber(num, true) - sortDigitsInNumber(num, false);

        int digit_count_diff = ALLOWED_DIGIT_COUNT - CountNumberUtils.countDigits(newNum);
        if (digit_count_diff > 0) {
            newNum *= (int) Math.pow(NUMBER_BASE, digit_count_diff);
        }

        return newNum;
    }

    /**
     * Return the number of steps that need to make Kaprekar constant from given number.
     * @param num to iterate
     * @return count of iteration
     * @throws IllegalArgumentException if number cannot become a Kaprekar constant
     */
    public static int countK(int num) {
        if (CountNumberUtils.countDigits(num) != ALLOWED_DIGIT_COUNT) {
            throw new IllegalArgumentException("the number %d cannot become a Kaprekar constant".formatted(num));
        }

        if (num == KAPREKAR_CONSTANT) {
            return 0;
        }

        int newNum = kaprekarFunc(num);

        return countK(newNum) + 1;
    }
}
