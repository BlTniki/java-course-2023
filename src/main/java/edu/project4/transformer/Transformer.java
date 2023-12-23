package edu.project4.transformer;

import org.jetbrains.annotations.NotNull;

public interface Transformer {
    /**
     * Applying transformation to the point.
     * @param point 2d xy point on plane
     * @return new transformed point
     */
    @NotNull Point apply(@NotNull Point point);
}
