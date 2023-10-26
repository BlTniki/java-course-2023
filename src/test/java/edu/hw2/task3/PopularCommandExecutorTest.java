package edu.hw2.task3;

import edu.hw2.task3.connection.Connection;
import edu.hw2.task3.exception.ConnErrorMessage;
import edu.hw2.task3.exception.ConnectionException;
import edu.hw2.task3.manager.ConnectionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PopularCommandExecutorTest {
    private Connection connection;
    private ConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        // configure to fail 3 times
        this.connection = mock(Connection.class);
        doThrow(new ConnectionException(ConnErrorMessage.FAILED.message))
            .doThrow(new ConnectionException(ConnErrorMessage.FAILED.message))
            .doThrow(new ConnectionException(ConnErrorMessage.FAILED.message))
            .doNothing()
            .when(connection).execute(anyString());

        this.connectionManager = mock(ConnectionManager.class);
        when(connectionManager.getConnection()).thenReturn(connection);
    }

    @Test
    @DisplayName("Проверка успешного выполнения")
    void tryExecute_success() throws Exception {
        // given
        PopularCommandExecutor executor = new PopularCommandExecutor(connectionManager, 5);

        // when
        executor.updatePackages();

        // then
        verify(connection, times(4)).execute(anyString());
        verify(connection, times(1)).close();
    }

    @Test
    @DisplayName("Проверка провального выполнения")
    void tryExecute_fail() throws Exception {
        // given
        PopularCommandExecutor executor = new PopularCommandExecutor(connectionManager, 3);

        assertThatThrownBy(executor::updatePackages)
            .isInstanceOf(ConnectionException.class);

        verify(connection, times(3)).execute(anyString());
        verify(connection, times(1)).close();
    }
}
