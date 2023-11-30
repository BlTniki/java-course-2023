package edu.hw6.task4;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import org.jetbrains.annotations.NotNull;

public final class FileWriterUtils {
    private FileWriterUtils() {
    }

    public static void writeAUFWord(@NotNull Path path) {
        if (Files.exists(path) && !path.toFile().isFile()) {
            throw new IllegalArgumentException("Path must be a file!");
        }

        try (OutputStream fileOutputStream = new FileOutputStream(path.toFile());
             OutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, new Adler32());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                 bufferedOutputStream, StandardCharsets.UTF_8
             );
             PrintWriter printWriter = new PrintWriter(outputStreamWriter)) {

            printWriter.println("Programming is learned by writing programs. ― Brian Kernighan");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
