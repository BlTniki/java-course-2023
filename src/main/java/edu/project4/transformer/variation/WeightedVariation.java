package edu.project4.transformer.variation;

import edu.project4.transformer.Point;
import edu.project4.transformer.Transformer;
import org.jetbrains.annotations.NotNull;

/**
 * Transformer that applies variation to the point and then scale point by factor weight.
 */
public record WeightedVariation(double weight, Variation vari) implements Transformer {
    @Override
    public @NotNull Point apply(@NotNull Point point) {
        return vari.apply(point).scale(weight);
    }
}
