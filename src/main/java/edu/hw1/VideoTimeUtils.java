package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class VideoTimeUtils {
    private final static Logger LOGGER = LogManager.getLogger();

    private final static String TIME_REGEX = "^(0[0-9]|[0-9]+[0-9]):([0-5][0-9])$";

    private VideoTimeUtils() {
    }

    /**
     * Converts the time in the format "mm:ss" in seconds.
     * @param videoTime string in format "mm:ss"
     * @return time in seconds or -1 if validation failed
     */
    public static int minutesToSeconds(String videoTime) {
        LOGGER.trace("Converting time {}", videoTime);

        // Match regex
        Pattern pattern = Pattern.compile(TIME_REGEX);
        Matcher matcher = pattern.matcher(videoTime);

        // Validate videoTime
        if (!matcher.matches()) {
            return -1;
        }

        // extract minutes and seconds
        String minutes = matcher.group(1);
        String seconds = matcher.group(2);

        // convert to seconds
        int resultSeconds = Integer.parseInt(seconds);
        resultSeconds += Integer.parseInt(minutes) * 60;

        return resultSeconds;
    }
}
