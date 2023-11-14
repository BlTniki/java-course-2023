package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class DateParserBuilderTest {
    static Arguments[] valid_dates() {
        return new Arguments[] {
            Arguments.of("2020-10-10", LocalDate.of(2020, 10, 10)),
            Arguments.of("20-10-10", LocalDate.of(2020, 10, 10)),
            Arguments.of("1/3/1976", LocalDate.of(1976, 3, 1)),
            Arguments.of("1/3/20", LocalDate.of(2020, 3, 1)),
            Arguments.of("tomorrow", LocalDate.now().plusDays(1)),
            Arguments.of("today", LocalDate.now().plusDays(0)),
            Arguments.of("yesterday", LocalDate.now().minusDays(1)),
            Arguments.of("1 day ago", LocalDate.now().minusDays(1)),
            Arguments.of("2234 days ago", LocalDate.now().minusDays(2234))
        };
    }

    static Arguments[] invalid_dates() {
        return new Arguments[] {
            Arguments.of(""),
            Arguments.of("tofewfmorrow"),
            Arguments.of("-1 day ago")
        };
    }

    @ParameterizedTest
    @MethodSource("valid_dates")
    void parse_valid(String dateToParse, LocalDate expected) {
        Optional<LocalDate> actual = DateParserBuilder.buildAll().parse(dateToParse);

        assertThat(actual)
            .hasValue(expected);
    }

    @ParameterizedTest
    @MethodSource("invalid_dates")
    void parse_invalid(String dateToParse) {
        Optional<LocalDate> actual = DateParserBuilder.buildAll().parse(dateToParse);

        assertThat(actual)
            .isNotPresent();
    }
}
