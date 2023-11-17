package edu.hw5.task1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public final class ComputerClubUtils {
    private final static Pattern SESSION_PATTERN = Pattern.compile(
        "(\\d{4}-\\d{2}-\\d{2}, \\d{2}:\\d{2}) - (\\d{4}-\\d{2}-\\d{2}, \\d{2}:\\d{2})"
    );
    private final static int START_DATE_TIME_GROUP = 1;
    private final static int END_DATE_TIME_GROUP = 2;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");

    private ComputerClubUtils() {
    }

    public static Duration parseDuration(final @NotNull String session) {
        final Matcher matcher = SESSION_PATTERN.matcher(session);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid session format: " + session);
        }

        final LocalDateTime start = LocalDateTime.parse(matcher.group(1), FORMATTER);
        final LocalDateTime end = LocalDateTime.parse(matcher.group(2), FORMATTER);


        return Duration.between(start, end);
    }

    public static Duration calculateAvgDuration(final @NotNull List<String> sessions) {
        return Duration.ofSeconds((long) sessions.stream()
            .map(ComputerClubUtils::parseDuration)
            .mapToLong(Duration::getSeconds)
            .average()
            .orElse(0.0)
        );
    }
}
