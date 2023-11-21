package edu.hw7.task3;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonDatabaseImplParallelTest extends MultithreadedTestCase {
    private PersonDatabaseImpl repo;

    @Override
    public void initialize() {
        repo = new PersonDatabaseImpl();
    }

    public void thread1() throws InterruptedException {
        repo.add(new Person(1, "foo", "bar", "lolkek"));
    }

    public void thread2() throws InterruptedException {
        repo.delete(1);
    }

    public void thread3() throws InterruptedException {
        repo.findByName("foo");
    }

    @Override
    public void finish() {
        assertTrue(repo.findByName("foo").size() < 2);
    }

    @Test
    public void testRepo() throws Throwable {
        TestFramework.runManyTimes(new PersonDatabaseImplParallelTest(), 1000);
    }
}
