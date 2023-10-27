package edu.project2.renderer;

import edu.project2.Cell;
import edu.project2.Maze;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RendererImplTest {
    private Renderer renderer;

    @BeforeEach
    void init() {
        final CellTexturePack texturePack = CellTexturePack.BASIC;
        final Mask mask = new Mask(texturePack);
        this.renderer = new RendererImpl(mask);
    }

    @Test
    void render_random_maze() {
        // given
        final int height = 500;
        final int width = 500;
        final Cell[][] grid = new Cell[height][width];
        final Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (random.nextBoolean()) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                }
            }
        }
        final Maze maze = new Maze(height, width, grid);

        String result = renderer.render(maze);

        System.out.println(result);
    }

    @Test
    void testRender() {
    }
}
