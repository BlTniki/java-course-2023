package edu.hw3.task5;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class ContactsUtilsTest {
    static Arguments[] valid_fcs() {
        return new Arguments[] {
            Arguments.of(List.of("abc", "aab", "aaa", "acb"), List.of("aaa", "aab", "abc", "acb")),
            Arguments.of(List.of("John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"), List.of("Thomas Aquinas", "Rene Descartes", "David Hume", "John Locke")),
            Arguments.of(List.of("Paul Erdos", "Leonhard Euler", "Carl Gauss"), List.of("Paul Erdos", "Leonhard Euler", "Carl Gauss")),
            Arguments.of(List.of(), List.of()),
            Arguments.of(null, List.of())
        };
    }

    @ParameterizedTest
    @MethodSource("valid_fcs")
    @DisplayName("Тест на валидных данных")
    void parseContacts(List<String> fcs, List<String> expectedASC) {
        // when
        var actualASC = ContactsUtils.parseContacts(fcs, Order.ASC);
        var actualDESC = ContactsUtils.parseContacts(fcs, Order.DESC);

        // then
        assertThat(actualASC)
            .containsExactlyElementsOf(expectedASC);
        assertThat(actualDESC)
            .containsExactlyElementsOf(expectedASC.reversed());
    }
}
