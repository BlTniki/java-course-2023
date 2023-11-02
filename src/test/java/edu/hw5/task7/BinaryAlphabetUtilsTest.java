package edu.hw5.task7;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class BinaryAlphabetUtilsTest {
    static Arguments[] atLeast3LettersAndEndsWith0() {
        return new Arguments[] {
            Arguments.of("0000", true),
            Arguments.of("1010", true),
            Arguments.of("110", true),
            Arguments.of("000", true),
            Arguments.of(null, false),
            Arguments.of("", false),
            Arguments.of("0", false),
            Arguments.of("00", false),
            Arguments.of("11", false),
            Arguments.of("1", false),
            Arguments.of("0001", false),
            Arguments.of("001", false),
            Arguments.of("111", false),
            Arguments.of("11111", false),
            Arguments.of("111101", false),
            Arguments.of("10", false)
        };
    }

    static Arguments[] startsAndEndsWithSameLetter() {
        return new Arguments[] {
            Arguments.of("000", true),
            Arguments.of("111", true),
            Arguments.of("010", true),
            Arguments.of("101", true),
            Arguments.of("00", true),
            Arguments.of("11", true),
            Arguments.of("0", true),
            Arguments.of("1", true),
            Arguments.of(null, false),
            Arguments.of("", false),
            Arguments.of("01", false),
            Arguments.of("10", false),
            Arguments.of("110", false),
            Arguments.of("011", false),
            Arguments.of("100", false),
            Arguments.of("001", false),
        };
    }

    static Arguments[] startsSizeBetween1And3() {
        return new Arguments[] {
            Arguments.of("0", true),
            Arguments.of("1", true),
            Arguments.of("00", true),
            Arguments.of("01", true),
            Arguments.of("11", true),
            Arguments.of("000", true),
            Arguments.of("010", true),
            Arguments.of("101", true),
            Arguments.of(null, false),
            Arguments.of("", false),
            Arguments.of("10101", false),
            Arguments.of("1010", false),
        };
    }

    @ParameterizedTest
    @MethodSource("atLeast3LettersAndEndsWith0")
    void isAtLeast3LettersAndEndsWith0(String string, boolean expected) {
        assertThat(BinaryAlphabetUtils.isAtLeast3LettersAndEndsWith0(string))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("startsAndEndsWithSameLetter")
    void isStartsAndEndsWithSameLetter(String string, boolean expected) {
        assertThat(BinaryAlphabetUtils.isStartsAndEndsWithSameLetter(string))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("startsSizeBetween1And3")
    void isSizeBetween1And3(String string, boolean expected) {
        assertThat(BinaryAlphabetUtils.isSizeBetween1And3(string))
            .isEqualTo(expected);
    }
}
