package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FilesUtilsTest {

    @Test
    void testCloning() throws IOException {
        Path file = Path.of("./test.txt");
        Path file1clone = Path.of("./test — копия.txt");
        Path file2clone = Path.of("./test — копия (2).txt");
        Path file3clone = Path.of("./test — копия (3).txt");

        Files.createFile(file);

        assertThat(Files.exists(file1clone))
            .isFalse();
        assertThat(Files.exists(file2clone))
            .isFalse();
        assertThat(Files.exists(file3clone))
            .isFalse();

        FilesUtils.cloneFile(file);
        FilesUtils.cloneFile(file);
        FilesUtils.cloneFile(file1clone);

        assertThat(Files.exists(file1clone))
            .isTrue();
        assertThat(Files.exists(file2clone))
            .isTrue();
        assertThat(Files.exists(file3clone))
            .isTrue();

        Files.deleteIfExists(file);
        Files.deleteIfExists(file1clone);
        Files.deleteIfExists(file2clone);
        Files.deleteIfExists(file3clone);
    }
}
