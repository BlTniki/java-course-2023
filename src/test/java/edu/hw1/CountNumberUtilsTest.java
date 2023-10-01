package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CountNumberUtilsTest {

    @Test
    @DisplayName("Проверка подсчёта чисел")
    void countDigits() {
        // given
        List<Integer> numbers = List.of(0, 5, 10, 123456, -10);
        List<Integer> expectedCounts = List.of(1, 1, 2, 6, 2);

        // when
        List<Integer> resultCount = numbers.stream()
            .map(CountNumberUtils::countDigits)
            .toList();

        // then
        assertEquals(expectedCounts, resultCount);
    }
}
