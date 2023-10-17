package edu.hw2.task3.connection;

import edu.hw2.task3.exception.ConnectionClosedException;
import edu.hw2.task3.exception.ConnectionException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FaultyConnectionTest {

    @Test
    void execute() {
        var conn = new StableConnection();
        try (conn) {
            int attempts = 0;
            while (attempts < 100) {
                conn.execute("kek");
                attempts++;
            }
        } catch (ConnectionException e) {
            assertThat(e.getMessage())
                .isEqualTo("Failed to execute command");
        }

        assertThatThrownBy(() -> conn.execute("kek"))
            .isInstanceOf(ConnectionClosedException.class);
    }
}
