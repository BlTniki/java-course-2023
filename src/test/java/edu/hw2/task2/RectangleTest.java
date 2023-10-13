package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class RectangleTest {

    static Arguments[] rectangles() {
        return new Arguments[]{
            Arguments.of(new Rectangle(4, 5)),
            Arguments.of(new Square(5))
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    @DisplayName("Проверка выполнения LSP")
    void rectangleArea(Rectangle rect) {
        rect = rect.setWidth(20);
        rect = rect.setHeight(10);

        assertThat(rect.area()).isEqualTo(200.0);
    }
}
