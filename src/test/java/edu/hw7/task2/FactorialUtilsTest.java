package edu.hw7.task2;

import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FactorialUtilsTest {

    @Test
    @DisplayName("Проверим эффективность вычисления факториала в параллели")
    void calculateFactorialParallel() {
        int n = 100000;

        long startNonParallel = System.nanoTime();
        BigInteger resultNonParallel = FactorialUtils.calculateFactorial(n);
        long endNonParallel = System.nanoTime() - startNonParallel;

        long startParallel = System.nanoTime();
        BigInteger resultParallel = FactorialUtils.calculateFactorialParallel(n);
        long endParallel = System.nanoTime() - startParallel;

        assertThat(resultParallel).isEqualTo(resultNonParallel);
        assertThat(endParallel).isLessThan(endNonParallel);

        System.out.printf("Обычный: %d, Параллель: %d, Разница: %d%n",
            endNonParallel, endParallel, endNonParallel - endParallel);
    }
}
