package edu.hw7.task3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PersonDatabaseImplTest {
    PersonDatabase repo;

    @BeforeEach
    void setUp() {
        repo = new PersonDatabaseImpl();
    }

    @Test
    @DisplayName("Проверим добавление человека")
    void add() {
        var person = new Person(1, "foo", "bar", "lolkek");

        repo.add(person);

        assertThat(repo.findByName(person.name()))
            .containsExactly(person);
        assertThat(repo.findByAddress(person.address()))
            .containsExactly(person);
        assertThat(repo.findByPhone(person.phoneNumber()))
            .containsExactly(person);
    }

    @Test
    @DisplayName("Проверим удаление человека")
    void delete() {
        var person = new Person(1, "foo", "bar", "lolkek");
        add();

        repo.delete(person.id());

        assertThat(repo.findByName(person.name()))
            .isEmpty();
        assertThat(repo.findByAddress(person.address()))
            .isEmpty();
        assertThat(repo.findByPhone(person.phoneNumber()))
            .isEmpty();
    }
}
