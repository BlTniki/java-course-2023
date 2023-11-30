package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RegexFilesFilterTest {

    @Test
    void check() {
        RegexFilesFilter filter = new RegexFilesFilter("^foo.*$");

        try (var ds = Files.newDirectoryStream(Path.of("./src/test/resources/files/regex"), filter)) {
            List<Path> list = StreamSupport.stream(ds.spliterator(), false).toList();
            assertThat(list)
                .containsOnly(
                    Path.of("./src/test/resources/files/regex/foo"),
                    Path.of("./src/test/resources/files/regex/foobar")
                );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
