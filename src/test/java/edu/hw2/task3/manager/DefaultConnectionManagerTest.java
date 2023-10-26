package edu.hw2.task3.manager;

import edu.hw2.task3.connection.FaultyConnection;
import edu.hw2.task3.connection.StableConnection;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultConnectionManagerTest {

    @Test
    void getConnection() {
        boolean stableWasThere = false;
        boolean faultyWasThere = false;

        for (int i = 0; i < 100; i++) {
            var conn = new DefaultConnectionManager().getConnection();

            if (!stableWasThere) {
                stableWasThere = conn instanceof StableConnection;
            }
            if (!faultyWasThere) {
                faultyWasThere = conn instanceof FaultyConnection;
            }
        }

        assertThat(stableWasThere).isTrue();
        assertThat(faultyWasThere).isTrue();
    }
}
