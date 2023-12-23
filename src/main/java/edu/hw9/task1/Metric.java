package edu.hw9.task1;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Metric {
    public final String metricName;
    public final List<Double> indicators;
    public final double sum;
    public final double avg;
    public final double max;
    public final double min;

    public Metric(@NotNull String metricName, List<Double> indicators) {
        this.metricName = metricName;
        this.indicators = indicators;
        this.sum = indicators.stream()
            .mapToDouble(Double::doubleValue)
            .sum();
        this.avg = indicators.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0);
        this.max = indicators.stream()
            .max(Double::compareTo)
            .orElse(0.0);
        this.min = indicators.stream()
            .min(Double::compareTo)
            .orElse(0.0);
    }

    @Override
    public String toString() {
        return "Metric{"
            + "metricName=" + metricName
            + ", sum=" + sum
            + ", avg=" + avg
            + ", max=" + max
            + ", min=" + min
            + '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Metric metric)) {
            return false;
        }

        if (!metricName.equals(metric.metricName)) {
            return false;
        }
        return indicators.equals(metric.indicators);
    }

    @Override
    public int hashCode() {
        int result = metricName.hashCode();
        result = 31 * result + indicators.hashCode();
        return result;
    }
}
