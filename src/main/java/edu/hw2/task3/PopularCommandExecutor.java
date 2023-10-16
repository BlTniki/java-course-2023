package edu.hw2.task3;

import edu.hw2.task3.connection.Connection;
import edu.hw2.task3.exception.ConnectionException;
import edu.hw2.task3.manager.ConnectionManager;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    /**
     * Tries to execute command with max attempts.
     * @param command to execute
     * @throws ConnectionException if execution failed after max attempts
     */
    void tryExecute(String command) {
        Connection connection = manager.getConnection();

        // пробуем выполнить команду
        try (connection) {
            // делаем maxAttempts попыток
            for (int attempts = 1; attempts <= maxAttempts; attempts++) {
                try {
                    connection.execute(command);
                    break;
                } catch (ConnectionException e) {
                    // если кол-во попыток превышено, кидаем ошибку дальше
                    if (attempts == maxAttempts) {
                        throw e;
                    }
                }
            }
        } catch (Exception e) {
            throw new ConnectionException(
                "Failed to execute '%s' at server with %d attempts".formatted(command, maxAttempts),
                e
            );
        }
    }
}
