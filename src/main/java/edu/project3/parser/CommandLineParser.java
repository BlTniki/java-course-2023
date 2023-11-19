package edu.project3.parser;

import edu.project3.exception.CommandLineParsingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommandLineParser {
    public static final long MAX_EPOCH_SECOND = 365241780471L;
    public String path;
    public ZonedDateTime fromTime;
    public ZonedDateTime toTime;
    public String format;

    public CommandLineParser(String[] args) throws CommandLineParsingException {
        parseArgs(args);
    }

    private void throwError(List<String> errors) throws CommandLineParsingException {
        throw new CommandLineParsingException(
            "Bad command arguments:\n"
            + String.join("\n", errors)
        );
    }

    private void parseArgs(String[] args) throws CommandLineParsingException {
        List<String> errors = new ArrayList<>();
        if (args.length < 1 || args[0].startsWith("--")) {
            errors.add("Missing path to the file");
            throwError(errors);
        }
        path = args[0];
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--from":
                    if (i + 1 < args.length) {
                        try {
                            this.fromTime = parseZonedDateTime(args[i + 1]);
                        } catch (Exception e) {
                            errors.add("Failed to parse from date: " + args[i + 1]);
                        }
                    } else {
                        errors.add("Missing value for --from");
                    }
                    break;
                case "--to":
                    if (i + 1 < args.length) {
                        try {
                            this.fromTime = parseZonedDateTime(args[i + 1]);
                        } catch (Exception e) {
                            errors.add("Failed to parse to date: " + args[i + 1]);
                        }
                    } else {
                        errors.add("Missing value for --to");
                    }
                    break;
                case "--format":
                    if (i + 1 < args.length) {
                        parseFormat(args, i, errors);
                    } else {
                        errors.add("Missing value for --format");
                    }
                    break;
                default:
                    if (args[i].startsWith("--")) {
                        errors.add("Unknown option " + args[i]);
                    }
            }
        }

        if (!errors.isEmpty()) {
            throwError(errors);
        }
        if (fromTime == null) {
            fromTime = ZonedDateTime.of(
                LocalDateTime.ofEpochSecond(
                    0L,
                    0,
                    ZoneOffset.UTC
                ),
                ZoneId.systemDefault()
            );
        }
        if (toTime == null) {
            toTime = ZonedDateTime.of(
                LocalDateTime.ofEpochSecond(
                    MAX_EPOCH_SECOND,
                    0,
                    ZoneOffset.UTC
                ),
                ZoneId.systemDefault()
            );
        }
    }

    private void parseFormat(String[] args, int i, List<String> errors) {
        if (args[i + 1].equals("markdown") || args[i + 1].equals("adoc")) {
            this.format = args[i + 1];
        } else {
            errors.add("format %s is unsupported".formatted(args[i + 1]));
        }
    }

    private ZonedDateTime parseZonedDateTime(String value) {
        if (value.contains("+") || value.contains(" -")) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            return ZonedDateTime.parse(value, formatter);
        }
        if (value.contains(":")) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            return ZonedDateTime.of(LocalDateTime.parse(value, formatter), ZoneId.systemDefault());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        return ZonedDateTime.of(LocalDate.parse(value, formatter).atStartOfDay(), ZoneId.systemDefault());
    }

    @Override public String toString() {
        return "CommandLineParser{"
            + "path='" + path + '\''
            + ", fromTime=" + fromTime
            + ", toTime=" + toTime
            + ", format='" + format + '\''
            + '}';
    }
}
