package edu.project4.fractalFrame;

import edu.project4.histogram.Histogram;
import edu.project4.transformer.Point;
import edu.project4.transformer.flameFunction.FlameFunction;
import edu.project4.transformer.flameFunction.FlameFunctionsHandler;
import edu.project4.transformer.flameFunction.FlameFunctionsUtils;
import edu.project4.transformer.variation.VariationType;
import java.awt.Color;
import java.util.Random;
import org.jetbrains.annotations.Range;

public class FractalFlame {
    private final static double X_MIN = -1;
    private final static double X_MAX = 1;
    private final static double Y_MIN = -1;
    private final static double Y_MAX = 1;
    private final static int INIT_ITER = -20;

    private final int xRes;
    private final int yRes;
    private final int iterPerSample;
    private final FlameFunctionsHandler handler;
    public final Histogram histogram;

    public FractalFlame(int xRes, int yRes, int iterPerSample, VariationType... variTypes) {
        this.xRes = xRes;
        this.yRes = yRes;

        this.iterPerSample = iterPerSample;

        this.handler = FlameFunctionsUtils.buildHandler(variTypes);
        this.histogram = Histogram.createDefault(xRes, yRes);
    }

    private double randBetween(double min, double max, Random rand) {
        return min + (max - min) * rand.nextDouble();
    }

    public void proceedSamples(@Range(from = 0, to = Integer.MAX_VALUE) int samplesCount, Random rand) {
        for (int sample = 0; sample < samplesCount; sample++) {
            Point newPoint = new Point(randBetween(X_MIN, X_MAX, rand), randBetween(Y_MIN, Y_MAX, rand));
            Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());

            for (int it = INIT_ITER; it < iterPerSample; it++) {
                final FlameFunction flameFunction = handler.getRand(rand);

                newPoint = flameFunction.apply(newPoint);
                color = flameFunction.blend(color);

                Point pixelPoint = new Point(
                    Math.round(((X_MAX - newPoint.x()) / (X_MAX - X_MIN)) * xRes),
                    Math.round(((Y_MAX - newPoint.y()) / (Y_MAX - Y_MIN)) * yRes)
                );

                if (it >= 0 && pixelPoint.inRange(0, xRes, 0, yRes)) {
                    histogram.increaseCounterAt((int) pixelPoint.x(), (int) pixelPoint.y());
                    histogram.setColorAt((int) pixelPoint.x(), (int) pixelPoint.y(), color);
                }
            }
        }
    }
}
