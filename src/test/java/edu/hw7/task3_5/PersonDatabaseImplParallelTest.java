package edu.hw7.task3_5;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import edu.hw7.task3.PersonDatabaseImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonDatabaseImplParallelTest {
    private static PersonDatabase repo = new edu.hw7.task3.PersonDatabaseImpl();

    @BeforeEach
    public void before() {
        repo = new PersonDatabaseImpl();
    }

    @Test
    public void testParallel() throws InterruptedException {
        List<Thread> threadPool = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threadPool.add(new Thread(() -> {
                int id = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
                for (int j = 0; j < 100; j++) {
                    repo.add(new Person(id, "foo"+id, "bar", "lolkek"));
                    repo.findByName("foo" + id);
                    repo.delete(id);
                }
            }));
            threadPool.getLast().start();
        }
        for (var thread : threadPool) {
            thread.join();
        }
        assertEquals(0, repo.findByAddress("bar").size());
    }
}
