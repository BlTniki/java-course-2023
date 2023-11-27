package edu.hw8.task1;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AufClientServerTest {
    @Test
    @DisplayName("Проверка базовой функциональности")
    void basic_test() throws IOException {
        AufServer server = new AufServer(50001, 1, 50);
        AufClient client = new AufClient("localhost", 50001);

        String answer = client.getAufLineByKey("личности");

        assertThat(answer)
            .isEqualTo("Не переходи на личности там, где их нет");

        server.kill();
    }

    @Test
    @DisplayName("Проверка работы в многопотоке на разных кол-вах потоках")
    void parallel_test() throws IOException {
        for (Integer poolSize : List.of(1, 6, 12, 24, 50, 100)) {
            AufServer server = new AufServer(50000, poolSize, 10_000);
            ConcurrentLinkedQueue<Long> endTimes = new ConcurrentLinkedQueue<>();

            ArrayList<Thread> clients = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                clients.add(new Thread(() -> {
                    long startTime = System.currentTimeMillis();
                    while (true) {
                        try {
                            AufClient client = new AufClient("localhost", 50000);
                            String answer = client.getAufLineByKey("личности");
                            assertThat(answer)
                                .isEqualTo("Не переходи на личности там, где их нет");
                            endTimes.add(System.currentTimeMillis() - startTime);
                            break;
                        } catch (RuntimeException e) {
                            if (! (e.getCause() instanceof ConnectException)) {
                                throw e;
                            }
                        }
                    }
                }));
            }

            long startTime = System.currentTimeMillis();
            clients.forEach(Thread::start);
            clients.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            double endTimeMS = ((double)System.currentTimeMillis() - startTime) / 1_000_000;

            double avgThreadLiveTimeMS = endTimes.stream()
                .mapToDouble(Double::valueOf)
                .average()
                .orElse(0);

            System.out.printf("%d threads%n", poolSize);
            System.out.printf("\tTime consumed: %,.5f ms%n", endTimeMS);
            System.out.printf("\tAverage thread live time: %,.5f ns%n", avgThreadLiveTimeMS);

            server.kill();
        }
    }
}
