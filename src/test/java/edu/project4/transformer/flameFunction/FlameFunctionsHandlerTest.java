package edu.project4.transformer.flameFunction;

import edu.project4.transformer.Affine;
import edu.project4.transformer.Point;
import edu.project4.transformer.variation.Variation;
import edu.project4.transformer.variation.WeightedVariation;
import java.awt.Color;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FlameFunctionsHandlerTest {
    @Test
    @DisplayName("Проверим корректное создание хендлера")
    void build_valid() {
        Affine affine = new Affine(0, -1, 0, 1, 0, 0);
        List<WeightedVariation> weightedVariations = List.of(
            new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())))
        );
        FlameFunction flameFunction = new FlameFunction(affine, weightedVariations, Color.black);

        Affine affine2 = new Affine(0, 0, 0, 0, 0, 0);
        List<WeightedVariation> weightedVariations2 = List.of(
            new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())))
        );
        FlameFunction flameFunction2 = new FlameFunction(affine2, weightedVariations2, Color.black);

        List<FlameFunction> flameFunctions = List.of(flameFunction, flameFunction2);
        List<Double> probabilities = List.of(0.5, 0.5);
        new FlameFunctionsHandler(flameFunctions, probabilities);
    }

    @Test
    @DisplayName("Проверим наличие ошибки если не совпадают размеры списков")
    void build_diff_sizes() {
        Affine affine = new Affine(0, -1, 0, 1, 0, 0);
        List<WeightedVariation> weightedVariations = List.of(
            new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())))
        );
        FlameFunction flameFunction = new FlameFunction(affine, weightedVariations, Color.black);

        Affine affine2 = new Affine(0, 0, 0, 0, 0, 0);
        List<WeightedVariation> weightedVariations2 = List.of(
            new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())))
        );
        FlameFunction flameFunction2 = new FlameFunction(affine2, weightedVariations2, Color.black);

        List<FlameFunction> flameFunctions = List.of(flameFunction, flameFunction2);
        List<Double> probabilities = List.of(0.2, 0.2, 0.2, 0.2, 0.2);
        assertThatThrownBy(() -> new FlameFunctionsHandler(flameFunctions, probabilities))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверим наличие ошибки если не сумма вероятностей не равна 1")
    void build_bad_probabilities() {
        Affine affine = new Affine(0, -1, 0, 1, 0, 0);
        List<WeightedVariation> weightedVariations = List.of(
            new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())))
        );
        FlameFunction flameFunction = new FlameFunction(affine, weightedVariations, Color.black);

        Affine affine2 = new Affine(0, 0, 0, 0, 0, 0);
        List<WeightedVariation> weightedVariations2 = List.of(
            new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())))
        );
        FlameFunction flameFunction2 = new FlameFunction(affine2, weightedVariations2, Color.black);

        List<FlameFunction> flameFunctions = List.of(flameFunction, flameFunction2);
        List<Double> probabilities = List.of(0.2, 0.2, 0.2, 0.2, 0.1);
        assertThatThrownBy(() -> new FlameFunctionsHandler(flameFunctions, probabilities))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверим выдачу случайной функции")
    void getRand() {
        Affine affine = new Affine(0, -1, 0, 1, 0, 0);
        List<WeightedVariation> weightedVariations = List.of(
            new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())))
        );
        FlameFunction flameFunction = new FlameFunction(affine, weightedVariations, Color.black);

        Affine affine2 = new Affine(0, 0, 0, 0, 0, 0);
        List<WeightedVariation> weightedVariations2 = List.of(
            new WeightedVariation(2,  new Variation(point -> new Point(-point.x(), point.y())))
        );
        FlameFunction flameFunction2 = new FlameFunction(affine2, weightedVariations2, Color.black);

        List<FlameFunction> flameFunctions = List.of(flameFunction, flameFunction2);
        List<Double> probabilities = List.of(0.5, 0.5);
        FlameFunctionsHandler handler = new FlameFunctionsHandler(flameFunctions, probabilities);

        Random mockRand = mock(Random.class);
        when(mockRand.nextDouble()).thenReturn(0.1).thenReturn(0.6);

        Point point = new Point(1, 1);

        Point expected = new Point(2, 2);
        Point expected2 = new Point(0, 0);

        Point actual = handler.getRand(mockRand).apply(point);
        Point actual2 = handler.getRand(mockRand).apply(point);

        assertThat(actual).isEqualTo(expected);
        assertThat(actual2).isEqualTo(expected2);
    }
}
