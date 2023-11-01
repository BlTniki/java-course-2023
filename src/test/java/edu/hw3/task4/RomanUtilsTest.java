package edu.hw3.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class RomanUtilsTest {
    public static Arguments[] numbers() {
        return new Arguments[] {
            Arguments.of(1, "I"),
            Arguments.of(2, "II"),
            Arguments.of(12, "XII"),
            Arguments.of(16, "XVI"),
            Arguments.of(2888, "MMDCCCLXXXVIII")
        };
    }

    @ParameterizedTest
    @MethodSource("numbers")
    @DisplayName("Проверяем преобразование чисел")
    void convertToRoman(int n, String expected) {
        // when
        String actual = RomanUtils.convertToRoman(n);

        // then
        assertThat(actual)
            .isEqualTo(expected);
    }
}
