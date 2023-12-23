package edu.project4.transformer.variation;

import edu.project4.transformer.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class WeightedVariationTest {

    @Test
    @DisplayName("Проверим применения WeightedVariation")
    void apply() {
        WeightedVariation weightedVariation = new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())));
        Point expected = new Point(-2, 2);

        Point actual = weightedVariation.apply(new Point(1, 1));

        assertThat(actual)
            .isEqualTo(expected);
    }
}
