package edu.project3.analytics;

import edu.project3.model.HttpStatus;
import edu.project3.model.LogRecord;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class AnalyticsUtils {
    private AnalyticsUtils() {
    }

    public static @NotNull List<Map.Entry<HttpStatus, Long>> findKMostPopularStatusCodes(
        @NotNull Collection<LogRecord> logRecords,
        @Range(from = 1, to = Integer.MAX_VALUE) int k) {
        return logRecords.stream()
            .collect(Collectors.groupingBy(LogRecord::statusCode, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(k)
            .collect(Collectors.toList());
    }

    public static @NotNull List<Map.Entry<String, Long>> findKMostPopularUrls(
        @NotNull Collection<LogRecord> logRecords,
        @Range(from = 1, to = Integer.MAX_VALUE) int k) {
        return logRecords.stream()
            .collect(Collectors.groupingBy(LogRecord::url, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(k)
            .collect(Collectors.toList());
    }

    public static double findBytesSendAvg(@NotNull Collection<LogRecord> logRecords) {
        return logRecords.stream()
            .mapToDouble(LogRecord::bytesSent)
            .average()
            .orElse(0);
    }
}
