package edu.hw2.task3.exception;

public enum ConnErrorMessage {
    EMPTY("Command Empty"),
    CLOSED("Connection is closed"),
    FAILED("Failed to execute command");

    public final String message;

    ConnErrorMessage(String message) {
        this.message = message;
    }
}
