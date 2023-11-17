package edu.hw5.task3.dateParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Parsing date from string like "yy-MM-dd" and "yyyy-MM-dd".
 */
public class DateDashParser extends DateParser {
    private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yy-MM-dd");

    @Override
    public @NotNull Optional<LocalDate> parse(@NotNull String dateToParse) {
        try {
            return Optional.of(
                FORMATTER.parse(dateToParse)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            );
        } catch (ParseException e) {
            if (hasNext) {
                return nextParser.parse(dateToParse);
            }

            return Optional.empty();
        }
    }
}
