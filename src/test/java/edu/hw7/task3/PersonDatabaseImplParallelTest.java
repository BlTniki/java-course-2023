package edu.hw7.task3;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PersonDatabaseImplParallelTest {

    @Test
    public void testParallel() throws InterruptedException {
        final PersonDatabase repo = new PersonDatabaseImpl();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                repo.add(new Person(1, "foo", "bar", "lolkek"));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                repo.delete(1);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                int sizes;
                synchronized (repo) {
                     sizes = repo.findByPhone("lolkek").size() + repo.findByAddress("bar").size();
                }
                System.out.println(sizes);
                assertThat(sizes)
                    .is(new Condition<>(s -> s == 0 || s == 2, "0 or 2"));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
