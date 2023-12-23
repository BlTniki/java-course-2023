package edu.project4.mandelbrot;

import edu.project4.CanvasExample;
import edu.project4.histogram.HistoPoint;
import javax.swing.SwingUtilities;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        int xRes = 1000;
        int yRes = 1000;
        int threshold = 1000;

        Mandelbrot mandelbrot = new Mandelbrot(
            xRes,
            yRes,
            threshold
        );

        HistoPoint[][] histoPoints = mandelbrot.histogram.histoPoints;
        SwingUtilities.invokeLater(() -> new CanvasExample(histoPoints.length, histoPoints[0].length, histoPoints));

        mandelbrot.proceedSamples();
    }
}
