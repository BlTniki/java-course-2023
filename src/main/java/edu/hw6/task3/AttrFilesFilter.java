package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class AttrFilesFilter extends FilesFilter {
    private final List<Attr> attrList;

    public AttrFilesFilter(@NotNull List<Attr> attrList) {
        this.attrList = attrList;
    }

    @Override
    protected boolean check(@NotNull Path entry) throws IOException {
        for (Attr attr: attrList) {
            if (!Files.getAttribute(entry, attr.name()).equals(attr.val())) {
                return false;
            }
        }

        return true;
    }

    public record Attr(@NotNull String name, @NotNull Object val) {}
}
