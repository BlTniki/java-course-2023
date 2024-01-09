package edu.project4.transformer.variation;

import edu.project4.transformer.Point;
import edu.project4.transformer.Transformer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/**
 * Non-linear (usually) function that transform point
 */
public record Variation(Function<Point, Point> function) implements Transformer {

    @Override
    public @NotNull Point apply(@NotNull Point point) {
        return this.function.apply(point);
    }
}
