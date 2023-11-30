package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ExtensionFilesFilterTest {

    @Test
    @DisplayName("Проверим что файлы фильтруются по расширению")
    void check() {
        ExtensionFilesFilter filter = new ExtensionFilesFilter("txt");

        try (var ds = Files.newDirectoryStream(Path.of("./src/test/resources/files/extension"), filter)) {
            List<Path> list = StreamSupport.stream(ds.spliterator(), false).toList();
            assertThat(list)
                .containsOnly(Path.of("./src/test/resources/files/extension/foo.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
