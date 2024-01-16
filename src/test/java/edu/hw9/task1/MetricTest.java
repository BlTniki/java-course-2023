package edu.hw9.task1;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class MetricTest {
    static Arguments[] metrics() {
        return new Arguments[] {
            Arguments.of(
                new Metric("test", List.of(1.0, 2.0, 3.0)),
                "Metric{metricName=%s, sum=%s, avg=%s, max=%s, min=%s}".formatted("test", 6.0, 2.0, 3.0, 1.0)
            ),
            Arguments.of(
                new Metric("test", List.of()),
                "Metric{metricName=%s, sum=%s, avg=%s, max=%s, min=%s}".formatted("test", 0.0, 0.0, 0.0, 0.0)
            )
        };
    }

    @ParameterizedTest
    @MethodSource("metrics")
    void testToString(Metric metric, String expected) {
        assertThat(metric.toString()).isEqualTo(expected);
    }
}
