package edu.hw2.task3;

import edu.hw2.task3.exception.ConnectionException;
import edu.hw2.task3.manager.ConnectionManager;
import edu.hw2.task3.manager.DefaultConnectionManager;
import edu.hw2.task3.manager.FaultyConnectionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PopularCommandExecutorTest {

    static boolean makeExperimentsWithDefaultManager() {
        int experimentsCount = 10_000;
        double expectedFailChance = 0.025;
        double M = experimentsCount * expectedFailChance;
        int ninetyFivePercentChanceInterval = 30;

        int failsCount = 0;
        for (int attempt = 0; attempt < experimentsCount; attempt++) {
            ConnectionManager manager = new DefaultConnectionManager();
            PopularCommandExecutor executor = new PopularCommandExecutor(manager, 2);

            try {
                executor.updatePackages();
            } catch (ConnectionException e) {
                failsCount++;
            }
        }

        return Math.abs(M - failsCount) < ninetyFivePercentChanceInterval;
    }

    static boolean makeExperimentsWithFaultyManager() {
        int experimentsCount = 10_000;
        double expectedFailChance = 0.25;
        double M = experimentsCount * expectedFailChance;
        int ninetyFivePercentChanceInterval = 85;

        int failsCount = 0;
        for (int attempt = 0; attempt < experimentsCount; attempt++) {
            ConnectionManager manager = new FaultyConnectionManager();
            PopularCommandExecutor executor = new PopularCommandExecutor(manager, 2);

            try {
                executor.updatePackages();
            } catch (ConnectionException e) {
                failsCount++;
            }
        }

        return Math.abs(M - failsCount) < ninetyFivePercentChanceInterval;
    }

    @Test
    @DisplayName("Выполнение команд: stable manager")
    void tryExecute_stable() {
        // Включили отопление и мне напекло голову.
        // Поэтому я решил тестировать логику
        // через проверку совпадения мат ожидания выборки
        // на соответствие предполагаемой генеральной совокупности.
        // Соответственно мы проверяем, что мат ожидание выборки
        // не попадает в интервал 95%
        // всего в 0.05+-delta случаев.
        int assumptionRightCount = 0;
        int experimentsCount = 1000;
        double expectedSuccessChance = 0.95;
        double delta = 0.05;
        for (int i = 0; i < experimentsCount; i++) {
            if (makeExperimentsWithDefaultManager()) {
                assumptionRightCount++;
            }
        }
        double actualSuccessChance = (double)assumptionRightCount/experimentsCount;
        System.out.println("actual success rate: " + actualSuccessChance);
        boolean condition = Math.abs(expectedSuccessChance-actualSuccessChance) < delta;
        assertThat(condition)
            .withFailMessage(
                "Шанс успеха выборки отличается от ожидаемой генеральной больше чем на 5%"
            ).isTrue();
    }

    @Test
    @DisplayName("Выполнение команд: faulty manager")
    void tryExecute_faulty() {
        int assumptionRightCount = 0;
        int experimentsCount = 1000;
        double expectedSuccessChance = 0.95;
        double delta = 0.05;
        for (int i = 0; i < experimentsCount; i++) {
            if (makeExperimentsWithFaultyManager()) {
                assumptionRightCount++;
            }
        }
        double actualSuccessChance = (double)assumptionRightCount/experimentsCount;
        System.out.println("actual success rate: " + actualSuccessChance);
        boolean condition = Math.abs(expectedSuccessChance-actualSuccessChance) < delta;
        assertThat(condition)
            .withFailMessage(
                "Шанс успеха выборки отличается от ожидаемой генеральной больше чем на 5%"
            ).isTrue();
    }
}
