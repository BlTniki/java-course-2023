package edu.project4.transformer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AffineTest {

    @Test
    @DisplayName("Проверим, что аффина трансформирует корректно")
    void apply() {
        // поворот на 90 градусов
        Affine affine = new Affine(0, -1, 0, 1, 0, 0);
        Point expected = new Point(0, 1);

        Point actual = affine.apply(new Point(1, 0));

        assertThat(actual)
            .isEqualTo(expected);
    }
}
