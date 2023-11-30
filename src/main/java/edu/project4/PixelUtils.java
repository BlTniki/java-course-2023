package edu.project4;

import edu.project4.fractalFrame.HistoPoint;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;

public final class PixelUtils {
    public static final int MAX_COLOR_VAL = 255;

    private PixelUtils() {
    }

    /**
     * Converts histogram into color array applying gamma correction.
     * @param histoPoints histogram
     * @param doGammaCor whether to apply gamma correction
     * @param gammaIncrease increase or decrease average gamma
     * @return color array
     */
    public static @NotNull Color[][] histToPixels(
            @NotNull HistoPoint[][] histoPoints,
            boolean doGammaCor,
            double gammaIncrease
        ) {
        final Color[][] colors = new Color[histoPoints.length][histoPoints[0].length];

        // find log of maximum histoPoints hit counter
        int maxHit = 0;
        for (HistoPoint[] line : histoPoints) {
            for (HistoPoint histoPoint: line) {
                if (histoPoint.counter > maxHit) {
                    maxHit = histoPoint.counter;
                }
            }
        }
        double maxHitLog = Math.log(maxHit);

        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[0].length; j++) {
                final HistoPoint histoPoint = histoPoints[i][j];

                if (doGammaCor && histoPoint.counter > 0) {
                    // if hit the point do log gamma correction
                    double gammaFactor = Math.log(histoPoint.counter) / maxHitLog;
                    gammaFactor += gammaFactor * gammaIncrease;
                    colors[i][j] = new Color(
                        Math.min((int) Math.round(gammaFactor * histoPoint.color.getRed()), MAX_COLOR_VAL),
                        Math.min((int) Math.round(gammaFactor * histoPoint.color.getGreen()), MAX_COLOR_VAL),
                        Math.min((int) Math.round(gammaFactor * histoPoint.color.getBlue()), MAX_COLOR_VAL)
                    );
                } else {
                    // else just copy color
                    colors[i][j] = histoPoint.color;
                }
            }
        }

        return colors;
    }
}
