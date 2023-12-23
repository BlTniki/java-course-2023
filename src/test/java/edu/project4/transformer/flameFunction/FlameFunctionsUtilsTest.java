package edu.project4.transformer.flameFunction;

import edu.project4.transformer.Affine;
import edu.project4.transformer.variation.Variation;
import edu.project4.transformer.variation.VariationType;
import edu.project4.transformer.variation.WeightedVariation;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FlameFunctionsUtilsTest {

    @Test
    @DisplayName("Проверим что аффина собирается с корректными параметрами")
    void buildAffine() {
        Affine affine = FlameFunctionsUtils.buildAffine();

        assertThat(affine.a()).isBetween(-1.5, 1.5);
        assertThat(affine.b()).isBetween(-1.5, 1.5);
        assertThat(affine.c()).isBetween(-1.5, 1.5);
        assertThat(affine.d()).isBetween(-1.5, 1.5);
        assertThat(affine.e()).isBetween(-1.5, 1.5);
        assertThat(affine.f()).isBetween(-1.5, 1.5);
    }

    @Test
    @DisplayName("Проверим что генерация списка Variation происходит корректно")
    void buildWeightedVariations() {
        VariationType[] variationTypes = new VariationType[] {VariationType.LINEAR, VariationType.SINUSOIDAL};

        List<WeightedVariation> weightedVariations = FlameFunctionsUtils.buildWeightedVariations(10, variationTypes);
        List<Variation> variations = weightedVariations.stream().map(WeightedVariation::vari).toList();
        List<Double> weights = weightedVariations.stream().map(WeightedVariation::weight).toList();

        assertThat(variations)
            .containsOnly(
                Stream.of(variationTypes)
                .map(variationType -> variationType.vari)
                .toList().toArray(new Variation[0])
            )
            .hasSizeLessThanOrEqualTo(10);

        assertThat(weights.stream().filter(w -> w < 0).toList())
            .hasSize(0);
        assertThat(weights.stream().reduce(Double::sum).orElse(0.0))
            .isBetween(0.99, 1.01);
    }

    @Test
    void buildFlameFunction() {
        VariationType[] variationTypes = new VariationType[] {VariationType.LINEAR, VariationType.SINUSOIDAL};

        FlameFunction flameFunction = FlameFunctionsUtils.buildFlameFunction(variationTypes);
        List<Variation> variations = flameFunction.weightedVaris.stream().map(WeightedVariation::vari).toList();
        List<Double> weights = flameFunction.weightedVaris.stream().map(WeightedVariation::weight).toList();

        assertThat(flameFunction.affine)
            .isNotNull();
        assertThat(flameFunction.color)
            .isNotNull();

        assertThat(variations)
            .containsOnly(
                Stream.of(variationTypes)
                    .map(variationType -> variationType.vari)
                    .toList().toArray(new Variation[0])
            )
            .hasSizeLessThanOrEqualTo(10);

        assertThat(weights.stream().filter(w -> w < 0).toList())
            .hasSize(0);
        assertThat(weights.stream().reduce(Double::sum).orElse(0.0))
            .isBetween(0.99, 1.01);
    }

    @Test
    void buildHandler() {
        VariationType[] variationTypes = new VariationType[] {VariationType.LINEAR, VariationType.SINUSOIDAL};

        FlameFunctionsHandler handler = FlameFunctionsUtils.buildHandler(variationTypes);

        assertThat(handler.flameFunctions)
            .isNotEmpty();
        assertThat(handler.probabilities)
            .hasSize(handler.flameFunctions.size());
    }
}
