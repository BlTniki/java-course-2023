package edu.hw2.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExprTest {

    @Test
    @DisplayName("Вычисление: good")
    void evaluate_good() {
        // given
        var two = new Expr.Constant(2);
        var four = new Expr.Constant(4);
        var negOne = new Expr.Negate(new Expr.Constant(1));
        var sumTwoFour = new Expr.Addition(two, four);
        var mult = new Expr.Multiplication(two, negOne);
        var exp = new Expr.Exponent(sumTwoFour, mult);
        var res = new Expr.Addition(exp, new Expr.Constant(1));

        var expectedResult = 1.027d;
        var accuracy = 1e-3d;

        // when
        var actualResult = res.evaluate();

        // then
        assertThat(Math.abs(expectedResult - actualResult) < accuracy).isTrue();
    }

    @Test
    @DisplayName("Вычисление: отрицательное основание")
    void evaluate_negative_base() {
        var base = new Expr.Constant(-1);
        var exponent = new Expr.Constant(2);
        assertThatThrownBy(() -> (new Expr.Exponent(base, exponent)).evaluate())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Вычисление: краевые значения")
    void evaluate_edge() {
        // given
        var num = new Expr.Constant(Double.MAX_VALUE);
        var exp = new Expr.Exponent(new Expr.Exponent(num, num), new Expr.Exponent(num, num));
        var expectedResult = Double.POSITIVE_INFINITY;

        // when
        var actualResult = exp.evaluate();

        // then
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
