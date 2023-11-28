package edu.project4.transformer.variation;

import edu.project4.transformer.Point;
import java.util.function.Function;

public enum VariationType {
    LINEAR(point -> new Point(point.x(), point.y())),
    SINUSOIDAL(point -> new Point(Math.sin(point.x()), Math.sin(point.y()))),
    SPHERICAL(point -> {
        final double r = point.x() * point.x() + point.y() * point.y();
        return new Point(point.x() / r, point.y() / r);
    });

    public final Variation vari;

    VariationType(Function<Point, Point> function) {
        this.vari = new Variation(function);
    }
}
