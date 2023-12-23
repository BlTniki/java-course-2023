package edu.project4;

import edu.project4.fractalFrame.histogram.Histogram;
import java.awt.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PixelUtilsTest {
    private Histogram histogram;

    @BeforeEach
    void init_hist() {
        histogram = Histogram.createDefault(1, 2);
        histogram.histoPoints[0][0].counter = 10;
        histogram.histoPoints[0][0].color = new Color(100, 90, 80);
        histogram.histoPoints[0][1].counter = 20;
        histogram.histoPoints[0][1].color = new Color(200, 200, 200);
    }

    @Test
    @DisplayName("Проверим рендеринг цветов без гамма коррекции")
    void histToPixels_no_gamma_correction() {
        Color[][] colors = PixelUtils.histToPixels(histogram, false, 0);

        assertThat(colors[0][0])
            .isEqualTo(new Color(100, 90, 80));
        assertThat(colors[0][1])
            .isEqualTo(new Color(200, 200, 200));
    }

    @Test
    @DisplayName("Проверим рендеринг цветов c гамма коррекцией")
    void histToPixels_with_gamma_correction() {
        Color[][] colors = PixelUtils.histToPixels(histogram, true, 0);

        assertThat(colors[0][0])
            .isEqualTo(new Color(77, 69, 61));
        assertThat(colors[0][1])
            .isEqualTo(new Color(200, 200, 200));
    }

    @Test
    @DisplayName("Проверим рендеринг цветов c гамма коррекцией и повышением гаммы")
    void histToPixels_with_gamma_correction_and_gamma_increase() {
        Color[][] colors = PixelUtils.histToPixels(histogram, true, 1);

        assertThat(colors[0][0])
            .isEqualTo(new Color(154, 138, 123));
        assertThat(colors[0][1])
            .isEqualTo(new Color(255, 255, 255));
    }
}
