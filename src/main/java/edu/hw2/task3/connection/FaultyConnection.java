package edu.hw2.task3.connection;

import edu.hw2.task3.exception.ConnectionClosedException;
import edu.hw2.task3.exception.ConnectionException;
import java.util.Random;

public class FaultyConnection implements Connection {
    private final static int BOUNDARY = 100;
    private final static int GOOD_EXECUTES_STARTS_AT = 50;
    private boolean isOpen = true;

    @Override
    public void execute(String command) {
        final int luckyNumber = new Random().nextInt(BOUNDARY);

        if (!isOpen) {
            throw new ConnectionClosedException("Connection is closed");
        }

        if (luckyNumber < GOOD_EXECUTES_STARTS_AT) {
            throw new ConnectionException("Failed to execute command");
        }
    }

    @Override
    public void close() {
        isOpen = false;
    }
}
