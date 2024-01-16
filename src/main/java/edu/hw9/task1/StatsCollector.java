package edu.hw9.task1;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public final class StatsCollector {
    private final ConcurrentMap<String, Double[]> metrics;

    public StatsCollector(ConcurrentMap<String, Double[]> metrics) {
        this.metrics = metrics;

    }

    public void push(@NotNull String metricName, Double... indicators) {
        if (indicators == null || indicators.length == 0) {
            return;
        }

        metrics.compute(
            metricName,
            (k, oldIndicators) -> {
                if (oldIndicators == null) {
                    return indicators;
                }

                final Double[] concatIndicators = new Double[oldIndicators.length + indicators.length];
                System.arraycopy(oldIndicators, 0, concatIndicators, 0, oldIndicators.length);
                System.arraycopy(indicators, 0, concatIndicators, oldIndicators.length, indicators.length);

                return concatIndicators;
            }
        );
    }

    public @NotNull Map<String, Metric> getAll() {
        return metrics.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> new Metric(entry.getKey(), List.of(entry.getValue())))
            );
    }
}
