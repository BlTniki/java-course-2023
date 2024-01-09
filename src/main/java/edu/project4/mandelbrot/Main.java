package edu.project4.mandelbrot;

import edu.project4.CanvasExample;
import edu.project4.histogram.HistoPoint;
import javax.swing.SwingUtilities;

public final class Main {

    public static final int CONST = 1000;

    private Main() {
    }

    public static void main(String[] args) {
        int xRes = CONST;
        int yRes = CONST;
        int threshold = CONST;

        Mandelbrot mandelbrot = new Mandelbrot(
            xRes,
            yRes,
            threshold
        );

        HistoPoint[][] histoPoints = mandelbrot.histogram.histoPoints;
        SwingUtilities.invokeLater(() -> new CanvasExample(histoPoints.length, histoPoints[0].length, histoPoints));

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        mandelbrot.proceedSamples();
    }
}
