package edu.hw3.task2;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClusterUtilsTest {

    public static Arguments[] valid_clusters() {
        return new Arguments[] {
            Arguments.of("", List.of()),
            Arguments.of("()()()", List.of("()", "()", "()")),
            Arguments.of("((()))", List.of("((()))")),
            Arguments.of("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())")),
            Arguments.of("((())())(()(()()))", List.of("((())())", "(()(()()))"))
        };
    }

    public static Arguments[] invalid_clusters() {
        return new Arguments[] {
            Arguments.of("("),
            Arguments.of(")"),
            Arguments.of("(()))"),
            Arguments.of("((())"),
            Arguments.of("((())()")
        };
    }

    @ParameterizedTest
    @MethodSource("valid_clusters")
    @DisplayName("Проверяем на валидных")
    void clusterize_valid(String input, List<String> expected) {
        // when
        List<String> actual = ClusterUtils.clusterize(input);

        // then
        assertThat(actual)
            .containsExactlyElementsOf(expected);
    }

    @ParameterizedTest
    @MethodSource("invalid_clusters")
    @DisplayName("Проверяем на невалидных")
    void clusterize_invalid(String input) {
        assertThatThrownBy(() -> ClusterUtils.clusterize(input))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
