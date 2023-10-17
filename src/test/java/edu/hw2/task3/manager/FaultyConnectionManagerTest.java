package edu.hw2.task3.manager;

import edu.hw2.task3.connection.FaultyConnection;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FaultyConnectionManagerTest {

    @Test
    void getConnection() {
        var conn = new FaultyConnectionManager().getConnection();

        assertThat(conn)
            .isInstanceOf(FaultyConnection.class);
    }
}
