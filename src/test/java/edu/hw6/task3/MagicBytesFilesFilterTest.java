package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MagicBytesFilesFilterTest {

    @Test
    void check() {
        // .png	89 50 4e 47
        MagicBytesFilesFilter filter = new MagicBytesFilesFilter(new byte[] {(byte) 0x89, 0x50, 0x4e, 0x47});

        try (var ds = Files.newDirectoryStream(Path.of("./src/test/resources/files/magic"), filter)) {
            List<Path> list = StreamSupport.stream(ds.spliterator(), false).toList();
            assertThat(list)
                .containsOnly(
                    Path.of("./src/test/resources/files/magic/foo.png")
                );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
