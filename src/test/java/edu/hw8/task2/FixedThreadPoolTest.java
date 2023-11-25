package edu.hw8.task2;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
    @DisplayName("Вычислим 50-е число Фибоначчи")
    void fibonacciTest() {
        for (Integer poolSize : List.of(1, 2, 6, 12, 24, 50)) {
            int fibonacciNumber = 1000;
            ConcurrentMap<Integer, BigInteger> cache = new ConcurrentHashMap<>();

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
                System.out.println("За время: %,.8f мс \n".formatted(endTimeMS));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    @DisplayName("Вычислим 50-е число Фибоначчи")
    void fibonacciTest2() throws Exception {
        // Создаем пул потоков с пятью потоками
        ThreadPool threadPool = FixedThreadPool.create(5);
        threadPool.start();

        // Задаем номер числа Фибоначчи, которое хотим вычислить
        int n = 5;

        // Вычисляем число Фибоначчи параллельно с использованием пула потоков
        long result = fibonacci(n, threadPool);

        // Выводим результат
        System.out.println("Fibonacci(" + n + ") = " + result);

        // Закрываем пул потоков
        threadPool.close();
    }

    private static long fibonacci(int n, ThreadPool threadPool) {
        if (n <= 1) {
            return n;
        } else {
            // Используем пул потоков для параллельного вычисления чисел Фибоначчи
            Future<Long> left = submitTask(() -> fibonacci(n - 1, threadPool), threadPool);
            Future<Long> right = submitTask(() -> fibonacci(n - 2, threadPool), threadPool);

            try {
                // Получаем результаты вычислений
                return left.get() + right.get();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Error during Fibonacci calculation", e);
            }
        }
    }

    private static Future<Long> submitTask(Callable<Long> task, ThreadPool threadPool) {
        CompletableFuture<Long> future = new CompletableFuture<>();
        threadPool.execute(() -> {
            try {
                future.complete(task.call());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }
}
