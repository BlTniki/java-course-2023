package edu.hw7.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AtomicCounterTest {
    @Test
    @DisplayName("Проверим подсчёт в нескольких тредах")
    void testCounting() {
        int expectedCount = 100_000;
        var counter = new AtomicCounter(expectedCount);
        var t1 = counter.makeIncrementThread();
        var t2 = counter.makeIncrementThread();
        var t3 = counter.makeIncrementThread();
        var t4 = counter.makeIncrementThread();

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(counter.getCount())
            .isEqualTo(4 * expectedCount);
    }
}
