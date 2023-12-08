package edu.hw10.task2;

public class Main {
    public static void main(String[] args) {
        FibCalculator target = new FiCalculatorImpl();

        FibCalculator fi = CacheProxy.create(target, FibCalculator.class);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 45; i++) {
            fi.fib(i);
        }
        System.out.println(fi.fib(45) + " " + (System.currentTimeMillis() - start));
    }
}
