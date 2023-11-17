package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public class RegexFilesFilter extends FilesFilter {
    /**
     * Extrude file name and file extension if exist
     */
    private final static Pattern PATH_EXTRUDER = Pattern.compile("^(.*[^.\\n\\r\\t]+[\\\\/])(.+)$");
    private final static int FILENAME_GROUP_INDEX = 2;

    private final Pattern nameRegex;

    public RegexFilesFilter(@NotNull String nameRegex) {
        this.nameRegex = Pattern.compile(nameRegex);
    }

    @Override
    protected boolean check(@NotNull Path entry) throws IOException {
        final Matcher matcher = PATH_EXTRUDER.matcher(entry.toAbsolutePath().toString());

        if (!matcher.matches()) {
            return false;
        }

        final String fileName = matcher.group(FILENAME_GROUP_INDEX);

        return nameRegex.matcher(fileName).matches();
    }
}
