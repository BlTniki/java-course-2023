package edu.hw1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoTimeUtils {

    /**
     * Converts the time in the format "mm:ss" in seconds.
     * @param videoTime string in format "mm:ss"
     * @return time in seconds
     * @throws IllegalArgumentException if time format validation failed
     */
    public static int minutesToSeconds(String videoTime) {
        // Match regex
        String TIME_REGEX = "^(0[0-9]|[0-9]+[0-9]):([0-5][0-9])$";
        Pattern pattern = Pattern.compile(TIME_REGEX);
        Matcher matcher = pattern.matcher(videoTime);

        // Validate videoTime
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                "[%s] is the incorrect time format".formatted(videoTime)
            );
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
