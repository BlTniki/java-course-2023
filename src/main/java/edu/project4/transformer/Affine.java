package edu.project4.transformer;

import org.jetbrains.annotations.NotNull;

/**
 * Affine transforms.
 */
public record Affine(
    double a,
    double b,
    double c,
    double d,
    double e,
    double f
 ) implements Transformer {
    @Override
    public @NotNull Point apply(@NotNull Point point) {
        double newX = a * point.x() + b * point.y() + c;
        double newY = d * point.x() + e * point.y() + f;
        return new Point(newX, newY);
    }
}
