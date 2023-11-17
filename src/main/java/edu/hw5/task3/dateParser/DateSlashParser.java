package edu.hw5.task3.dateParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Parsing date from string like "dd/MM/yy" and "dd/MM/yyyy".
 */
public class DateSlashParser extends DateParser {
    private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yy");

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
