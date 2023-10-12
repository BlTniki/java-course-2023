package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BitwiseShiftUtilsTest {

    @Test
    @DisplayName("Проверка сдвига влево")
    void rotateLeft() {
        // given
        var numbers = List.of(1, 16, 17);
        var expectedResult = List.of(1, 1, 3);

        // when
        var resultedResult = numbers.stream()
            .map((i -> BitwiseShiftUtils.rotateLeft(i, 1)))
            .toList();

        // then
        assertEquals(expectedResult, resultedResult);
    }

    @Test
    @DisplayName("Проверка сдвига вправо")
    void rotateRight() {
        // given
        var numbers = List.of(1, 16, 17);
        var expectedResult = List.of(1, 8, 24);

        // when
        var resultedResult = numbers.stream()
            .map((i -> BitwiseShiftUtils.rotateRight(i, 1)))
            .toList();

        // then
        assertEquals(expectedResult, resultedResult);
    }

    @Test
    @DisplayName("Краевые условия MAX")
    void rotate_edge_max() {
        // given
        int num = Integer.MAX_VALUE;
        int shift = Integer.MAX_VALUE;

        // when
        int leftResult = BitwiseShiftUtils.rotateLeft(num, shift);
        int rightResult = BitwiseShiftUtils.rotateRight(num, shift);

        // then
        assertEquals(num, leftResult);
        assertEquals(num, rightResult);
    }

    @Test
    @DisplayName("Краевые условия MIN")
    void rotate_edge_min() {
        // given
        int num = Integer.MIN_VALUE;
        int shift = Integer.MAX_VALUE;

        // when
        int leftResult = BitwiseShiftUtils.rotateLeft(num, shift);
        int rightResult = BitwiseShiftUtils.rotateRight(num, shift);

        // then
        assertEquals(1073741824, leftResult);
        assertEquals(1, rightResult);
    }
}
