package edu.hw10.task1;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RandomObjectGeneratorTest {

    @Test
    @DisplayName("Проверим что генератор выбирает публичный конструктор с наибольшим кол-вом аргументов")
    void nextObject_good() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new Random());

        TestClass good = rog.nextObject(TestClass.Good.class);

        assertThat(good.s()).isEqualTo("widest public");
    }

    @Test
    @DisplayName("Проверим что генератор кидает ошибку если у класса нет публичных конструкторов")
    void nextObject_bad() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new Random());

        assertThatThrownBy(() ->rog.nextObject(TestClass.Bad.class))
            .withFailMessage("Given class have no public constructors")
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверим что генератор выбирает публичный фабричный метод с наибольшим кол-вом аргументов")
    void factoryNextObject_good() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new Random());

        TestClass good = rog.nextObject(TestClass.Good.class, "create");

        assertThat(good.s()).isEqualTo("widest public factory");
    }

    @Test
    @DisplayName("Проверим что генератор кидает ошибку если у класса нет публичных фабричных методов с таким названием")
    void factoryNextObject_bad() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new Random());

        assertThatThrownBy(() ->rog.nextObject(TestClass.Bad.class, "create"))
            .withFailMessage("Given class have no public factory method called: create")
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверим что генератор создаёт суб POJO классы учитывая аннотацию")
    void createSubObject_good() {
        Random m = mock(Random.class);
        RandomObjectGenerator rog = new RandomObjectGenerator(m);

        rog.nextObject(TestClass.SubObject.class);

        verify(m, times(1)).nextBoolean();
    }
}
