package edu.project4.fractalFrame;

import edu.project4.histogram.Histogram;
import edu.project4.transformer.Point;
import edu.project4.transformer.flameFunction.FlameFunction;
import edu.project4.transformer.flameFunction.FlameFunctionsHandler;
import edu.project4.transformer.flameFunction.FlameFunctionsUtils;
import edu.project4.transformer.variation.VariationType;
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FractalFlame {
    private final static double X_MIN = -1;
    private final static double X_MAX = 1;
    private final static double Y_MIN = -1;
    private final static double Y_MAX = 1;
    private final static int INIT_ITER = -20;

    private final int xRes;
    private final int yRes;
    private final int samples;
    private final int it;
    private final Random rand;
    private final FlameFunctionsHandler handler;
    public final Histogram histogram;

    private final AtomicInteger curSampleNumber;
    public final AtomicBoolean isDone;

    public FractalFlame(int xRes, int yRes, int samples, int it, Random rand, VariationType... variTypes) {
        this.xRes = xRes;
        this.yRes = yRes;

        this.samples = samples;
        this.curSampleNumber = new AtomicInteger();
        this.isDone = new AtomicBoolean();

        this.it = it;
        this.rand = rand;

        this.handler = FlameFunctionsUtils.buildHandler(variTypes);
        this.histogram = Histogram.createDefault(xRes, yRes);
    }

    private double randBetween(double min, double max) {
        return min + (max - min) * rand.nextDouble();
    }

    public void procceedSample() {
        if (curSampleNumber.getAndIncrement() >= samples) {
            isDone.set(true);
            return;
        }

        Point newPoint = new Point(randBetween(X_MIN, X_MAX), randBetween(Y_MIN, Y_MAX));
        Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());

        for (int i = INIT_ITER; i < it; i++) {
            final FlameFunction flameFunction = handler.getRand(rand);

            newPoint = flameFunction.apply(newPoint);
            color = flameFunction.blend(color);

            Point pixelPoint = new Point(
                Math.round(((X_MAX - newPoint.x()) / (X_MAX - X_MIN)) * xRes),
                Math.round(((Y_MAX - newPoint.y()) / (Y_MAX - Y_MIN)) * yRes)
            );

            if (i >= 0 && pixelPoint.inRange(0, xRes, 0, yRes)) {
                histogram.increaseCounterAt((int) pixelPoint.x(), (int) pixelPoint.y());
                histogram.changeColorAt((int) pixelPoint.x(), (int) pixelPoint.y(), color);
            }
        }
    }

    public boolean hasRemainSamples() {
        return isDone.get();
    }
}
