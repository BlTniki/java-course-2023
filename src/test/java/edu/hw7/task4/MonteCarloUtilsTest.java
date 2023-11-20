package edu.hw7.task4;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MonteCarloUtilsTest {
    private final static Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Создадим отчёт об эффективности вычислений числа Пи")
    void makeReport() {
        List<Integer> dotsCounts = List.of(10_000, 100_000, 1_000_000, 10_000_000, 10_000_000_00);
        StringBuilder builder = new StringBuilder("\nВычисление Пи");

        long start1kk1t = System.nanoTime();
        double pi1kk1t = MonteCarloUtils.calcPi(1_000_000, 1);
        long end1kk1t = System.nanoTime() - start1kk1t;

        builder.append("\nВычисление 1 млн. точек с 1 потоком: \n\tПи: %,.5f, \n\tЗа время: %d".formatted(
            pi1kk1t,
            end1kk1t
        ));

        long start1kk12t = System.nanoTime();
        double pi1kk12t = MonteCarloUtils.calcPi(1_000_000, 12);
        long end1kk12t = System.nanoTime() - start1kk12t;

        builder.append("\nВычисление 1 млн. точек с 12 потоками: \n\tПи: %,.5f, \n\tЗа время: %d".formatted(
            pi1kk12t,
            end1kk12t
        )).append("\n");

        for (Integer dotsCount : dotsCounts) {
            long start = System.nanoTime();
            double pi = MonteCarloUtils.calcPi(dotsCount, 12);
            long end = System.nanoTime() - start;

            builder.append("\nВычисление %d точек с 12 потоками: \n\tПи: %,.5f, \n\tЗа время: %d".formatted(
                dotsCount,
                pi,
                end
            ));
        }

        LOGGER.info(builder.toString());
    }
}
