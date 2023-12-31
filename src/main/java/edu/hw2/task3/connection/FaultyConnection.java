package edu.hw2.task3.connection;

import edu.hw2.task3.exception.ConnErrorMessage;
import edu.hw2.task3.exception.ConnectionClosedException;
import edu.hw2.task3.exception.ConnectionException;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class FaultyConnection implements Connection {
    private final static int BOUNDARY = 100;
    private final static int GOOD_EXECUTES_STARTS_AT = 50;
    private boolean isOpen = true;

    @Override
    public void execute(@NotNull String command) {
        if (command.isEmpty()) {
            throw new IllegalArgumentException(ConnErrorMessage.EMPTY.message);
        }

        final int luckyNumber = new Random().nextInt(BOUNDARY);

        if (!isOpen) {
            throw new ConnectionClosedException(ConnErrorMessage.CLOSED.message);
        }

        if (luckyNumber < GOOD_EXECUTES_STARTS_AT) {
            throw new ConnectionException(ConnErrorMessage.FAILED.message);
        }
    }

    @Override
    public void close() {
        isOpen = false;
    }
}
