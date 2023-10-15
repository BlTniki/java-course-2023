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

    void tryExecute(String command) {
        Connection connection = manager.getConnection();

        int attempts = 0;
        while (true) {
            try (connection) {
                connection.execute(command);
                break;
            } catch (Exception e) {
                attempts++;
                if (attempts < maxAttempts) {
                    throw new ConnectionException(
                        "Failed to execute '%s' at server with %d attempts".formatted(command, maxAttempts),
                        e
                    );
                }
                continue;
            }
        }
    }
}
