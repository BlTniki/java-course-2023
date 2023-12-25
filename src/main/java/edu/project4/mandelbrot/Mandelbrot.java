package edu.project4.mandelbrot;

import edu.project4.histogram.Histogram;
import edu.project4.transformer.mandelbrot.ComplexPoint;
import edu.project4.transformer.mandelbrot.MandelbrotIterator;
import java.awt.Color;

public class Mandelbrot {
    private final static double X_MIN = -1.5;
    private final static double X_MAX = 1.5;
    private final static double Y_MIN = -1.5;
    private final static double Y_MAX = 1.5;
    private final static double P_MIN = Math.max(X_MIN, Y_MIN);
    private final static double P_MAX = Math.min(X_MAX, Y_MAX);
    public static final int BAIL_OUT = 4;

    private final int xRes;
    private final int yRes;
    private final boolean xResLowerYRes;
    private final int minRes;
    private final double offsetRes;
    private final int threshold;
    public final Histogram histogram;

    public Mandelbrot(int xRes, int yRes, int threshold) {
        this.xRes = xRes;
        this.yRes = yRes;
        this.xResLowerYRes = xRes < yRes;
        this.minRes = Math.min(xRes, yRes);
        this.offsetRes = ((double) Math.max(xRes, yRes) - Math.min(xRes, yRes)) / 2.0;

        this.threshold = threshold;

        this.histogram = Histogram.createDefault(xRes, yRes);
    }

    private ComplexPoint mapToPoint(int x, int y) {
        double pointX;
        double pointY;
        if (xResLowerYRes) {
            pointX = P_MAX + ((P_MAX - P_MIN) * ((double) x - minRes)) / (double) minRes;
            pointY = P_MAX + ((P_MAX - P_MIN) * ((double) y - minRes - offsetRes)) / (double) minRes;
        } else {
            pointX = P_MAX + ((P_MAX - P_MIN) * ((double) x - minRes - offsetRes)) / (double) minRes;
            pointY = P_MAX + ((P_MAX - P_MIN) * ((double) y - minRes)) / (double) minRes;
        }

        return new ComplexPoint(pointX, pointY);
    }

    public void proceedSamples() {
        for (int j = 0; j < yRes; j++) {
            for (int i = 0; i < xRes; i++) {
                // we not simply put (i, j) as (x, y) correspondingly
                // we apply some transformations so i=0, j=0 will be left right corner of complex plane
                final ComplexPoint complexPoint = mapToPoint(i, yRes - j);
                MandelbrotIterator iterator = new MandelbrotIterator(complexPoint);

                ComplexPoint z = iterator.next();

                int it = 0;
                for (; it < threshold && z.moduleSquared() < BAIL_OUT; it++) {
                    z = iterator.next();
                }

                if (z.moduleSquared() < 2) {
                    histogram.setColorAt(i, j, Color.BLACK);
                } else {
                    histogram.setColorAt(i, j, Color.WHITE);
                }
            }
        }
    }
}
