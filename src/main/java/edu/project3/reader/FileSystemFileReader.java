package edu.project3.reader;

import edu.project3.exception.BadFilePathException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tools.ant.DirectoryScanner;
import org.jetbrains.annotations.NotNull;

public class FileSystemFileReader implements FileReader {
    @SuppressWarnings("RegExpRedundantEscape")
    private final static Pattern GLOB_EXTRUDER = Pattern.compile("[\\/]?[^\\/]*\\*.*");

    @Override
    public @NotNull Collection<String> getStringsFrom(@NotNull String filepath)
        throws IOException, BadFilePathException {
        List<Path> paths = new ArrayList<>();

        // create path (paths in glob case)
        final Matcher matcher = GLOB_EXTRUDER.matcher(filepath);
        try {
            if (matcher.find()) {
                final int startIdx = matcher.start();
                paths = getAllMatchedFiles(filepath.substring(0, startIdx), filepath.substring(startIdx));
            } else {
                paths.add(Path.of(filepath));
            }
        } catch (InvalidPathException | IllegalStateException e) {
            throw new BadFilePathException("Failed to understand that path: " + filepath);
        }

        // filter bad paths
        paths = paths.stream()
            .filter(Files::exists)
            .filter(Files::isExecutable)
            .toList();

        if (paths.isEmpty()) {
            throw new BadFilePathException("Failed to find any files by path: " + filepath);
        }


        List<String> lines = new ArrayList<>();

        for (Path path : paths) {
            lines.addAll(Files.readAllLines(path));
        }

        return lines;
    }

    private @NotNull List<Path> getAllMatchedFiles(@NotNull String baseDir, @NotNull String pattern) {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[] {pattern});
        scanner.setBasedir(baseDir.isEmpty() ? "/" : baseDir);
        scanner.setCaseSensitive(false);
        scanner.scan();

        ArrayList<Path> paths = new ArrayList<>();
        for (String str : scanner.getIncludedFiles()) {
            paths.add(Path.of(baseDir + "/" + str));
        }
        return paths;
    }
}
