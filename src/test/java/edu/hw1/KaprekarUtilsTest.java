package edu.hw1;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("Сортировка 5 цифирного числа должна кинуть ошибку")
    void sortDigitsInNumber_badDigitsCount() {
        int fiveDigitsNumber = 12345;

        assertThatThrownBy(() -> KaprekarUtils.sortDigitsInNumber(fiveDigitsNumber, false))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Сортировка отрицательного числа должна кинуть ошибку")
    void sortDigitsInNumber_negative() {
        int tenDigitsNumber = -1000;

        assertThatThrownBy(() -> KaprekarUtils.sortDigitsInNumber(tenDigitsNumber, false))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверка применения функции Капрекара")
    void kaprekarFunc() {
        // given
        List<Integer> numbersToApply = List.of(1112, 3524);
        List<Integer> expectedNumbers = List.of(9990, 3087);

        // when
        List<Integer> returnedNumbers = numbersToApply.stream()
            .map(KaprekarUtils::kaprekarFunc)
            .toList();

        // then
        assertEquals(expectedNumbers, returnedNumbers);
    }

    @Test
    @DisplayName("применение функции Капрекара к 5 цифирному числу должно кинуть ошибку")
    void kaprekarFunc_badDigitsCount() {
        int fiveDigitsNumber = 12345;

        assertThatThrownBy(() -> KaprekarUtils.kaprekarFunc(fiveDigitsNumber))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("применение функции Капрекара к отрицательному числу должно кинуть ошибку")
    void kaprekarFunc_negative() {
        int negativeNumber = -1000;

        assertThatThrownBy(() -> KaprekarUtils.kaprekarFunc(negativeNumber))
            .isInstanceOf(IllegalArgumentException.class);
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

    @Test
    @DisplayName("Подсчёт итераций 5 цифирного числа должно кинуть ошибку")
    void countK_badDigitsCount() {
        int fiveDigitsNumber = 12345;

        assertThatThrownBy(() -> KaprekarUtils.countK(fiveDigitsNumber))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Подсчёт итераций отрицательного числа должно кинуть ошибку")
    void countK_negative() {
        int negativeNumber = -1000;

        assertThatThrownBy(() -> KaprekarUtils.countK(negativeNumber))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
