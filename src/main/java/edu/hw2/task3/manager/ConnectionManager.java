package edu.hw2.task3.manager;

import edu.hw2.task3.connection.Connection;

public interface ConnectionManager {
    /**
     * Return remote server connection entity.
     * @return remote server connection entity
     */
    Connection getConnection();
}
