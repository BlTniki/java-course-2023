package edu.project2.generator;

import java.util.Random;

public class GeneratorManager {
    private final Random random;

    public GeneratorManager(Random random) {
        this.random = random;
    }

    public Generator getInstanceOf(GenerationType type) {
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
