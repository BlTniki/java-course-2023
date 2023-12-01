package edu.project4;

import edu.project4.fractalFrame.FractalFlame;
import edu.project4.histogram.Histogram;
import edu.project4.transformer.variation.VariationType;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.SwingUtilities;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) throws InterruptedException {
        final int xRes = 2000;
        final int yRes = 2000;
        final int samples = 200_000; // int samples = 40000; 100_000
        final int coresAvailable = Runtime.getRuntime().availableProcessors();
        final int samplesPerTask = (int) Math.round((double) samples / coresAvailable);
        final int it = 5000;

        FractalFlame fractalFlame = new FractalFlame(
            xRes,
            yRes,
            it,
            VariationType.SPHERICAL, VariationType.DISK
        );

        Histogram histogram = fractalFlame.histogram;

        SwingUtilities.invokeLater(() -> new CanvasExample(
            histogram.histoPoints.length,
            histogram.histoPoints[0].length,
            histogram.histoPoints
        ));

        final long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(coresAvailable);
        for (int i = 0; i < coresAvailable; i++) {
            executorService.submit(() -> {
                fractalFlame.proceedSamples(samplesPerTask, ThreadLocalRandom.current());
            });
        }

        executorService.close();

        System.out.println(System.currentTimeMillis() - start);



        ImageCreator.createImage(PixelUtils.histToPixels(histogram, true, 1.5), Path.of("fire1_5.png"));
        ImageCreator.createImage(PixelUtils.histToPixels(histogram, true, 2), Path.of("fire2.png"));
        ImageCreator.createImage(PixelUtils.histToPixels(histogram, true, 1), Path.of("fire1.png"));
    }
}
