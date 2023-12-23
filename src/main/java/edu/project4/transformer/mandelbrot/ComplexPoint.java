package edu.project4.transformer.mandelbrot;

import org.jetbrains.annotations.NotNull;

public record ComplexPoint(double re, double im) {
    public @NotNull ComplexPoint plus(@NotNull ComplexPoint other) {
        return new ComplexPoint(
            this.re + other.re,
            this.im + other.im
        );
    }

    public @NotNull ComplexPoint square() {
        return new ComplexPoint(
            re * re - im * im,
            2 * re * im
        );
    }

    public double module() {
        return Math.sqrt(re * re + im * im);
    }
}
