package edu.hw2.task3.exception;

public class ConnectionClosedException extends RuntimeException {
    public ConnectionClosedException(String message) {
        super(message);
    }
}
