package edu.project4.fractalFrame;

import edu.project4.fractalFrame.histogram.Histogram;
import edu.project4.transformer.Point;
import edu.project4.transformer.flameFunction.FlameFunction;
import edu.project4.transformer.flameFunction.FlameFunctionsHandler;
import edu.project4.transformer.flameFunction.FlameFunctionsUtils;
import edu.project4.transformer.variation.VariationType;
import java.awt.Color;
import java.util.Random;
import org.jetbrains.annotations.Range;

/**
 * Flame generator. Fills the hit histogram that can be rendered to a picture.
 */
public class FractalFlame {
    private final static double X_MIN = -1;
    private final static double X_MAX = 1;
    private final static double Y_MIN = -1;
    private final static double Y_MAX = 1;
    private final static double P_MIN = Math.max(X_MIN, Y_MIN);
    private final static double P_MAX = Math.min(X_MAX, Y_MAX);
    private final static int INIT_ITER = -20;

    private final int xRes;
    private final int yRes;
    private final boolean xResLowerYRes;
    private final int minRes;
    private final double offsetRes;
    private final int iterPerSample;
    private final FlameFunctionsHandler handler;
    public final Histogram histogram;

    /**
     * Init generator by creating flame functions set and histogram plane.
     * @param xRes width of histogram plane.
     * @param yRes height of histogram plane.
     * @param iterPerSample how many chaos game iterations with sample point.
     * @param variTypes list of allowed variations.
     */
    public FractalFlame(int xRes, int yRes, int iterPerSample, VariationType... variTypes) {
        this.xRes = xRes;
        this.yRes = yRes;
        this.xResLowerYRes = xRes < yRes;
        this.minRes = Math.min(xRes, yRes);
        this.offsetRes = ((double) Math.max(xRes, yRes) - Math.min(xRes, yRes)) / 2.0;

        this.iterPerSample = iterPerSample;

        this.handler = FlameFunctionsUtils.buildHandler(variTypes);
        this.histogram = Histogram.createDefault(xRes, yRes);
    }

    private double randBetween(double min, double max, Random rand) {
        return min + (max - min) * rand.nextDouble();
    }

    private Point mapToHist(Point point) {
        if (xResLowerYRes) {
            return new Point(
                minRes - Math.round(((P_MAX - point.x()) / (P_MAX - P_MIN)) * minRes),
                offsetRes + minRes - Math.round(((P_MAX - point.y()) / (P_MAX - P_MIN)) * minRes)
            );
        }
        return new Point(
            offsetRes + minRes - Math.round(((P_MAX - point.x()) / (P_MAX - P_MIN)) * minRes),
            minRes - Math.round(((P_MAX - point.y()) / (P_MAX - P_MIN)) * minRes)
        );
    }

    /**
     * Proceed next samplesCount samples.
     * @param samplesCount how much samples proceed
     * @param rand Random instance so you can avoid multi-thread blocking.
     */
    public void proceedSamples(@Range(from = 0, to = Integer.MAX_VALUE) int samplesCount, Random rand) {
        for (int sample = 0; sample < samplesCount; sample++) {
            Point newPoint = new Point(randBetween(X_MIN, X_MAX, rand), randBetween(Y_MIN, Y_MAX, rand));
            Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());

            for (int it = INIT_ITER; it < iterPerSample; it++) {
                final FlameFunction flameFunction = handler.getRand(rand);

                newPoint = flameFunction.apply(newPoint);
                color = flameFunction.blend(color);

                Point pixelPoint = mapToHist(newPoint);

                if (it >= 0 && pixelPoint.inRange(0, xRes, 0, yRes)) {
                    histogram.increaseCounterAt((int) pixelPoint.x(), (int) pixelPoint.y());
                    histogram.setColorAt((int) pixelPoint.x(), (int) pixelPoint.y(), color);
                }
            }
        }
    }
}
