package edu.project4.histogram;

import edu.project4.fractalFrame.histogram.Histogram;
import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class HistogramTest {

    @Test
    @DisplayName("Проверим что создаётся правильная стандартная гистограмма")
    void createDefault() {
        int xRes = 10;
        int yRes = 10;
        Histogram histogram = Histogram.createDefault(xRes, yRes);
        assertThat(histogram.histoPoints)
            .hasDimensions(xRes, yRes)
            .isNotEmpty();
    }

    @RepeatedTest(value = 100)
    void increaseCounterAt() {
        int xRes = 1;
        int yRes = 1;
        Histogram histogram = Histogram.createDefault(xRes, yRes);

        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            executorService.submit(() -> histogram.increaseCounterAt(0, 0));
            executorService.submit(() -> histogram.increaseCounterAt(0, 0));
        }

        assertThat(histogram.histoPoints[0][0].counter)
            .isEqualTo(2);
    }

    @RepeatedTest(value = 100)
    void setColorAt() {
        int xRes = 1;
        int yRes = 1;
        Histogram histogram = Histogram.createDefault(xRes, yRes);
        Color expectedColor = new Color(histogram.histoPoints[0][0].color.getRGB() + 2);

        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            executorService.submit(() -> histogram.setColorAt(
                0,
                0,
                new Color(histogram.histoPoints[0][0].color.getRGB() + 1)
            ));
            executorService.submit(() -> histogram.setColorAt(
                0,
                0,
                new Color(histogram.histoPoints[0][0].color.getRGB() + 1)
            ));
        }

        assertThat(histogram.histoPoints[0][0].color)
            .isEqualTo(expectedColor);
    }
}
