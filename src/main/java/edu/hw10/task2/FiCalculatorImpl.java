package edu.hw10.task2;

public class FiCalculatorImpl implements FibCalculator {
    @Override
    public long fib(int n) {
        long result;
        if (n <= 1) {
            result = n;
        } else {
            // Cache the result of inner calls as well
            result = fib(n - 1) + fib(n - 2);
        }

        return result;
    }
}
