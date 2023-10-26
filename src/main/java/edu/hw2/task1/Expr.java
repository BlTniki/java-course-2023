package edu.hw2.task1;

import org.jetbrains.annotations.NotNull;

public sealed interface Expr {
    double evaluate();

    record Constant(double val) implements Expr {
        @Override
        public double evaluate() {
            return val;
        }
    }

    record Negate(@NotNull Expr innerExpr) implements Expr {
        @Override
        public double evaluate() {
            return innerExpr.evaluate() * (-1);
        }
    }

    record Exponent(@NotNull Expr base, @NotNull Expr exponent) implements Expr {
        @Override
        public double evaluate() {
            final double baseVal = base.evaluate();

            if (baseVal < 0) {
                throw new IllegalArgumentException(
                    "[%f ^ %f] The exponentiation operation for a negative number is not defined"
                        .formatted(base.evaluate(), exponent.evaluate())
                );
            }

            return Math.pow(baseVal, exponent.evaluate());
        }
    }

    record Addition(@NotNull Expr a, @NotNull Expr b) implements Expr {
        @Override
        public double evaluate() {
            return a.evaluate() + b.evaluate();
        }
    }

    record Multiplication(@NotNull Expr a, @NotNull Expr b) implements Expr {
        @Override
        public double evaluate() {
            return a.evaluate() * b.evaluate();
        }
    }
}
