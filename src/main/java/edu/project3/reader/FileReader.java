package edu.project3.reader;

import edu.project3.exception.BadFilePathException;
import java.io.IOException;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

public interface FileReader {
    /**
     * Open file with given file, read all text lines and return them as string collection.
     * If filepath points at several files method will return lines from all files in a single collection.
     * @param filepath path to the file
     * @return Collection of text lines
     * @throws IOException if anything unexpected with io happened
     * @throws BadFilePathException if bad path to the file was given
     */
    @NotNull Collection<String> getStringsFrom(@NotNull String filepath) throws IOException, BadFilePathException;
}
