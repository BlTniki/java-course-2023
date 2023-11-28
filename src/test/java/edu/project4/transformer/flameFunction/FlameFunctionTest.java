package edu.project4.transformer.flameFunction;

import edu.project4.transformer.Affine;
import edu.project4.transformer.Point;
import edu.project4.transformer.variation.Variation;
import edu.project4.transformer.variation.WeightedVariation;
import java.awt.Color;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FlameFunctionTest {

    @Test
    @DisplayName("Проверим применения FlameFunction")
    void apply() {
        // поворот на 90 градусов
        Affine affine = new Affine(0, -1, 0, 1, 0, 0);
        List<WeightedVariation> weightedVariations = List.of(
            new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())))
        );
        FlameFunction flameFunction = new FlameFunction(affine, weightedVariations, Color.lightGray);
        Point expected = new Point(2, 2);

        Point actual = flameFunction.apply(new Point(1, 1));

        assertThat(actual)
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверим применения FlameFunction")
    void blend() {
        Affine affine = new Affine(0, -1, 0, 1, 0, 0);
        List<WeightedVariation> weightedVariations = List.of(
            new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())))
        );
        FlameFunction flameFunction = new FlameFunction(affine, weightedVariations, Color.black);
        Color other = new Color(100,  100,  100);
        Color expected = new Color(50, 50, 50);

        Color actual = flameFunction.blend(other);

        assertThat(actual)
            .isEqualTo(expected);
    }
}
