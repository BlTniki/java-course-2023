package edu.hw1;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VideoTimeUtilsTest {

    @Test
    @DisplayName("Проверка на валидных данных")
    void minutesToSeconds_valid() {
        // given
        List<String> videoTimes = List.of("00:00", "00:01", "05:05", "999:59");
        List<Integer> expectedSeconds = List.of(0, 1, 305, 59999);

        // when
        List<Integer> returnedSeconds = videoTimes.stream()
            .map(VideoTimeUtils::minutesToSeconds)
            .toList();

        // then
        assertEquals(returnedSeconds, expectedSeconds);
    }

    @Test
    @DisplayName("Проверка на невалидных данных")
    void minutesToSeconds_invalid() {
        // given
        List<String> videoTimes = List.of("00:000", "00:60", "5:05", "05:5", "word", "123", "");

        // check for exception
        for (String videoTime: videoTimes) {
            assertEquals(VideoTimeUtils.minutesToSeconds(videoTime), -1);
        }
    }

    @Test
    @DisplayName("Проверка на невалидных данных: null")
    void minutesToSeconds_null() {
        assertThat(VideoTimeUtils.minutesToSeconds(null)).isEqualTo(-1);
    }
}
