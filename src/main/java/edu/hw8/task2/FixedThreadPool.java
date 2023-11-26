package edu.hw8.task2;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class FixedThreadPool implements ThreadPool {
    private final Worker[] pool;
    private final BlockingDeque<Runnable> deque;
    private volatile boolean isAlive;

    private FixedThreadPool(int poolSize) {
        this.pool = new Worker[poolSize];
        for (int i = 0; i < poolSize; i++) {
            this.pool[i] = new Worker();
        }
        this.deque = new LinkedBlockingDeque<>();
    }

    public static FixedThreadPool create(@Range(from = 1, to = Integer.MAX_VALUE) int poolSize) {
        return new FixedThreadPool(poolSize);
    }

    @Override
    public void start() {
        isAlive = true;
        for (var t: pool) {
            t.start();
        }
    }

    @Override
    public void execute(@NotNull Runnable runnable) {
        try {
            deque.put(runnable);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        isAlive = false;

        for (var t : pool) {
            t.interrupt();
        }
    }

    @Override
    public int queueSize() {
        return deque.size();
    }

    private final class Worker extends Thread {
        @Override
        public void run() {
            try {
                while (isAlive) {
                    final Runnable runnable = deque.take();
                    runnable.run();
                }
            } catch (InterruptedException e) {
                // pass
            }
        }
    }
}
