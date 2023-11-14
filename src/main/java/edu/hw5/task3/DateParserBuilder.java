package edu.hw5.task3;

import edu.hw5.task3.dateParser.DateDashParser;
import edu.hw5.task3.dateParser.DateParser;
import edu.hw5.task3.dateParser.DateSlashParser;
import edu.hw5.task3.dateParser.DaysAgoParser;
import edu.hw5.task3.dateParser.RelativeDateParser;

/**
 * Build date parser's chain of responsibility
 */
@SuppressWarnings("checkstyle:FinalClass")
public class DateParserBuilder {
    private DateParserBuilder() {
    }

    public static DateParser buildAll() {
        return new DateDashParser().link(
            new DateSlashParser().link(
                new RelativeDateParser().link(
                    new DaysAgoParser()
                )
            )
        );
    }
}
