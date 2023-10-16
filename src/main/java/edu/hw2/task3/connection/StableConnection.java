package edu.hw2.task3.connection;

import edu.hw2.task3.exception.ConnectionClosedException;
import org.jetbrains.annotations.NotNull;

public class StableConnection implements Connection {
    private boolean isOpen = true;

    @Override
    public void execute(@NotNull String command) {
        if (command.isEmpty()) {
            throw new IllegalArgumentException("Command empty");
        }
        if (!isOpen) {
            throw new ConnectionClosedException("Connection is closed");
        }
    }

    @Override
    public void close() {
        isOpen = false;
    }
}
