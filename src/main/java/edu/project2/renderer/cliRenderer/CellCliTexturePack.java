package edu.project2.renderer.cliRenderer;

import org.jetbrains.annotations.Range;

/**
 * Dictionary of wall pack textures.
 * Textures inside the pack are arranged so that their indexes are equal to their number.
 * The texture number is 4 binary flags telling if there are more walls next to the wall.
 * For example, a wall that has walls on the left and bottom (┐) will have such a number in binary:
 * (L U R D)
 * 1 0 0 1
 * Which in decimal is 9.
 * PENULTIMATE CHARACTER MUST REPRESENT HIGHLIGHTED PATH
 * LAST CHARACTER MUST REPRESENT PASSAGE.
 */
public enum CellCliTexturePack {
    BASIC("·╷╶┌╵│└├╴┐─┬┘┤┴┼. ");

    private final String texturePack;

    CellCliTexturePack(String texturePack) {
        final int texturePackSize = 18;

        if (texturePack == null || texturePack.length() != texturePackSize) {
            throw new IllegalArgumentException("Bad texture pack");
        }

        this.texturePack = texturePack;
    }

    public char getWallTextureByNumber(@Range(from = 0, to = 15) int number) {
        return texturePack.charAt(number);
    }

    public char getHighlightedPathTexture() {
        return texturePack.charAt(texturePack.length() - 2);
    }

    public char getPassageTexture() {
        return texturePack.charAt(texturePack.length() - 1);
    }
}
