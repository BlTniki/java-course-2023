package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FindDirectoriesTask extends RecursiveTask<List<Path>> {
    private final Path directory;
    private final int minFilesNumber;
    private int filesNumber;

    FindDirectoriesTask(Path directory, int minFilesNumber) {
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("given path is not dir!");
        }
        this.directory = directory;
        this.minFilesNumber = minFilesNumber;
    }

    @Override
    protected List<Path> compute() {
        List<FindDirectoriesTask> subtasks = new ArrayList<>();
        List<Path> result = new ArrayList<>();

        try (var dirStream = Files.newDirectoryStream(directory)) {
            for (var path : dirStream) {
                try {
                    if (Files.isDirectory(path)) {
                        FindDirectoriesTask subtask = new FindDirectoriesTask(path, minFilesNumber);
                        subtask.fork();
                        subtasks.add(subtask);
                    } else {
                        filesNumber += 1;
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

        for (FindDirectoriesTask subtask : subtasks) {
            var subResult = subtask.join();
            if (subtask.filesNumber >= minFilesNumber) {
                result.addAll(subResult);
                result.add(subtask.directory);
            }
            filesNumber += subtask.filesNumber;
        }

        return result;
    }
}
