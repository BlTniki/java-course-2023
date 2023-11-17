package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FilesFilterTest {

    @Test
    @DisplayName("Проверим работу фильтров в связке")
    void check() {
        FilesFilter filter = new AttrFilesFilter(List.of(
            new AttrFilesFilter.Attr("dos:readonly", true)
            )).and(new SizeFilesFilter(16, true))
            .and(new RegexFilesFilter("^foo.*$"))
            .and(new ExtensionFilesFilter("txt"))
            .and(new MagicBytesFilesFilter(new byte[] {(byte) 0x89, 0x50, 0x4e, 0x47}));

        try (var ds = Files.newDirectoryStream(Path.of("./src/test/resources/files/chain"), filter)) {
            List<Path> list = StreamSupport.stream(ds.spliterator(), false).toList();
            assertThat(list)
                .containsOnly(Path.of("./src/test/resources/files/chain/foo.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
