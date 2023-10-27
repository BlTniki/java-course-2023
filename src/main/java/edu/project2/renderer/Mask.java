package edu.project2.renderer;

import edu.project2.Cell;
import org.jetbrains.annotations.Nullable;

/**
 * This class exists to determine the texture of a cell,
 * based on its neighbors.
 */
public class Mask {
    private final CellTexturePack texturePack;

    public Mask(CellTexturePack texturePack) {
        this.texturePack = texturePack;
    }

    public char determineTexture(@Nullable Cell left, @Nullable Cell up, @Nullable Cell right, @Nullable Cell down) {
//        int resultNumber =
        return ' ';
    }
}
