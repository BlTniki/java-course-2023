package edu.hw5.task3.dateParser;

import java.time.LocalDate;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Parsing strings like "today", "tomorrow" and "yesterday".
 */
public class RelativeDateParser extends DateParser {
    @SuppressWarnings("checkstyle:ReturnCount")
    @Override
    public @NotNull Optional<LocalDate> parse(@NotNull String dateToParse) {
        switch (dateToParse) {
            case "today" -> {
                return Optional.of(LocalDate.now());
            }
            case "tomorrow" -> {
                return Optional.of(LocalDate.now().plusDays(1));
            }
            case "yesterday" -> {
                return Optional.of(LocalDate.now().minusDays(1));
            }
            default -> {
                if (hasNext) {
                    return nextParser.parse(dateToParse);
                }
                return Optional.empty();
            }
        }
    }
}
