package edu.project4.transformer.variation;

import edu.project4.transformer.Point;
import java.util.function.Function;

public enum VariationType {
    LINEAR(point -> new Point(point.x(), point.y())),
    SINUSOIDAL(point -> new Point(Math.sin(point.x()), Math.sin(point.y()))),
    SPHERICAL(point -> {
        final double r2 = point.x() * point.x() + point.y() * point.y();
        return new Point(point.x() / r2, point.y() / r2);
    }),
    DISK(point -> {
        final double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        final double theta = Math.atan(point.x() / point.y());
        return new Point(
            (theta / Math.PI) * Math.sin(Math.PI * r),
            (theta / Math.PI) * Math.cos(Math.PI * r)
        );
    });

    public final Variation vari;

    VariationType(Function<Point, Point> function) {
        this.vari = new Variation(function);
    }
}
