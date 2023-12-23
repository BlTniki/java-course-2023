package edu.hw9.task1;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class StatsCollectorTest {
    private StatsCollector statsCollector;

    @BeforeEach
    void init() {
        ConcurrentMap<String, Double[]> map = new ConcurrentHashMap<>();
        map.put("lol", new Double[] {1.0, 2.0, 3.0});
        map.put("kek", new Double[] {1.0, 2.0, 6.0});
        this.statsCollector = new StatsCollector(map);
    }

    @Test
    @DisplayName("Базовая проверка добавления новой метрики")
    void push_new() {
        statsCollector.push("foo", 0.0, 1.0, -1.0);

        Map<String, Metric> metrics = statsCollector.getAll();

        assertThat(metrics)
            .contains(Map.entry("foo", new Metric("foo", List.of(0.0, 1.0, -1.0))));
    }

    @Test
    @DisplayName("Базовая проверка обновления метрики")
    void push_update() {
        statsCollector.push("lol", 0.0, 1.0, -1.0);

        Map<String, Metric> metrics = statsCollector.getAll();

        assertThat(metrics)
            .contains(Map.entry("lol", new Metric("lol", List.of(1.0, 2.0, 3.0, 0.0, 1.0, -1.0))));
    }

    @Test
    @DisplayName("Базовая проверка получения статистики")
    void getAll() {
        assertThat(statsCollector.getAll())
            .containsExactly(
                Map.entry("kek", new Metric("kek", List.of(1.0, 2.0, 6.0))),
                Map.entry("lol", new Metric("lol", List.of(1.0, 2.0, 3.0)))
            );
    }

    @RepeatedTest(value = 100)
    @DisplayName("Проверка обновление метрики в параллели")
    void parallel() {
        final StatsCollector stats = new StatsCollector(new ConcurrentHashMap<>());
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int fi = i;
            executorService.submit(() -> {
                for (int j = fi * 20; j < (fi + 1) * 20; j++) {
                    stats.push("p", (double) j);
                }
            });
        }
        executorService.close();
        assertThat(stats.getAll())
            .hasSize(1);
        assertThat(stats.getAll().get("p").toString())
            .isEqualTo("Metric{metricName=%s, sum=%s, avg=%s, max=%s, min=%s}".formatted("p", 4950.0, 49.5, 99.0, 0.0));
    }
}
