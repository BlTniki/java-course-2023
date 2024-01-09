package edu.project4.transformer.flameFunction;

import edu.project4.transformer.Affine;
import edu.project4.transformer.Point;
import edu.project4.transformer.Transformer;
import edu.project4.transformer.variation.WeightedVariation;
import java.awt.Color;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * FlameFunction that described in original article.
 */
public class FlameFunction implements Transformer {
    public final Affine affine;
    public final List<WeightedVariation> weightedVaris;
    public final Color color;

    /**
     * FlameFunction that described in original article.
     */
    public FlameFunction(Affine affine, List<WeightedVariation> weightedVaris, Color color) {
        this.affine = affine;
        this.weightedVaris = weightedVaris;
        this.color = color;
    }

    @Override
    public @NotNull Point apply(@NotNull Point point) {
        Point newPoint = new Point(0, 0);
        for (var wv: weightedVaris) {
            newPoint = newPoint.add(wv.apply(affine.apply(point)));
        }
        return newPoint;
    }

    /**
     * Blend two colors (this and given) by basically find average between each RGB component.
     * @param otherColor wich blend with FLameFunction color
     * @return new blended color
     */
    public @NotNull Color blend(@NotNull Color otherColor) {
        return new Color(
            (this.color.getRed() + otherColor.getRed()) / 2,
            (this.color.getGreen() + otherColor.getGreen()) / 2,
            (this.color.getBlue() + otherColor.getBlue()) / 2
        );
    }
}
