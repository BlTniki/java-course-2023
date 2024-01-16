package edu.hw8.task2;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedThreadPoolTest {
    private static BigInteger fibonacci(int n, Map<Integer, BigInteger> cache) {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        if (n < 2) {
            cache.put(n, BigInteger.valueOf(n));
        } else {
            cache.put(n, fibonacci(n - 1, cache).add(fibonacci(n - 2, cache)));
        }

        return cache.get(n);
    }

    @Test
    @DisplayName("Вычислим n-е число Фибоначчи")
    void fibonacciTest() {
        for (Integer poolSize : List.of(1, 2, 6, 12, 24, 50)) {
            int fibonacciNumber = 1000;
            Map<Integer, BigInteger> cache = new ConcurrentHashMap<>();

            try (ThreadPool pool = FixedThreadPool.create(poolSize)) {
                for (int i = fibonacciNumber; i > 0; i -= fibonacciNumber / poolSize) {
                    final int startNum = i;
                    pool.execute(() -> fibonacci(startNum, cache));
                }

                long startTime = System.nanoTime();
                pool.start();
                while (!cache.containsKey(fibonacciNumber)) {
                    // pass
                }
                double endTimeMS = ((double) System.nanoTime() - startTime) / 1_000_000;

                System.out.printf("%d потоков %n", poolSize);
                System.out.printf("%d-oe число Фибоначчи: %s%n", fibonacciNumber, cache.get(fibonacciNumber));
                System.out.printf("За время: %,.8f мс \n%n", endTimeMS);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
