package edu.hw7.task3_5;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import edu.hw7.task3.PersonDatabaseImpl;
import java.util.Random;
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
        Thread lastThread = null;
        for (int i = 0; i < 10000; i++) {
            lastThread = new Thread(() -> {
                int id = new Random().nextInt(Integer.MAX_VALUE);
                repo.add(new Person(id, "foo"+id, "bar", "lolkek"));
                repo.findByName("foo" + id);
                repo.delete(id);
            });
            lastThread.start();
        }
        lastThread.join();
        assertEquals(0, repo.findByAddress("bar").size());
    }
}
