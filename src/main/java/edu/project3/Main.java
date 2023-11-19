package edu.project3;

import edu.project3.analytics.AnalyticsUtils;
import edu.project3.exception.BadFilePathException;
import edu.project3.exception.CommandLineParsingException;
import edu.project3.model.HttpStatus;
import edu.project3.model.LogRecord;
import edu.project3.parser.CommandLineParser;
import edu.project3.parser.NginxLogParser;
import edu.project3.reader.FileSystemFileReader;
import edu.project3.reader.UrlFileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("StringBufferReplaceableByString")
public final class Main {
    private final static Logger LOGGER = LogManager.getLogger();
    public static final int TOP_SIZE = 3;

    private Main() {
    }

    private static @NotNull String makeTopHttpCodesReport(@NotNull List<Map.Entry<HttpStatus, Long>> entries) {
        StringBuilder report = new StringBuilder("### Топ статус кодов \nКод\tИмя\tКол-во");

        for (var entry : entries) {
            StringBuilder line = new StringBuilder("\n");
            line.append(entry.getKey().value).append("\t");
            line.append(entry.getKey().toString()).append("\t");
            line.append(entry.getValue());
            report.append(line);
        }

        return report.toString();
    }

    private static @NotNull String makeTopUrlReport(@NotNull List<Map.Entry<String, Long>> entries) {
        StringBuilder report = new StringBuilder("### Топ ресурсов \nРесурс\tКол-во");

        for (var entry : entries) {
            StringBuilder line = new StringBuilder("\n");
            line.append(entry.getKey()).append("\t");
            line.append(entry.getValue());
            report.append(line);
        }

        return report.toString();
    }

    private static @NotNull String makeGeneralReport(
        @NotNull CommandLineParser args,
        long recordsCount,
        double avgBytesSend
    ) {
        StringBuilder report = new StringBuilder("### Общая информация\nМетрика\tЗначение\n");

        report.append("Файл(-ы)\t").append(args.path).append("\n");
        report.append("Начальная дата\t").append(args.fromTime).append("\n");
        report.append("Конечная дата\t").append(args.toTime).append("\n");
        report.append("Количество запросов\t").append(recordsCount).append("\n");
        report.append("Средний размер ответа\t").append("%,.2f b".formatted(avgBytesSend));

        return report.toString();
    }

    public static void writeStringToFile(String content, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    public static void main(String[] args)
        throws CommandLineParsingException, IOException, BadFilePathException {
        CommandLineParser checkedArgs = new CommandLineParser(args);

        Collection<String> lines;
        if (checkedArgs.path.contains("://")) {
            lines = new UrlFileReader().getStringsFrom(checkedArgs.path);
        } else {
            lines = new FileSystemFileReader().getStringsFrom(checkedArgs.path);
        }

        List<LogRecord> logRecords = NginxLogParser.parseAll(lines);
        if (logRecords.isEmpty()) {
            throw new RuntimeException("Failed to parse any log record");
        }

        logRecords = logRecords.stream()
            .filter(r -> r.timestamp().isAfter(checkedArgs.fromTime))
            .filter(r -> r.timestamp().isBefore(checkedArgs.toTime))
            .toList();

        var generalReport =
            makeGeneralReport(checkedArgs, logRecords.size(), AnalyticsUtils.findBytesSendAvg(logRecords));
        var topUrls = makeTopUrlReport(AnalyticsUtils.findKMostPopularUrls(logRecords, TOP_SIZE));
        var topCodes = makeTopHttpCodesReport(AnalyticsUtils.findKMostPopularStatusCodes(logRecords, TOP_SIZE));

        LOGGER.info(generalReport);
        LOGGER.info(topUrls);
        LOGGER.info(topCodes);

        if (checkedArgs.format != null) {
            if (checkedArgs.format.equals("markdown")) {
                writeStringToFile(
                    generalReport + "\n" + topUrls + "\n" + topCodes,
                    "result.md"
                );
            } else {
                writeStringToFile(
                    generalReport + "\n" + topUrls + "\n" + topCodes,
                    "result.adoc"
                );
            }
        }
    }
}
