package edu.hw5.task8;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class BinaryAlphabet2UtilsTest {
    static Arguments[] odd_words() {
        return new Arguments[] {
            Arguments.of("0", true),
            Arguments.of("1", true),
            Arguments.of("010", true),
            Arguments.of("101", true),
            Arguments.of("00", false),
            Arguments.of("01", false),
            Arguments.of("11", false)
        };
    }

    @ParameterizedTest
    @MethodSource("odd_words")
    void isWordHasOddLength(String word, boolean expected) {
        assertThat(BinaryAlphabet2Utils.isWordHasOddLength(word))
            .isEqualTo(expected);
    }

    static Arguments[] zeroOrOne_words() {
        return new Arguments[] {
            Arguments.of("0", true),
            Arguments.of("11", true),
            Arguments.of("010", true),
            Arguments.of("1001", true),
            Arguments.of("01", false),
            Arguments.of("1", false),
            Arguments.of("0101", false),
            Arguments.of("10011", false)
        };
    }

    @ParameterizedTest
    @MethodSource("zeroOrOne_words")
    void zeroOrOne(String word, boolean expected) {
        assertThat(BinaryAlphabet2Utils.zeroOrOne(word))
            .isEqualTo(expected);
    }

    static Arguments[] notContain11or111_words() {
        return new Arguments[] {
            Arguments.of("0", true),
            Arguments.of("100101", true),
            Arguments.of("1001010", true),
            Arguments.of("1", true),
            Arguments.of("1111", true),
            Arguments.of("11", false),
            Arguments.of("111", false)
        };
    }

    @ParameterizedTest
    @MethodSource("notContain11or111_words")
    void notContain11or111(String word, boolean expected) {
        assertThat(BinaryAlphabet2Utils.notContain11or111(word))
            .isEqualTo(expected);
    }

    static Arguments[] has1AtOddIndexes_words() {
        return new Arguments[] {
            Arguments.of("1", true),
            Arguments.of("11", true),
            Arguments.of("10", true),
            Arguments.of("111", true),
            Arguments.of("101", true),
            Arguments.of("1011", true),
            Arguments.of("1001", false),
            Arguments.of("0", false),
            Arguments.of("0101", false),
            Arguments.of("110", false),
            Arguments.of("10110", false),
            Arguments.of("1101", false)
        };
    }

    @ParameterizedTest
    @MethodSource("has1AtOddIndexes_words")
    void has1AtOddIndexes(String word, boolean expected) {
        assertThat(BinaryAlphabet2Utils.has1AtOddIndexes(word))
            .isEqualTo(expected);
    }
}
