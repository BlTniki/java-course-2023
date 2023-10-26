package edu.hw2.task3.connection;

import edu.hw2.task3.exception.ConnectionException;
import org.jetbrains.annotations.NotNull;

public interface Connection extends AutoCloseable {
    /**
     * Executes commands on remote server.
     * @param command to execute
     * @throws ConnectionException if the connection installation failed
     */
    void execute(@NotNull String command);
}
