package edu.hw5.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {
    static Arguments[] strings() {
        return new Arguments[] {
            Arguments.of("abc", "achfdbaabgabcaabg", true),
            Arguments.of("abc", "achfdbaabgabacaabg", false),
            Arguments.of(null, null, true),
            Arguments.of(null, "null", false),
            Arguments.of("null", null, false),
            Arguments.of("", "", true),
            Arguments.of("", "dawdaw", true),
        };
    }

    @ParameterizedTest
    @MethodSource("strings")
    @DisplayName("Проверка, является ли строка подстрокой другой строки")
    void isSubsequenceOf(String sub, String string, boolean expected) {
        assertThat(StringUtils.isSubsequenceOf(sub, string))
            .isEqualTo(expected);
    }
}
