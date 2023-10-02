package edu.hw1;

import java.util.Objects;
import java.util.LinkedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PalindromeUtils {
    private final static Logger LOGGER = LogManager.getLogger();

    private final static int NUMBER_BASE = 10;

    /**
     * Make int array from digits of given number.
     * @param number digits to array
     * @return int array of digits
     */
    public static int[] convertNumberToDigitsIntArray(Integer number) {
        char[] charArray = number.toString().toCharArray();
        int[] intArray = new int[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            intArray[i] = charArray[i] - '0';
        }

        return intArray;
    }

    /**
     * Calculate number child with number digits sequence.
     * Note that original can have odd number of digits.
     * In this case child will be calculated like: 123 -> (1+2)3
     * @param digitsArray number digits sequence.
     * @return number child
     * @throws NullPointerException if the input array is null
     */
    public static int calcChild(int[] digitsArray) {
        Objects.requireNonNull(digitsArray);

        int child = 0;
        LinkedList<Integer> pairsSum = new LinkedList<>();

        // sum pairs
        for (int i = 1; i < digitsArray.length; i += 2) {
            int leftDigit = digitsArray[i - 1];
            int rightDigit = digitsArray[i];

            pairsSum.add(leftDigit + rightDigit);
        }
        // add last digit if array length odd
        if (digitsArray.length % 2 == 1) {
            pairsSum.add(digitsArray[digitsArray.length - 1]);
        }

        // build child
        for (int i = pairsSum.size() - 1; i >= 0 && !pairsSum.isEmpty(); i--) {
            child += pairsSum.poll() * (int) Math.pow(NUMBER_BASE, i);
        }

        return child;
    }

    /**
     * Checks whether a number or its child element is a palindrome.
     * @param num to check
     * @return true if number or its child is a palindrome, false otherwise
     */
    public static boolean isPalindromeDescendant(int num) {
        if (CountNumberUtils.countDigits(num) < 2) {
            return false;
        }

        LOGGER.trace("Checking number %d".formatted(num));

        // check for palindrome
        int[] digits = convertNumberToDigitsIntArray(num);
        boolean isPalindrome = true;
        for (int i = 0; i < digits.length / 2; i++) {
            int leftDigit = digits[i];
            int rightDigit = digits[(digits.length - 1) - i];

            if (leftDigit != rightDigit) {
                isPalindrome = false;
                break;
            }
        }

        if (isPalindrome) {
            return true;
        }

        // if num is not a palindrome check the child
        return isPalindromeDescendant(calcChild(digits));
    }
}
