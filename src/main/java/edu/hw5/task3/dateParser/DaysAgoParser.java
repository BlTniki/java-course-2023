package edu.hw5.task3.dateParser;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Parsing date from string like "d days ago".
 */
public class DaysAgoParser extends DateParser {
    private final static Pattern DATE_REGEX = Pattern.compile("^(\\d+) days? ago$");

    @Override
    public @NotNull Optional<LocalDate> parse(@NotNull String dateToParse) {
        final Matcher matcher = DATE_REGEX.matcher(dateToParse);

        if (matcher.matches()) {
            int daysCount = Integer.parseInt(matcher.group(1));
            return Optional.of(LocalDate.now().minusDays(daysCount));
        }

        return Optional.empty();
    }
}
