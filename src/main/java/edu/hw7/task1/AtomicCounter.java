package edu.hw7.task1;

import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class AtomicCounter {
    private final int numIncrements;

    private final AtomicInteger counter;

    public AtomicCounter(@Range(from = 0, to = Integer.MAX_VALUE) int numIncrements) {
        this.numIncrements = numIncrements;
        this.counter = new AtomicInteger(0);
    }

    public int getCount() {
        return counter.get();
    }

    public @NotNull IncrementThread makeIncrementThread() {
        return new IncrementThread(this);
    }

    public final static class IncrementThread extends Thread {
        private final AtomicCounter counter;

        private IncrementThread(AtomicCounter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < counter.numIncrements; i++) {
                counter.counter.incrementAndGet();
            }
        }
    }
}
