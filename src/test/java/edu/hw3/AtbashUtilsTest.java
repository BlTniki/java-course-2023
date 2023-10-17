package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class AtbashUtilsTest {

    static Arguments[] charsArg() {
        return new Arguments[] {
            Arguments.of('b', 'y'),
            Arguments.of('B', 'Y'),
            Arguments.of('[', '['),
            Arguments.of('0', '0'),
            Arguments.of(' ', ' ')
        };
    }

    static Arguments[] stringsArg() {
        return new Arguments[] {
            Arguments.of("", ""),
            Arguments.of("aBc", "zYx"),
            Arguments.of("Hello world!", "Svool dliow!"),
            Arguments.of(
                "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler",
                "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi"
                )
        };
    }

    @ParameterizedTest
    @MethodSource("charsArg")
    @DisplayName("Проверка зеркаливания символов")
    void mirrorChar(char c, char expected) {
        // when
        char actual = AtbashUtils.mirrorChar(c);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("stringsArg")
    @DisplayName("Проверка зеркаливания символов")
    void atbash(String s, String expected) {
        // when
        String actual = AtbashUtils.atbash(s);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
