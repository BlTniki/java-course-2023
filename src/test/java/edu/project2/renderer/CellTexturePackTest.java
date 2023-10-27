package edu.project2.renderer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CellTexturePackTest {
    @Test
    @DisplayName("Проверяем корректную выдачу текстур")
    void getTexture() {
        // given
        int wallNumber = 0b0110;
        char expectedWall = '└';
        char expectedPassage = ' ';
        CellTexturePack texturePack = CellTexturePack.BASIC;

        // when
        char actualWall = texturePack.getWallTextureByNumber(wallNumber);
        char actualPassage = texturePack.getPassageTexture();

        // then
        assertThat(actualWall).isEqualTo(expectedWall);
        assertThat(actualPassage).isEqualTo(expectedPassage);
    }
}
