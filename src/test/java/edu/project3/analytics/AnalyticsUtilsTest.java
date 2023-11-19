package edu.project3.analytics;

import edu.project3.model.HttpStatus;
import edu.project3.model.LogRecord;
import edu.project3.model.Method;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AnalyticsUtilsTest {
    static List<LogRecord> logRecords;
    static {
        logRecords = new ArrayList<>();
        logRecords.add(
            new LogRecord(
                "0.0.0.0",
                ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(945205200000L),
                    ZoneOffset.of("+03")
                ),
                Method.GET,
                "/amazing/path",
                "HTTP/1.1",
                HttpStatus.valueOf(200),
                0L,
                "referer",
                "useragent"
            )
        );
        logRecords.add(
            new LogRecord(
                "0.0.0.0",
                ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(945205200000L),
                    ZoneOffset.of("+03")
                ),
                Method.GET,
                "/amazing/path2",
                "HTTP/1.1",
                HttpStatus.valueOf(200),
                0L,
                "referer",
                "useragent"
            )
        );
        logRecords.add(
            new LogRecord(
                "0.0.0.0",
                ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(945205200000L),
                    ZoneOffset.of("+03")
                ),
                Method.POST,
                "/amazing/path",
                "HTTP/1.1",
                HttpStatus.valueOf(400),
                6L,
                "referer",
                "useragent"
            )
        );
    }

    @Test
    @DisplayName("Найдём топ статус кодов")
    void findKMostPopularStatusCodes() {
        List<Map.Entry<HttpStatus, Long>> expectedCodes = List.of(
            Map.entry(HttpStatus.OK, 2L),
            Map.entry(HttpStatus.BAD_REQUEST, 1L)
        );

        var actualCodes = AnalyticsUtils.findKMostPopularStatusCodes(logRecords, 3);

        assertThat(actualCodes)
            .containsExactlyElementsOf(expectedCodes);
    }

    @Test
    @DisplayName("Найдём топ юрл")
    void findKMostPopularUrls() {
        List<Map.Entry<String, Long>> expectedUrl = List.of(
            Map.entry("/amazing/path", 2L),
            Map.entry("/amazing/path2", 1L)
        );

        var actualUrl = AnalyticsUtils.findKMostPopularUrls(logRecords, 3);

        assertThat(actualUrl)
            .containsExactlyElementsOf(expectedUrl);
    }

    @Test
    @DisplayName("Найдём средний вес ответа")
    void findBytesSendAvg() {
        var actualAvg = AnalyticsUtils.findBytesSendAvg(logRecords);

        assertThat(actualAvg)
            .isEqualTo(2d);
    }
}
