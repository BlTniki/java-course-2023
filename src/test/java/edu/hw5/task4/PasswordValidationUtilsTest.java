package edu.hw5.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class PasswordValidationUtilsTest {
    static Arguments[] passwords() {
        return new Arguments[] {
            Arguments.of("$", true),
            Arguments.of("321*", true),
            Arguments.of("&daw", true),
            Arguments.of("3213%dwad", true),
            Arguments.of("3$213%dwad", true),
            Arguments.of("", false),
            Arguments.of("123", false),
            Arguments.of("adawd", false),
            Arguments.of("  ", false),
            Arguments.of(null, false)
        };
    }
    @ParameterizedTest
    @MethodSource("passwords")
    @DisplayName("Проверка валидности пароля")
    void isPasswordValid(String password, boolean expected) {
        assertThat(PasswordValidationUtils.isPasswordValid(password))
            .isEqualTo(expected);
    }
}
