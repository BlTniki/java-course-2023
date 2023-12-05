package edu.hw9.task2;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FindDirectoriesTaskTest {

    @Test
    void compute() {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            List<Path> directoriesWithMoreThan2Files = pool.invoke(new FindDirectoriesTask(Path.of("./src/test/resources/files"), 3));

            assertThat(directoriesWithMoreThan2Files)
                .containsOnly(
                    Path.of("./src/test/resources/files/chain"),
                    Path.of("./src/test/resources/files/regex")
                );
        }
    }
}
