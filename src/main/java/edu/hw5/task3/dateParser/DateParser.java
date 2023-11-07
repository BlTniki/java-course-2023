package edu.hw5.task3.dateParser;

import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.util.Optional;

public abstract class DateParser {
    DateParser nextParser;

    boolean hasNext;

    /**
     * Parsing date from given string. If the attempt fails,
     * the string is passed to the next parser in the hierarchy,
     * if there is one, otherwise it will return an empty Optional.
     * @param dateToParse string to parse
     * @return parsed day or empty Optional
     */
    public abstract @NotNull Optional<LocalDate> parse(@NotNull String dateToParse);

    public DateParser getNextParser() {
        return nextParser;
    }

    public DateParser link(@NotNull DateParser nextParser) {
        this.nextParser = nextParser;
        this.hasNext = true;
        return this;
    }
}
