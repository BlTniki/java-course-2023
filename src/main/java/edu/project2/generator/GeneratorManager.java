package edu.project2.generator;

import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class GeneratorManager {
    private final Random random;

    public GeneratorManager(Random random) {
        this.random = random;
    }

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
