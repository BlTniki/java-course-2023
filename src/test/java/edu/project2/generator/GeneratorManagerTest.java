package edu.project2.generator;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class GeneratorManagerTest {
    private GeneratorManager generatorManager;
    @BeforeEach
    void before() {
        this.generatorManager = new GeneratorManager(new Random());
    }

    @Test
    void getInstanceOf() {
        // given
        GenerationType type = GenerationType.RecursiveDivision;
        var expectedClass = RecursiveDivisionGenerator.class;

        // when
        var actualClass = generatorManager.getInstanceOf(type);

        // then
        assertThat(actualClass)
            .isInstanceOf(expectedClass);
    }
}
