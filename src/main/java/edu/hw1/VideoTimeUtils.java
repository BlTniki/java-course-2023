package edu.hw1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VideoTimeUtils {
    private final static String TIME_REGEX = "^(0[0-9]|[0-9]+[0-9]):([0-5][0-9])$";
    private final static int MINUTE_IN_SECONDS = 60;

    private VideoTimeUtils() {
    }

    /**
     * Converts the time in the format "mm:ss" in seconds.
     * @param videoTime string in format "mm:ss"
     * @return time in seconds or -1 if validation failed
     */
    public static int minutesToSeconds(final String videoTime) {
        if (videoTime == null) {
            return -1;
        }

        // Match regex
        final Pattern pattern = Pattern.compile(TIME_REGEX);
        final Matcher matcher = pattern.matcher(videoTime);

        // Validate videoTime
        if (!matcher.matches()) {
            return -1;
        }

        // extract minutes and seconds
        final String minutes = matcher.group(1);
        final String seconds = matcher.group(2);

        // convert to seconds

        return Integer.parseInt(seconds) + Integer.parseInt(minutes) * MINUTE_IN_SECONDS;
    }
}
