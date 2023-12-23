package edu.project4.transformer.variation;

import edu.project4.transformer.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class VariationTest {

    @Test
    @DisplayName("Проверим применения Variation")
    void apply() {
        Variation variation = new Variation(point -> new Point(-point.x(), point.y()));
        Point expected = new Point(-1, 1);

        Point actual = variation.apply(new Point(1, 1));

        assertThat(actual)
            .isEqualTo(expected);
    }
}
