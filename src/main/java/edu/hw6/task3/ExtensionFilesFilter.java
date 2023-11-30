package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public class ExtensionFilesFilter extends FilesFilter {
    /**
     * Extrude file name and file extension if exist
     */
    private final static Pattern PATH_EXTRUDER = Pattern.compile("^(.*[\\\\/][^.\\n\\r\\t]+)(\\.([^.\\W]+))?$");
    private final static int EXTENSION_GROUP_INDEX = 3;

    private final String expectedExtension;

    /**
     * Creates new filter instance.
     * @param expectedExtension file extension like "txt", "csv", etc.
     */
    public ExtensionFilesFilter(@NotNull String expectedExtension) {
        this.expectedExtension = expectedExtension;
    }

    @Override
    protected boolean check(@NotNull Path entry) throws IOException {
        final Matcher matcher = PATH_EXTRUDER.matcher(entry.toAbsolutePath().toString());

        if (!matcher.matches()) {
            return false;
        }

        final String actualExtension = matcher.group(EXTENSION_GROUP_INDEX);

        if (actualExtension == null) {
            return false;
        }

        return actualExtension.equals(expectedExtension);
    }
}
