package edu.project4;

import edu.project4.fractalFrame.FractalFlame;
import edu.project4.fractalFrame.HistoPoint;
import edu.project4.transformer.variation.VariationType;
import java.nio.file.Path;
import java.util.Random;
import javax.swing.SwingUtilities;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) throws InterruptedException {
        int xRes = 2000;
        int yRes = 2000;
        int samples = 100_000; // int samples = 40000; 100_000
        int it = 5000; // int it = 25000; 2000

        FractalFlame fractalFlame = new FractalFlame(
            xRes,
            yRes,
            samples,
            it,
            new Random(),
            VariationType.DISK, VariationType.SPHERICAL
        );

        HistoPoint[][] histoPoints = fractalFlame.generate();

        SwingUtilities.invokeLater(() -> new CanvasExample(histoPoints.length, histoPoints[0].length, histoPoints));
        Thread.sleep(2000);

        while (fractalFlame.hasRemainSamples()) {
            fractalFlame.generate();
        }

        ImageCreator.createImage(PixelUtils.histToPixels(histoPoints, true, 0), Path.of("fire0.png"));
        ImageCreator.createImage(PixelUtils.histToPixels(histoPoints, true, 0.5), Path.of("fire05.png"));
        ImageCreator.createImage(PixelUtils.histToPixels(histoPoints, true, 1), Path.of("fire1.png"));
    }
}
