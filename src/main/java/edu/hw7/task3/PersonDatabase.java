package edu.hw7.task3;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface PersonDatabase {
    void add(Person person);

    void delete(int id);

    @NotNull List<Person> findByName(@NotNull String name);

    @NotNull List<Person> findByAddress(@NotNull String address);

    @NotNull List<Person> findByPhone(@NotNull String phone);
}
