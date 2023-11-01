package edu.hw3.task8;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class BackwardIteratorTest {
    @Test
    void iter() {
        // given
        List<Integer> numbers = List.of(1,2,3,4,5);
        List<Integer> expected = numbers.reversed();

        // when
        List<Integer> actual = new ArrayList<>();
        for (Integer i: new BackwardIterator<>(numbers)) {
            actual.add(i);
        }

        // then
        assertThat(actual)
            .containsExactlyElementsOf(expected);
    }
}
