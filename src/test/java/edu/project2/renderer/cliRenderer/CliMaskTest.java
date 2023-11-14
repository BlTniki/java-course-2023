package edu.project2.renderer.cliRenderer;

import edu.project2.Cell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class CliMaskTest {
    private final static CellCliTexturePack texturePack = CellCliTexturePack.BASIC;
    private final static CliMask mask = new CliMask(texturePack);

    static Arguments[] wallCells() {
        return new Arguments[] {
            Arguments.of(null, null, null, null, texturePack.getWallTextureByNumber(0b0000)),
            Arguments.of(
                new Cell(0, 0, Cell.Type.PASSAGE),
                new Cell(0, 0, Cell.Type.PASSAGE),
                new Cell(0, 0, Cell.Type.PASSAGE),
                new Cell(0, 0, Cell.Type.PASSAGE),
                texturePack.getWallTextureByNumber(0b0000)
            ),
            Arguments.of(
                new Cell(0, 0, Cell.Type.WALL),
                new Cell(0, 0, Cell.Type.PASSAGE),
                null,
                new Cell(0, 0, Cell.Type.WALL),
                texturePack.getWallTextureByNumber(0b1001)
            ),
            Arguments.of(
                new Cell(0, 0, Cell.Type.WALL),
                new Cell(0, 0, Cell.Type.WALL),
                new Cell(0, 0, Cell.Type.WALL),
                new Cell(0, 0, Cell.Type.WALL),
                texturePack.getWallTextureByNumber(0b1111)
            )
        };
    }

    @ParameterizedTest
    @MethodSource("wallCells")
    @DisplayName("Проверим определение стен")
    void determineWallTexture(Cell left, Cell up, Cell right, Cell down, char expectedTexture) {
        // when
        char actualTexture = mask.determineWallTexture(left, up, right, down);

        // then
        assertThat(actualTexture).isEqualTo(expectedTexture);
    }

    @Test
    @DisplayName("Проверка получения текстуры прохода")
    void getPassageTexture() {
        // when
        char actualTexture = mask.getPassageTexture();

        // then
        assertThat(actualTexture).isEqualTo(texturePack.getPassageTexture());
    }

    @Test
    @DisplayName("Проверка получения текстуры пути")
    void getHighlightedPathTexture() {
        // when
        char actualTexture = mask.getHighlightedPathTexture();

        // then
        assertThat(actualTexture).isEqualTo(texturePack.getHighlightedPathTexture());
    }
}
