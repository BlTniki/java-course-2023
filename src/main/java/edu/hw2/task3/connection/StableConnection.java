package edu.hw2.task3.connection;

import edu.hw2.task3.exception.ConnErrorMessage;
import edu.hw2.task3.exception.ConnectionClosedException;
import org.jetbrains.annotations.NotNull;

public class StableConnection implements Connection {
    private boolean isOpen = true;

    @Override
    public void execute(@NotNull String command) {
        if (command.isEmpty()) {
            throw new IllegalArgumentException(ConnErrorMessage.EMPTY.message);
        }
        if (!isOpen) {
            throw new ConnectionClosedException(ConnErrorMessage.CLOSED.message);
        }
    }

    @Override
    public void close() {
        isOpen = false;
    }
}
