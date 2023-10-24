package edu.hw3.task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

public class BackwardIterator<T> implements Iterator<T>, Iterable<T> {
    private final List<T> elms;
    private int cursor;

    public BackwardIterator(Collection<T> collection) {
        this.elms = List.copyOf(collection);
        this.cursor = elms.size();
    }

    @Override
    public boolean hasNext() {
        return cursor > 0;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return elms.get(--cursor);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this;
    }
}
