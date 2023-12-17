package edu.hw11.task3;

public final class Fi {
    private Fi() {
    }

    public static long fib(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }
}
