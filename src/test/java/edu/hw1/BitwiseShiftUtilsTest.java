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
}
