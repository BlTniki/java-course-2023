package edu.project3.parser;

import edu.project3.exception.CommandLineParsingException;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class CommandLineParserTest {
    static Arguments[] valid_args() {
        return new Arguments[] {
            Arguments.of(
                new String[] {"test"},
                new CommandLineParser(
                    "test",
                    ZonedDateTime.parse("1970-01-01T00:00+03:00"),
                    ZonedDateTime.parse("+13544-01-20T10:07:51+03:00"),
                    null
                )
            ),
            Arguments.of(
                new String[] {"test", "--from", "2015-12-15", "--to", "2016-12-15", "--format", "adoc"},
                new CommandLineParser(
                    "test",
                    ZonedDateTime.parse("2015-12-15T00:00+03:00"),
                    ZonedDateTime.parse("2016-12-15T00:00+03:00"),
                    "adoc"
                )
            )
        };
    }

    @ParameterizedTest
    @MethodSource("valid_args")
    @DisplayName("Проверим на валидных аргуменах")
    void parse_valid(String[] args, CommandLineParser expected) throws CommandLineParsingException {
        var actual = new CommandLineParser(args);

        assertThat(actual.path).isEqualTo(expected.path);
        assertThat(actual.fromTime.toLocalDate()).isEqualTo(expected.fromTime.toLocalDate());
        assertThat(actual.toTime.toLocalDate()).isEqualTo(expected.toTime.toLocalDate());
        assertThat(actual.format).isEqualTo(expected.format);
    }
}
