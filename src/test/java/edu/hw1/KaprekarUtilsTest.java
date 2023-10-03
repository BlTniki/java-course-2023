package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class KaprekarUtilsTest {

    @Test
    @DisplayName("Проверка сортировки по возрастанию цифр в числе")
    void sortDigitsInNumber_ASC() {
        // given
        List<Integer> numbersToSort = List.of(0, 1302, 4213);
        List<Integer> expectedNumbers = List.of(0, 123, 1234);

        // when
        List<Integer> returnedNumbers = numbersToSort.stream()
            .map(n -> KaprekarUtils.sortDigitsInNumber(n, false))
            .toList();

        // then
        assertEquals(expectedNumbers, returnedNumbers);
    }

    @Test
    @DisplayName("Проверка сортировки по убыванию цифр в числе")
    void sortDigitsInNumber_DESC() {
        // given
        List<Integer> numbersToSort = List.of(0, 1302, 4213);
        List<Integer> expectedNumbers = List.of(0, 3210, 4321);

        // when
        List<Integer> returnedNumbers = numbersToSort.stream()
            .map(n -> KaprekarUtils.sortDigitsInNumber(n, true))
            .toList();

        // then
        assertEquals(expectedNumbers, returnedNumbers);
    }

    @Test
    @DisplayName("Проверка применения функции Капрекара")
    void kaprekarFunc() {
        // given
        List<Integer> numbersToApply = List.of(0, 1112, 3524);
        List<Integer> expectedNumbers = List.of(0, 9990, 3087);

        // when
        List<Integer> returnedNumbers = numbersToApply.stream()
            .map(KaprekarUtils::kaprekarFunc)
            .toList();

        // then
        assertEquals(expectedNumbers, returnedNumbers);
    }

    @Test
    @DisplayName("Проверка подсчёта итераций")
    void countK() {
        // given
        List<Integer> numbersToApply = List.of(6174, 1112, 3524, 6621, 6554, 1234);
        List<Integer> expectedCounts = List.of(0, 5, 3, 5, 4, 3);

        // when
        List<Integer> returnedCounts = numbersToApply.stream()
            .map(KaprekarUtils::countK)
            .toList();

        // then
        assertEquals(expectedCounts, returnedCounts);
    }
}
