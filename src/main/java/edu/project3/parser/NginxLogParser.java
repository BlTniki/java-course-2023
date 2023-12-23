package edu.project3.parser;

import edu.project3.model.HttpStatus;
import edu.project3.model.LogRecord;
import edu.project3.model.Method;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public final class NginxLogParser {
    @SuppressWarnings("checkstyle:LineLength")
    private final static Pattern LOG_EXTRUDER = Pattern.compile(
        "^(?<ipaddress>\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) - - \\[(?<timestamp>\\d{2}/\\w{3}/\\d{4}:\\d{2}:\\d{2}:\\d{2} ([+\\-])\\d{4})] (\"(?<method>GET|POST) (?<url>.+) (?<protocol>.+)\") (?<statuscode>\\d{3}) (?<bytessent>\\d+) ([\"](?<referer>(-)|(.+))[\"]) ([\"](?<useragent>.+)[\"])$"
    );

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

    private NginxLogParser() {
    }

    /**
     * Parse log record entity from string.
     * @param rawRecord log file string line
     * @return LogRecord or empty if parse failed
     */
    public static @NotNull Optional<LogRecord> parse(@NotNull String rawRecord) {
        final Matcher matcher = LOG_EXTRUDER.matcher(rawRecord);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        try {
            return Optional.of(new LogRecord(
                matcher.group("ipaddress"),
                ZonedDateTime.parse(matcher.group("timestamp"), FORMATTER),
                Method.valueOf(matcher.group("method")),
                matcher.group("url"),
                matcher.group("protocol"),
                HttpStatus.valueOf(Integer.parseInt(matcher.group("statuscode"))),
                Long.parseLong(matcher.group("bytessent")),
                matcher.group("referer"),
                matcher.group("useragent")
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Parse log record entities from collection of log file string lines.
     * We are assuming that log file may contain non log trash,
     * but it must contain at least 1 valid log record.
     * @param rawRecords log file string lines. Must have at least 1 valid record
     * @return list of {@link LogRecord} from string collection
     */
    public static @NotNull  List<LogRecord> parseAll(@NotNull Collection<String> rawRecords) {
        return rawRecords.stream()
            .map(NginxLogParser::parse)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    }
}
