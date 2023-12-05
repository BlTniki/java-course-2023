package edu.hw9.task2;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FindFilesTaskTest {

    @Test
    void compute() {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            Predicate<Path> sizeAndExtensionPredicate = path -> path.toFile().length() > 19 && path.toFile().getName().endsWith("e");
            List<Path> filesMatchingPredicate = pool.invoke(new FindFilesTask(Path.of("./src/test/resources/files").toAbsolutePath(), sizeAndExtensionPredicate));

            assertThat(filesMatchingPredicate)
                .containsOnly(
                    Path.of(".\\src\\test\\resources\\files\\chain\\writable").toAbsolutePath(),
                    Path.of(".\\src\\test\\resources\\files\\size\\20bytesfile").toAbsolutePath()
            );
        }
    }
}
