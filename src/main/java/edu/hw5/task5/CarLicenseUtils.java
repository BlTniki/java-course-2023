package edu.hw5.task5;

import java.util.regex.Pattern;

public final class CarLicenseUtils {
    /**
     * This regex should cover all russian car number variations. <a href="https://regex101.com/r/q1lACR/2">examples</a>.
     */
    @SuppressWarnings("checkstyle:LineLength")
    private final static Pattern CAR_LICENSE_REGEX = Pattern.compile(
        "^(([АВЕКМНОРСТУХ]\\d{3}(?<!000)[АВЕКМНОРСТУХ]{1,2})(\\d{2,3})|(\\d{4}(?<!0000)[АВЕКМНОРСТУХ]{2})(\\d{2})|(\\d{3}(?<!000)(C?D|[ТНМВКЕ])\\d{3}(?<!000))(\\d{2}(?<!00))|([ТСК][АВЕКМНОРСТУХ]{2}\\d{3}(?<!000))(\\d{2})|([АВЕКМНОРСТУХ]{2}\\d{3}(?<!000)[АВЕКМНОРСТУХ])(\\d{2})|([АВЕКМНОРСТУХ]\\d{4}(?<!0000))(\\d{2})|(\\d{3}(?<!000)[АВЕКМНОРСТУХ])(\\d{2})|(\\d{4}(?<!0000)[АВЕКМНОРСТУХ])(\\d{2})|([АВЕКМНОРСТУХ]{2}\\d{4}(?<!0000))(\\d{2})|([АВЕКМНОРСТУХ]{2}\\d{3}(?<!000))(\\d{2,3})|(^Т[АВЕКМНОРСТУХ]{2}\\d{3}(?<!000)\\d{2,3}))"
    );

    private CarLicenseUtils() {
    }

    public static boolean isLicenseValid(String license) {
        return license != null && CAR_LICENSE_REGEX.matcher(license).matches();
    }
}
