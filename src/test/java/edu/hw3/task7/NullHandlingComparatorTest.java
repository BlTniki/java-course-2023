package edu.hw3.task7;

import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class NullHandlingComparatorTest {
    @Test
    void nullCheck() {
        TreeMap<String, String> tree = new TreeMap<>(new NullHandlingComparator<>());
        tree.put(null, "test");

        assertThat(tree.containsKey(null))
            .isTrue();
    }
}
