package edu.project3.wildcard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * <a href="https://github.com/EsotericSoftware/wildcard">Original</a>
 */
@SuppressWarnings({"checkstyle:ReturnCount", "checkstyle:CyclomaticComplexity"})
class GlobScanner {
    private final File rootDir;
    private final List<String> matches = new ArrayList<>(128);

    GlobScanner(File rootDir, List<String> includes, List<String> excludes, boolean ignoreCase) {
        this.rootDir = validDir(rootDir);

        if (includes == null) {
            throw new IllegalArgumentException("includes cannot be null.");
        }
        if (excludes == null) {
            throw new IllegalArgumentException("excludes cannot be null.");
        }

        if (includes.isEmpty()) {
            includes.add("**");
        }
        List<Pattern> includePatterns = new ArrayList<>(includes.size());
        for (String include : includes) {
            includePatterns.add(new Pattern(include, ignoreCase));
        }

        List<Pattern> allExcludePatterns = new ArrayList<>(excludes.size());
        for (String exclude : excludes) {
            allExcludePatterns.add(new Pattern(exclude, ignoreCase));
        }

        scanDir(rootDir, includePatterns);

        if (!allExcludePatterns.isEmpty()) {
            // For each file, see if any exclude patterns match.
            outerLoop:
            //
            for (Iterator<String> matchIter = matches.iterator(); matchIter.hasNext();) {
                String filePath = matchIter.next();
                List<Pattern> excludePatterns = new ArrayList<>(allExcludePatterns);
                try {
                    // Shortcut for excludes that are "**/XXX", just check file name.
                    for (Iterator<Pattern> excludeIter = excludePatterns.iterator(); excludeIter.hasNext();) {
                        Pattern exclude = excludeIter.next();
                        if (exclude.values.length == 2 && exclude.values[0].equals("**")) {
                            exclude.incr();
                            String fileName = filePath.substring(filePath.lastIndexOf(File.separatorChar) + 1);
                            if (exclude.matchesFile(fileName)) {
                                matchIter.remove();
                                continue outerLoop;
                            }
                            excludeIter.remove();
                        }
                    }
                    // Get the file names after the root dir.
                    String[] fileNames = filePath.split("\\\\" + File.separator);
                    for (String fileName : fileNames) {
                        for (Iterator<Pattern> excludeIter = excludePatterns.iterator(); excludeIter.hasNext();) {
                            Pattern exclude = excludeIter.next();
                            if (!exclude.matchesFile(fileName)) {
                                excludeIter.remove();
                                continue;
                            }
                            exclude.incr(fileName);
                            if (exclude.wasFinalMatch()) {
                                // Exclude pattern matched.
                                matchIter.remove();
                                continue outerLoop;
                            }
                        }
                        // Stop processing the file if none of the exclude patterns matched.
                        if (excludePatterns.isEmpty()) {
                            continue outerLoop;
                        }
                    }
                } finally {
                    for (Pattern exclude : allExcludePatterns) {
                        exclude.reset();
                    }
                }
            }
        }
    }

    @NotNull private static File validDir(File rootDir) {
        File tmpRootDir = rootDir;
        if (rootDir == null) {
            throw new IllegalArgumentException("rootDir cannot be null.");
        }
        if (!rootDir.exists()) {
            throw new IllegalArgumentException("Directory does not exist: " + tmpRootDir);
        }
        if (!rootDir.isDirectory()) {
            throw new IllegalArgumentException("File must be a directory: " + tmpRootDir);
        }
        try {
            tmpRootDir = tmpRootDir.getCanonicalFile();
        } catch (IOException ex) {
            throw new RuntimeException("OS error determining canonical path: " + tmpRootDir, ex);
        }
        return tmpRootDir;
    }

    private void scanDir(File dir, List<Pattern> includes) {
        if (!dir.canRead()) {
            return;
        }

        // See if patterns are specific enough to avoid scanning every file in the directory.
        boolean scanAll = false;
        for (Pattern include : includes) {
            if (include.value.indexOf('*') != -1 || include.value.indexOf('?') != -1) {
                scanAll = true;
                break;
            }
        }

        if (!scanAll) {
            // If not scanning all the files, we know exactly which ones to include.
            List<Pattern> matchingIncludes = new ArrayList<>(1);
            for (Pattern include : includes) {
                if (matchingIncludes.isEmpty()) {
                    matchingIncludes.add(include);
                } else {
                    matchingIncludes.set(0, include);
                }
                process(dir, include.value, matchingIncludes);
            }
        } else {
            // Scan every file.
            String[] fileNames = dir.list();
            if (fileNames == null) {
                return;
            }
            for (String fileName : fileNames) {
                // Get all include patterns that match.
                List<Pattern> matchingIncludes = new ArrayList<>(includes.size());
                for (Pattern include : includes) {
                    if (include.matchesFile(fileName)) {
                        matchingIncludes.add(include);
                    }
                }
                if (matchingIncludes.isEmpty()) {
                    continue;
                }
                process(dir, fileName, matchingIncludes);
            }
        }
    }

