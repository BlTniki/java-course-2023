package edu.hw6.task5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class HackerNewsUtilsTest {

    @Test
    @DisplayName("Проверим выдачу топа новостей")
    void hackerNewsTopStories() {
        long[] ids = HackerNewsUtils.hackerNewsTopStories();

        assertThat(ids)
            .doesNotContain(0);
    }

    @Test
    @DisplayName("Проверим получения заголовка новости по айди")
    void news() {
        long[] idsToCheck = HackerNewsUtils.hackerNewsTopStories();

        List<String> titles = StreamSupport.stream(Arrays.stream(idsToCheck).spliterator(), false)
            .map(HackerNewsUtils::news)
            .toList();

        assertThat(titles)
            .hasSize(idsToCheck.length)
            .doesNotContain("");
    }
}
