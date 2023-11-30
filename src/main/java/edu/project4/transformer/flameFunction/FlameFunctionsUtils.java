package edu.project4.transformer.flameFunction;

import edu.project4.transformer.Affine;
import edu.project4.transformer.variation.Variation;
import edu.project4.transformer.variation.VariationType;
import edu.project4.transformer.variation.WeightedVariation;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class FlameFunctionsUtils {
    public static final Random RAND = ThreadLocalRandom.current();
    public static final double AFFINE_MAX = 1.0;
    public static final double AFFINE_MIN = -1.0;
    public static final int FLAME_FUNC_MIN = 2;
    public static final int FLAME_FUNC_MAX = 21;
    public static final int VARIS_SIZE_MIN = 3;
    public static final int VARIS_SIZE_MAX = 7;

    private FlameFunctionsUtils() {
    }

    private static double randBetween(double min, double max) {
        return min + (max - min) * RAND.nextDouble();
    }

    /**
     * Create list of weighs 0.0 to 1.0 with sum == 1.0;
     * @return list of weighs
     */
    private static List<Double> createWeighs(int size) {
        final List<Double> probabilities = new ArrayList<>(size);
        double remain = 1.0;

        while (probabilities.size() < size - 1) {
            probabilities.add(remain * RAND.nextDouble());
            remain -= probabilities.getLast();
        }
        probabilities.add(remain);

        return probabilities;
    }

    /**
     * Builds Affine where each param between -1.5 to 1.5.
     * @return new Affine
     */
    public static @NotNull Affine buildAffine() {
        return new Affine(
            randBetween(AFFINE_MIN, AFFINE_MAX),
            randBetween(AFFINE_MIN, AFFINE_MAX),
            randBetween(AFFINE_MIN, AFFINE_MAX),
            randBetween(AFFINE_MIN, AFFINE_MAX),
            randBetween(AFFINE_MIN, AFFINE_MAX),
            randBetween(AFFINE_MIN, AFFINE_MAX)
        );
    }

    /**
     * Build list of WeightedVariation size of 1 to maxSize and making sure that weights sum is equal to 1.
     * Only the specified variTypes will be in the list, but not necessarily all
     * @param maxSize maximum list size
     * @param variTypes allowed variTypes
     * @return new list of WeightedVariation
     */
    public static @NotNull List<WeightedVariation> buildWeightedVariations(
            @Range(from = 1, to = Integer.MAX_VALUE) int maxSize,
            VariationType... variTypes
        ) {
        final Set<Variation> varis = new HashSet<>();
        for (int i = 0; i < maxSize; i++) {
            // pick random variType
            VariationType variType = variTypes[RAND.nextInt(variTypes.length)];

            varis.add(variType.vari);
        }

        // make weight and make WeightedVariation
        final List<Double> weights = createWeighs(varis.size());

        final List<WeightedVariation> weightedVariations = new ArrayList<>(varis.size());
        int i = 0;
        for (var vari : varis) {
            weightedVariations.add(new WeightedVariation(weights.get(i), vari));
            i++;
        }

        return weightedVariations;
    }

    /**
     * Builds flame function with given variTypes.
     * Generates Affine function with {@link #buildAffine()}
     * Number of variations somewhat between 1 and VARIS_SIZE_MAX.
     * All variation weighed, weighs positive and weighs sum is equal to 1.
     * Color picked randomly.
     * @param variTypes {@link VariationType}s that must be in flame function
     * @return new flame function
     */
    public static @NotNull FlameFunction buildFlameFunction(VariationType... variTypes) {
        final Affine affine = buildAffine();

        final int varisMaxSize = (int) Math.round(randBetween(VARIS_SIZE_MIN, VARIS_SIZE_MAX));
        final List<WeightedVariation> weightedVaris = buildWeightedVariations(varisMaxSize, variTypes);

        final Color color = new Color(RAND.nextFloat(), RAND.nextFloat(), RAND.nextFloat());

        return new FlameFunction(affine, weightedVaris, color);
    }

    /**
     * Builds flame function handler that contain somewhat FLAME_FUNC_MIN
     * to FLAME_FUNC_MAX number of flame functions with random probabilities.
     * @param variTypes {@link VariationType}s that flame functions must contain
     * @return new flame functions handler
     */
    public static @NotNull FlameFunctionsHandler buildHandler(VariationType... variTypes) {
        int countOfFunctions = (int) Math.round(randBetween(FLAME_FUNC_MIN, FLAME_FUNC_MAX));

        final List<FlameFunction> flameFunctions = new ArrayList<>(countOfFunctions);
        while (flameFunctions.size() < countOfFunctions) {
            flameFunctions.add(buildFlameFunction(variTypes));
        }

        final List<Double> probabilities = createWeighs(countOfFunctions);

        return new FlameFunctionsHandler(flameFunctions, probabilities);
    }
}
