package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("Проверка конвертации краевого числа")
    void convertNumberToDigitsIntArray_edge() {
        // given
        int edgeNumber = Integer.MAX_VALUE;
        int[] expectedArray = {2, 1, 4, 7, 4, 8, 3, 6, 4, 7};

        // when
        int[] returnedArray = PalindromeUtils.convertNumberToDigitsIntArray(edgeNumber);

        // then
        assertThat(expectedArray).isEqualTo(returnedArray);
    }

    @Test
    @DisplayName("Проверка вычисления потомка")
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
    @DisplayName("Проверка вычисления потомка краевого массива цифр")
    void calcChild_edge() {
        // given
        int[] edgeArray = {2, 1, 4, 7, 4, 8, 3, 6, 4, 7};
        int expectedChild = 42301;

        // when
        int returnedChild = PalindromeUtils.calcChild(edgeArray);

        // then
        assertThat(expectedChild).isEqualTo(returnedChild);
    }

    @Test
    @DisplayName("Проверка определения палиндромности числа или его потомка")
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
