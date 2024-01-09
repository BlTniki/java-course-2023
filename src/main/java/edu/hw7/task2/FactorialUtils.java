package edu.hw7.task2;

import java.math.BigInteger;
import java.util.stream.LongStream;

public final class FactorialUtils {

    public static final String ERROR_MESSAGE = "The factorial is defined only for non-negative numbers";

    private FactorialUtils() {
    }

    public static BigInteger calculateFactorialParallel(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }

        return LongStream.rangeClosed(1, n)
            .parallel()
            .mapToObj(BigInteger::valueOf)
            .reduce(BigInteger.ONE, BigInteger::multiply);
    }

    public static BigInteger calculateFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }

        return LongStream.rangeClosed(1, n)
            .mapToObj(BigInteger::valueOf)
            .reduce(BigInteger.ONE, BigInteger::multiply);
    }
}
