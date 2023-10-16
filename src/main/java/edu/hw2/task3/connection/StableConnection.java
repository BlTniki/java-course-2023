package edu.hw2.task3.connection;

import edu.hw2.task3.exception.ConnectionClosedException;

public class StableConnection implements Connection {
    private boolean isOpen = true;

    @Override
    public void execute(String command) {
        if (!isOpen) {
            throw new ConnectionClosedException("Connection is closed");
        }
    }

    @Override
    public void close() {
        isOpen = false;
    }
}
