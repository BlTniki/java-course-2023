package edu.project4.transformer;

import org.jetbrains.annotations.NotNull;

public record Point(double x, double y) {
    public @NotNull Point add(@NotNull Point point) {
        return new Point(x + point.x(), y + point.y());
    }

    public @NotNull Point scale(double factor) {
        return new Point(x * factor, y * factor);
    }
}
