package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public class SizeFilesFilter extends FilesFilter {
    private final long sizeThreshold;
    private final boolean mustBeBigger;

    /**
     * Creates new filter instance.
     * @param sizeThreshold Threshold for file size
     * @param mustBeBigger file must be bigger than threshold?
     */
    public SizeFilesFilter(long sizeThreshold, boolean mustBeBigger) {
        this.sizeThreshold = sizeThreshold;
        this.mustBeBigger = mustBeBigger;
    }

    @Override
    protected boolean check(@NotNull Path entry) throws IOException {
        long fileSize = Files.size(entry);

        if (mustBeBigger && fileSize <= sizeThreshold) {
            return false;
        }
        return mustBeBigger || fileSize <= sizeThreshold;
    }
}
