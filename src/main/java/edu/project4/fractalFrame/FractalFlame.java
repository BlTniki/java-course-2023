package edu.project4.fractalFrame;

import edu.project4.ImageCreator;
import edu.project4.transformer.Point;
import edu.project4.transformer.flameFunction.FlameFunction;
import edu.project4.transformer.flameFunction.FlameFunctionsHandler;
import edu.project4.transformer.flameFunction.FlameFunctionsUtils;
import edu.project4.transformer.variation.VariationType;
import java.awt.Color;
import java.util.Random;

public class FractalFlame {
    private final static Random RAND = new Random();
    private final static double X_MIN = -1;
    private final static double X_MAX = 1;
    private final static double Y_MIN = -1;
    private final static double Y_MAX = 1;

    private static double randBetween(double min, double max) {
        return min + (max - min) * RAND.nextDouble();
    }

    static Pixel[][] generate(int xRes, int yRes, int samples, int it) {
        final FlameFunctionsHandler handler = FlameFunctionsUtils.buildHandler(VariationType.values());

        final Pixel[][] pixels = new Pixel[xRes][yRes];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                pixels[i][j] = new Pixel();
            }
        }

        for (int sample = 0; sample < samples; sample++) {
            Point newPoint = new Point(randBetween(X_MIN, X_MAX), randBetween(Y_MIN, Y_MAX));
            Color color = new Color(RAND.nextFloat(), RAND.nextFloat(), RAND.nextFloat());

            for (int i = -20; i < it; i++) {
                final FlameFunction flameFunction = handler.getRand(RAND);

                newPoint = flameFunction.apply(newPoint);
                color = flameFunction.blend(color);

                Point pixelPoint = new Point(
                    Math.round(((X_MAX - newPoint.x()) / (X_MAX - X_MIN)) * xRes),
                    Math.round(((Y_MAX - newPoint.y()) / (Y_MAX - Y_MIN)) * yRes)
                );

                if (i >= 0 && pixelPoint.inRange(0, xRes, 0, yRes)) {
                    Pixel curPixel = pixels[(int) pixelPoint.x()][(int) pixelPoint.y()];

                    curPixel.increaseCounter();
                    curPixel.color = color;
                }
            }
        }

        return pixels;
    }

    public static void main(String[] args) {
        int xRes = 2000;
        int yRes = 2000;
        int samples = 40_000; // int samples = 40000; 100_000
        int it = 10000; // int it = 25000; 2000
        Pixel[][] pixels = generate(xRes, yRes, samples, it);
        ImageCreator.createImage(pixels, "fire.png");
    }
}
