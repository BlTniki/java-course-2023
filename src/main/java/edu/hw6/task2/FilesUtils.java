package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FilesUtils {
    /**
     * Extrude file name and file extension if exist
     */
    private final static Pattern PATH_EXTRUDER = Pattern.compile("^(.*[\\\\/][^.\\n\\r\\t]+)(\\.([^.\\W]+))?$");
    private final static int FILENAME_AND_PATH_GROUP_INDEX = 1;
    private final static int EXTENSION_GROUP_INDEX = 2;

    /**
     * Extrude file name and copy counter
     */
    private final static Pattern FILECOPY_EXTRUDER = Pattern.compile("^(.+?)( — копия( \\((\\d)\\))?)?$");
    private final static int FILENAME_GROUP_INDEX = 1;
    private final static int COPY_TAG_GROUP_INDEX = 2;
    private final static int COPY_COUNT_GROUP_INDEX = 4;

    private FilesUtils() {
    }

    public static void cloneFile(Path path) {
        try {
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("File %s not exist".formatted(path.toString()));
            }

            // extrude filename
            Path originalFile = path.getFileName().toAbsolutePath();
            final Matcher pathMatcher = PATH_EXTRUDER.matcher(originalFile.toString());

            if (!pathMatcher.matches()) {
                throw new IllegalArgumentException("Unexpected error: can't parse path\n" + path);
            }

            final String fileNameWithPath = pathMatcher.group(FILENAME_AND_PATH_GROUP_INDEX);
            final String extension = pathMatcher.group(EXTENSION_GROUP_INDEX);


            final Matcher fileNameMatcher = FILECOPY_EXTRUDER.matcher(fileNameWithPath);

            if (!fileNameMatcher.matches()) {
                throw new IllegalArgumentException(
                    "Unexpected error: can't parse path -- {%s}".formatted(fileNameWithPath)
                );
            }

            final String filename = fileNameMatcher.group(FILENAME_GROUP_INDEX);
            final String copyTag = fileNameMatcher.group(COPY_TAG_GROUP_INDEX);
            final String copyCountStr = fileNameMatcher.group(COPY_COUNT_GROUP_INDEX);

            String newFileName = filename + " — копия";

            // try to create first copy if given file not copy
            if (copyTag == null && !Files.exists(Path.of(newFileName + extension))) {
                Files.copy(path, Path.of(newFileName + extension));
                return;
            }

            // else lets create next copy
            int copyCount = 1;
            if (copyCountStr != null) {
                copyCount = Integer.parseInt(copyCountStr);
            }

            Path copyFile;
            do {
                copyFile = Path.of(newFileName + " (%d)".formatted(++copyCount) + extension);
            } while (Files.exists(copyFile));

            Files.copy(path, copyFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
