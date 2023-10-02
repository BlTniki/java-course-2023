package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PalindromeUtilsTest {

    @Test
    @DisplayName("Проверка конвертации чисел в последовательность цифр")
    void convertNumberToDigitsIntArray() {
        // given
        List<Integer> numbers = List.of(0, 123, 1234);
        List<int[]> expectedArrays = List.of(new int[]{0}, new int[]{1,2,3}, new int[]{1,2,3,4});

        // when
        List<int[]> returnedArrays = numbers.stream()
            .map(PalindromeUtils::convertNumberToDigitsIntArray)
            .toList();

        // then
        for (int i = 0; i < expectedArrays.size(); i++) {
            assertArrayEquals(expectedArrays.get(i), returnedArrays.get(i));
        }
    }

    @Test
    void calcChild() {
        // given
        List<int[]> digitsArrays = List.of(new int[]{1}, new int[]{1,2,3}, new int[]{1,2,3,4});
        List<Integer> expectedChildren = List.of(1, 33, 37);

        // when
        List<Integer> returnedChildren = digitsArrays.stream()
            .map(PalindromeUtils::calcChild)
            .toList();

        // then
        assertEquals(expectedChildren, returnedChildren);
    }

    @Test
    void isPalindromeDescendant() {
        // given
        List<Integer> numbers = List.of(0, 123, 11211230, 13001120, 23336014, 11);
        List<Boolean> expectedAnswers = List.of(false, true, true, true, true, true);

        // when
        List<Boolean> returnedAnswers = numbers.stream()
            .map(PalindromeUtils::isPalindromeDescendant)
            .toList();

        // then
        assertEquals(expectedAnswers, returnedAnswers);
    }
}
