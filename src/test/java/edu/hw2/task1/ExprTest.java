package edu.hw2.task1;

import org.junit.jupiter.api.Test;

class ExprTest {

    @Test
    void lolkek() {
        var two = new Expr.Constant(2);
        var four = new Expr.Constant(4);
        var negOne = new Expr.Negate(new Expr.Constant(1));
        var sumTwoFour = new Expr.Addition(two, four);
        var mult = new Expr.Multiplication(two, negOne);
        var exp = new Expr.Exponent(sumTwoFour, mult);
        var res = new Expr.Addition(exp, new Expr.Constant(1));

        System.out.println(res + " = " + res.evaluate());
    }
}
