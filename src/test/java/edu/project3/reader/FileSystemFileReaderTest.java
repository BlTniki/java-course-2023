package edu.project3.reader;

import edu.project3.exception.BadFilePathException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileSystemFileReaderTest {
    static Arguments[] valid_paths() {
        return new Arguments[] {
            Arguments.of(
                "src/test/resources/filesForProject3/foo1",
                List.of("1 foo1", "2 foo1")
            ),
            Arguments.of(
                "/home/runner/work/java-course-2023/java-course-2023/src/test/resources/filesForProject3/foo*",
                List.of("1 foo1", "2 foo1", "1 foo2", "2 foo2")
            ),
            Arguments.of(
                "src/test/resources/filesForProject3/**/foo*",
                List.of("1 foo1", "2 foo1", "1 foo2", "2 foo2", "1 foo3", "2 foo3")
            )
        };
    }

    static Arguments[] invalid_paths() {
        return new Arguments[] {
            Arguments.of(
                "awdawdw\ndawd"
            ),
            Arguments.of(
                "src/test/resources/f[]ilesForProject3/foo*"
            )
        };
    }

    @ParameterizedTest
    @MethodSource("valid_paths")
    @DisplayName("Проверим получение строк при корректном пути")
    void getStringsFrom_valid(String path, List<String> expextedList) throws IOException, BadFilePathException {
//        assertThat(Path.of(".").toAbsolutePath().toString()).isEqualTo("lol");
        Collection<String> actualList = new FileSystemFileReader().getStringsFrom(path);
        assertThat(actualList)
            .containsExactlyInAnyOrderElementsOf(expextedList);
    }

    @ParameterizedTest
    @MethodSource("invalid_paths")
    @DisplayName("Проверим получение строк при некорректном пути")
    void getStringsFrom_invalid(String path) {
        assertThatThrownBy(() -> new FileSystemFileReader().getStringsFrom(path))
            .isInstanceOf(BadFilePathException.class);
    }
}
