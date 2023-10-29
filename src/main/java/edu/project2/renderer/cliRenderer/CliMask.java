package edu.project2.renderer.cliRenderer;

import edu.project2.Cell;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class exists to determine the texture of a cell,
 * based on its neighbors.
 */
public class CliMask {
    private final static int LEFT_BIT = 8;
    private final static int UP_BIT = 4;
    private final static int RIGHT_BIT = 2;
    private final static int DOWN_BIT = 1;

    private final CellCliTexturePack texturePack;

    public CliMask(@NotNull CellCliTexturePack texturePack) {
        this.texturePack = texturePack;
    }

    /**
     * Returns the texture of the wall cell based on neighboring cells.
     * The reference to the neighbors can be null. This is interpreted as the absence of a wall in this cell.
     * @param left neighbor on the left
     * @param up top neighbor
     * @param right neighbor on the right
     * @param down neighbor from below
     * @return wall texture
     */
    public char determineWallTexture(
                                    @Nullable Cell left,
                                    @Nullable Cell up,
                                    @Nullable Cell right,
                                    @Nullable Cell down) {
        int wallNumber = 0;

        if (left != null && left.type().equals(Cell.Type.WALL)) {
            wallNumber += LEFT_BIT;
        }
        if (up != null && up.type().equals(Cell.Type.WALL)) {
            wallNumber += UP_BIT;
        }
        if (right != null && right.type().equals(Cell.Type.WALL)) {
            wallNumber += RIGHT_BIT;
        }
        if (down != null && down.type().equals(Cell.Type.WALL)) {
            wallNumber += DOWN_BIT;
        }

        return texturePack.getWallTextureByNumber(wallNumber);
    }

    /**
     * Returns passage texture from texture pack.
     * @return passage texture
     */
    public char getPassageTexture() {
        return texturePack.getPassageTexture();
    }
}
