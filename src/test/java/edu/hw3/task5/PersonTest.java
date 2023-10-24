package edu.hw3.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PersonTest {
    static Arguments[] valid_fcs() {
        return new Arguments[] {
            Arguments.of("foo bar", "bar", "foo bar"),
            Arguments.of("foo", "foo", "foo"),
            Arguments.of("foo bar lol kek", "bar lol kek", "foo bar lol kek")
        };
    }

    static Arguments[] invalid_fcs() {
        return new Arguments[] {
            Arguments.of(""),
            Arguments.of(" "),
            Arguments.of("      "),
            Arguments.of("foo       "),
            Arguments.of("    foo"),
            Arguments.of("    foo dwa")
        };
    }

    @ParameterizedTest
    @MethodSource("valid_fcs")
    @DisplayName("Проверка на валидных ФИО")
    void createPerson_valid(String fc, String expectedCompareVal, String expectedToString) {
        // when
        Person actualPerson = new Person(fc);

        //then
        assertThat(actualPerson.getValToCompare())
            .isEqualTo(expectedCompareVal);
        assertThat(actualPerson.toString())
            .isEqualTo(expectedToString);
    }

    @ParameterizedTest
    @MethodSource("invalid_fcs")
    @DisplayName("Проверка на невалидных ФИО")
    void createPerson_invalid(String fc) {
        assertThatThrownBy(() -> new Person(fc))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
