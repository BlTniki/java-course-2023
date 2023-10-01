package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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
        List<String> videoTimes = List.of("00:000", "00:60", "5:05", "05:5");

        // check for exception
        for (String videoTime: videoTimes) {
            assertThrows(
                IllegalArgumentException.class,
                () -> VideoTimeUtils.minutesToSeconds(videoTime)
            );
        }
    }
}
