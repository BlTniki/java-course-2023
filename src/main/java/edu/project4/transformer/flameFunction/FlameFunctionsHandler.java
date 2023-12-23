package edu.project4.transformer.flameFunction;

import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

/**
 * Handle flame function list and provide get random element method.
 * The choice of the element is not equally probable and is based on given probabilities.
 */
public class FlameFunctionsHandler {
    private static final double EPSILON = 0.000001d;
    public final List<FlameFunction> flameFunctions;
    public final List<Double> probabilities;

    /**
     * Handle flame function list and provide get random element method.
     * The choice of the element is not equally probable and is based on given probabilities.
     */
    public FlameFunctionsHandler(List<FlameFunction> flameFunctions, List<Double> probabilities) {
        if (flameFunctions.size() != probabilities.size()) {
            throw new IllegalArgumentException("The flameFunctions size and the probabilities size do not match");
        }

        double totalProbability = 0.0;
        for (double probability : probabilities) {
            if (probability < 0) {
                throw new IllegalArgumentException("Probabilities cannot be negative");
            }
            totalProbability += probability;
        }
        if (Math.abs(totalProbability - 1.0) > EPSILON) {
            throw new IllegalArgumentException("Probabilities must sum up to 1.0");
        }

        this.flameFunctions = flameFunctions;
        this.probabilities = probabilities;
    }

    public @NotNull FlameFunction get(int i) {
        return flameFunctions.get(i);
    }

    public @NotNull FlameFunction getRand(@NotNull Random rand) {
        double randomValue = rand.nextDouble();

        double cumulativeProbability = 0.0;
        for (int i = 0; i < flameFunctions.size(); i++) {
            cumulativeProbability += probabilities.get(i);
            if (randomValue <= cumulativeProbability) {
                return flameFunctions.get(i);
            }
        }

        // return first elem if we failed to pick randomly
        return flameFunctions.get(0);
    }
}
