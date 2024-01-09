package edu.hw7.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class PersonDatabaseImpl implements PersonDatabase {
    private final Map<Integer, Person> personById;
    private final Map<String, List<Person>> personByName;
    private final Map<String, List<Person>> personByAddress;
    private final Map<String, List<Person>> personByPhoneNumber;

    public PersonDatabaseImpl() {
        this.personById = new HashMap<>();
        this.personByName = new HashMap<>();
        this.personByAddress = new HashMap<>();
        this.personByPhoneNumber = new HashMap<>();
    }

    private void doSync(Runnable runnable) {
        synchronized (personById) {
            synchronized (personByName) {
                synchronized (personByAddress) {
                    synchronized (personByPhoneNumber) {
                        runnable.run();
                    }
                }
            }
        }
    }

    @Override
    public void add(@NotNull Person person) {
        doSync(() -> {
            personById.put(person.id(), person);
            personByName.computeIfAbsent(person.name(), k -> new ArrayList<>()).add(person);
            personByAddress.computeIfAbsent(person.address(), k -> new ArrayList<>()).add(person);
            personByPhoneNumber.computeIfAbsent(person.phoneNumber(), k -> new ArrayList<>()).add(person);
        });
    }

    @Override
    public void delete(int id) {
        doSync(() -> {
            Person person = personById.remove(id);
            if (person != null) {
                removeFromIndex(personByName, person.name(), person);
                removeFromIndex(personByAddress, person.address(), person);
                removeFromIndex(personByPhoneNumber, person.phoneNumber(), person);
            }
        });
    }

    private void removeFromIndex(Map<String, List<Person>> index, String key, Person person) {
        List<Person> persons = index.getOrDefault(key, new ArrayList<>());
        persons.remove(person);
        if (persons.isEmpty()) {
            index.remove(key);
        }
    }

    @Override
    public @NotNull List<Person> findByName(@NotNull String name) {
        List<Person> persons = new ArrayList<>();
        doSync(() -> persons.addAll(personByName.getOrDefault(name, new ArrayList<>())));
        return persons;
    }

    @Override
    public @NotNull List<Person> findByAddress(@NotNull String address) {
        List<Person> persons = new ArrayList<>();
        doSync(() -> persons.addAll(personByAddress.getOrDefault(address, new ArrayList<>())));
        return persons;
    }

    @Override
    public @NotNull List<Person> findByPhone(@NotNull String phone) {
        List<Person> persons = new ArrayList<>();
        doSync(() -> persons.addAll(personByPhoneNumber.getOrDefault(phone, new ArrayList<>())));
        return persons;
    }
}
