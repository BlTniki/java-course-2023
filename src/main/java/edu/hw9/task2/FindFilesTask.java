package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class FindFilesTask extends RecursiveTask<List<Path>> {
    private final Path directory;
    private final Predicate<Path> predicate;

    FindFilesTask(Path directory, Predicate<Path> predicate) {
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("given path is not dir!");
        }
        this.directory = directory;
        this.predicate = predicate;
    }

    @Override
    protected List<Path> compute() {
        List<FindFilesTask> subtasks = new ArrayList<>();
        List<Path> result = new ArrayList<>();

        try (var dirStream = Files.newDirectoryStream(directory)) {
            for (var path : dirStream) {
                try {
                    if (Files.isDirectory(path)) {
                        FindFilesTask subtask = new FindFilesTask(path, predicate);
                        subtask.fork();
                        subtasks.add(subtask);
                    } else {
                        if (predicate.test(path)) {
                            result.add(path); // добавляем файл к результату, если удовлетворяет предикату
                        }
                    }
                } catch (SecurityException e) {
                    continue;
                }
            }
        } catch (AccessDeniedException e) {
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (FindFilesTask subtask : subtasks) {
            result.addAll(subtask.join()); // ожидаем результат подзадачи и добавляем его к результату
        }

        return result;
    }
}
