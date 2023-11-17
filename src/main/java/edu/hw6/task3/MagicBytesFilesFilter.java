package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public class MagicBytesFilesFilter extends FilesFilter {
    private final byte[] magicNumbers;

    public MagicBytesFilesFilter(byte @NotNull [] magicNumbers) {
        this.magicNumbers = magicNumbers;
    }

    @Override
    protected boolean check(@NotNull Path entry) throws IOException {
        byte[] actualNumbers;
        try (var stream = Files.newInputStream(entry);) {
             actualNumbers = stream.readNBytes(magicNumbers.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < magicNumbers.length; i++) {
            if (magicNumbers[i] != actualNumbers[i]) {
                return false;
            }
        }

        return true;
    }
}
