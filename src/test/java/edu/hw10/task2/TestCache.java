package edu.hw10.task2;

public interface TestCache {
    @Cache
    int echo(int i);

    @Cache(persist = true)
    int echoPersist(int i);
}

record Echo() implements TestCache {
    @Override
    public int echo(int i) {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    @Override
    public int echoPersist(int i) {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
}
