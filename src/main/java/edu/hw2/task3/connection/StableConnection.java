package edu.hw2.task3.connection;

import edu.hw2.task3.exception.ConnectionException;

public class StableConnection implements Connection {
    private boolean isOpen = true;

    @Override
    public void execute(String command) {
        if (!isOpen) {
            throw new ConnectionException("Connection is closed");
        }
    }

    @Override
    public void close() {
        isOpen = false;
    }
}
