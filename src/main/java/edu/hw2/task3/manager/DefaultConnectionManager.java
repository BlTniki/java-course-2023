package edu.hw2.task3.manager;

import edu.hw2.task3.connection.Connection;
import edu.hw2.task3.connection.FaultyConnection;
import edu.hw2.task3.connection.StableConnection;
import java.util.Random;

public class DefaultConnectionManager implements ConnectionManager {
    private final static int BOUNDARY = 100;
    private final static int GOOD_CONNECTION_STARTS_AT = 10;

    @Override
    public Connection getConnection() {
        final int luckyNumber = new Random().nextInt(BOUNDARY);

        if (luckyNumber < GOOD_CONNECTION_STARTS_AT) {
            return new FaultyConnection();
        } else {
            return new StableConnection();
        }
    }
}
