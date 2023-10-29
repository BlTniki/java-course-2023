package edu.project2.generator;

import edu.project2.Cell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class GeneratorUtilsTest {
    @Test
    @DisplayName("Проверим что создаётся правильная сетка")
    void initGrid() {
        // given
        int height = 4;
        int width = 4;
        Cell[][] expectedGrid = new Cell[][] {
            {new Cell(0,0, Cell.Type.WALL), new Cell(0,1, Cell.Type.PASSAGE), new Cell(0,2, Cell.Type.WALL), new Cell(0,3, Cell.Type.WALL)},
            {new Cell(1,0, Cell.Type.WALL), new Cell(1,1, Cell.Type.PASSAGE), new Cell(1,2, Cell.Type.PASSAGE), new Cell(1,3, Cell.Type.WALL)},
            {new Cell(2,0, Cell.Type.WALL), new Cell(2,1, Cell.Type.PASSAGE), new Cell(2,2, Cell.Type.PASSAGE), new Cell(2,3, Cell.Type.WALL)},
            {new Cell(3,0, Cell.Type.WALL), new Cell(3,1, Cell.Type.WALL), new Cell(3,2, Cell.Type.PASSAGE), new Cell(3,3, Cell.Type.WALL)}
        };

        // when
        Cell[][] actualGrid = GeneratorUtils.initGrid(height, width);

        // then
        assertThat(actualGrid).isDeepEqualTo(expectedGrid);
    }
}
