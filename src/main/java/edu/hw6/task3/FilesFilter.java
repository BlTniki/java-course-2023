package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public abstract class FilesFilter implements DirectoryStream.Filter<Path> {
    protected FilesFilter child;

    /**
     * Adds chain to responsibility chain.
     * @param filter chain to add
     * @return this instance
     */
    public @NotNull FilesFilter and(@NotNull FilesFilter filter) {
        if (child == null) {
            child = filter;
        } else {
            child.and(filter);
        }

        return this;
    }

    @Override
    public boolean accept(Path entry) throws IOException {
        if (!check(entry)) {
            return false;
        }

        if (child == null) {
            return true;
        }

        return child.accept(entry);
    }

    /**
     * Decides if the given directory entry should be accepted or filtered,
     * but does not give further along the chain of responsibility.
     * @param entry the directory entry to be tested
     * @return true if the directory entry should be accepted
     * @throws IOException if something goes wrong ¯\_(ツ)_/¯
     */
    abstract protected boolean check(@NotNull Path entry) throws IOException;
}