    private void process(File dir, String fileName, List<Pattern> matchingIncludes) {
        // Increment patterns that need to move to the next token.
        boolean isFinalMatch = false;
        List<Pattern> incrementedPatterns = new ArrayList<>();
        for (Iterator<Pattern> iter = matchingIncludes.iterator(); iter.hasNext();) {
            Pattern include = iter.next();
            if (include.incr(fileName)) {
                incrementedPatterns.add(include);
                if (include.isExhausted()) {
                    iter.remove();
                }
            }
            if (include.wasFinalMatch()) {
                isFinalMatch = true;
            }
        }

        File file = new File(dir, fileName);
        if (isFinalMatch) {
            int length = rootDir.getPath().length();
            if (!rootDir.getPath().endsWith(File.separator)) {
                length++;
            } // Lose starting slash.
            matches.add(file.getPath().substring(length));
        }
        if (!matchingIncludes.isEmpty() && file.isDirectory()) {
            scanDir(file, matchingIncludes);
        }

        // Decrement patterns.
        for (Pattern include : incrementedPatterns) {
            include.decr();
        }
    }

    public List<String> matches() {
        return matches;
    }

    public File rootDir() {
        return rootDir;
    }

    static class Pattern {
        String value;
        boolean ignoreCase;
        final String[] values;

        private int index;

        Pattern(String pattern, boolean ignoreCase) {
            String tmpPattern = pattern;
            this.ignoreCase = ignoreCase;

            tmpPattern = tmpPattern.replace('\\', '/');
            tmpPattern = tmpPattern.replaceAll("\\*\\*(?=[^/])", "**/*");
            tmpPattern = tmpPattern.replaceAll("(?<=[^/])\\*\\*", "*/**");
            if (ignoreCase) {
                tmpPattern = tmpPattern.toLowerCase();
            }

            values = tmpPattern.split("/");
            value = values[0];
        }

        boolean matchesPath(String path) {
            reset();
            String[] files = path.split("[\\\\/]");
            for (int i = 0, n = files.length; i < n; i++) {
                String file = files[i];
                if (i > 0 && isExhausted()) {
                    return false;
                }
                if (!matchesFile(file)) {
                    return false;
                }
                if (!incr(file) && isExhausted()) {
                    return true;
                }
            }
            return wasFinalMatch();
        }

        boolean matchesFile(final String fileName) {
            String tmpFilename = fileName;
            if (value.equals("**")) {
                return true;
            }

            if (ignoreCase) {
                tmpFilename = tmpFilename.toLowerCase();
            }

            // Shortcut if no wildcards.
            if (value.indexOf('*') == -1 && value.indexOf('?') == -1) {
                return tmpFilename.equals(value);
            }

            int i = 0;
            int j = 0;
            int fileNameLength = tmpFilename.length();
            int valueLength = value.length();
            while (i < fileNameLength && j < valueLength) {
                char c = value.charAt(j);
                if (c == '*') {
                    break;
                }
                if (c != '?' && c != tmpFilename.charAt(i)) {
                    return false;
                }
                i++;
                j++;
            }

            // If reached end of pattern without finding a * wildcard, the match has to fail if not same length.
            if (j == valueLength) {
                return fileNameLength == valueLength;
            }

            int cp = 0;
            int mp = 0;
            while (i < fileNameLength) {
                if (j < valueLength) {
                    char c = value.charAt(j);
                    if (c == '*') {
                        if (j++ >= valueLength) {
                            return true;
                        }
                        mp = j;
                        cp = i + 1;
                        continue;
                    }
                    if (c == '?' || c == tmpFilename.charAt(i)) {
                        j++;
                        i++;
                        continue;
                    }
                }
                j = mp;
                i = cp++;
            }

            // Handle trailing asterisks.
            while (j < valueLength && value.charAt(j) == '*') {
                j++;
            }

            return j >= valueLength;
        }

        String nextValue() {
            if (index + 1 == values.length) {
                return null;
            }
            return values[index + 1];
        }

        boolean incr(String fileName) {
            if (value.equals("**")) {
                if (index == values.length - 1) {
                    return false;
                }
                incr();
                if (matchesFile(fileName)) {
                    incr();
                } else {
                    decr();
                    return false;
                }
            } else {
                incr();
            }
            return true;
        }

        void incr() {
            index++;
            if (index >= values.length) {
                value = null;
            } else {
                value = values[index];
            }
        }

        void decr() {
            index--;
            if (index > 0 && values[index - 1].equals("**")) {
                index--;
            }
            value = values[index];
        }

        void reset() {
            index = 0;
            value = values[0];
        }

        boolean isExhausted() {
            return index >= values.length;
        }

        boolean isLast() {
            return index >= values.length - 1;
        }

        boolean wasFinalMatch() {
            return isExhausted() || (isLast() && value.equals("**"));
        }
    }
}
