package edu.project3.parser;

import edu.project3.model.LogRecord;
import edu.project3.model.Method;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class NginxLogParserTest {

    @Test
    @DisplayName("Проверим парсинг валидной строки")
    void parse_valid() {
        String toParse = "0.0.0.0 - - [15/Dec/1999:00:00:00 +0300] \"GET /amazing/path HTTP/1.1\" 200 0 \"referer\" \"useragent\"";
        LogRecord expected = new LogRecord(
            "0.0.0.0",
            ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(945205200000L),
                ZoneOffset.of("+03")
            ),
            Method.GET,
            "/amazing/path",
            "HTTP/1.1",
            200,
            0L,
            "referer",
            "useragent"
        );

        var actual = NginxLogParser.parse(toParse);

        assertThat(actual)
            .isPresent()
            .hasValue(expected);
    }

    @Test
    @DisplayName("Проверим парсинг невалидной строки")
    void parse_invalid() {
        String toParse = "0.0.ыыы.0 - - [время пострелять па па па пау] \"GET /amazing/path HTTP/1.1\" 200 0 \"referer\" \"useragent\"";

        var actual = NginxLogParser.parse(toParse);

        assertThat(actual)
            .isEmpty();
    }

    @Test
    @DisplayName("Проверим парсинг валидных строк")
    void parseAll_valid() {
        List<String> rawStrings = List.of("""
            93.180.71.3 - - [17/May/2015:08:05:32 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            93.180.71.3 - - [17/May/2015:08:05:23 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            80.91.33.133 - - [17/May/2015:08:05:24 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\""""
            .split("\n")
        );

        var actual = NginxLogParser.parseAll(rawStrings);

        assertThat(actual)
            .hasSize(3);
    }

    @Test
    @DisplayName("Проверим парсинг невалидных строк")
    void parseAll_invalid() {
        List<String> rawStrings = List.of("""
            93.sss.71.3 - - [17/Mayadwadawdwa2 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            93.180.71.3dwadadwwa - - [17dwad05:23 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            80.91.33.dwadwa133 [17/May/2015:08:05:24 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\""""
            .split("\n")
        );

        var actual = NginxLogParser.parseAll(rawStrings);

        assertThat(actual)
            .hasSize(0);
    }
}
