package edu.hw10.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CacheProxyTest {

    @Test
    void create() throws IOException {
        Path pathToCache = Path.of("src/test/resources/cache/%s_echoPersist_cache.db".formatted(TestCache.class.getName()));
        Files.deleteIfExists(pathToCache);
        TestCache target = new Echo();
        TestCache proxy = CacheProxy.create(target, TestCache.class, Path.of("src/test/resources/cache"));

        proxy.echo(1);
        proxy.echoPersist(1);

        // assert that proxy caching
        long start = System.currentTimeMillis();
        proxy.echo(1);
        proxy.echoPersist(1);
        assertThat(System.currentTimeMillis() - start)
            .isLessThan(5000);
    }
}
