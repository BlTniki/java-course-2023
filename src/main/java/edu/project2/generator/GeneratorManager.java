package edu.project2.generator;

import java.util.Random;
import org.jetbrains.annotations.NotNull;

/**
 * Class that handle creating generator instance with specific type
 */
public class GeneratorManager {
    private final Random random;

    public GeneratorManager(Random random) {
        this.random = random;
    }

    /**
     * Returns Generator class with given type.
     * @param type algorithm type
     * @return Generator instance
     */
    public @NotNull Generator getInstanceOf(GenerationType type) {
        switch (type) {
            case RecursiveDivision -> {
                return new RecursiveDivisionGenerator(random);
            }
            default -> {
                return new RecursiveDivisionGenerator(random);
            }
        }
    }
}
