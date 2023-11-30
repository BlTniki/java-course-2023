package edu.project4.histogram;

import java.awt.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * Stores 2d HistoPoint array and provides concurrent safe write.
 */
public class Histogram {
    public final HistoPoint[][] histoPoints;

    public Histogram(@NotNull HistoPoint[][] histoPoints) {
        this.histoPoints = histoPoints;
    }

    public static @NotNull Histogram createDefault(
            @Range(from = 0, to = Integer.MAX_VALUE) int xRes,
            @Range(from = 0, to = Integer.MAX_VALUE) int yRes
    ) {
        HistoPoint[][] histoPoints = new HistoPoint[xRes][yRes];
        for (int i = 0; i < histoPoints.length; i++) {
            for (int j = 0; j < histoPoints[0].length; j++) {
                histoPoints[i][j] = new HistoPoint();
            }
        }

        return new Histogram(histoPoints);
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public void increaseCounterAt(int x, int y) {
        final HistoPoint curHistoPoint = histoPoints[x][y];

        synchronized (curHistoPoint) {
            curHistoPoint.increaseCounter();
        }
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public void changeColorAt(int x, int y, @NotNull Color color) {
        final HistoPoint curHistoPoint = histoPoints[x][y];

        synchronized (curHistoPoint) {
            curHistoPoint.color = color;
        }
    }
}
