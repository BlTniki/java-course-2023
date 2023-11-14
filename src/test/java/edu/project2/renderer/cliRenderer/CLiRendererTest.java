package edu.project2.renderer.cliRenderer;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.renderer.Renderer;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CLiRendererTest {
    private Renderer renderer;

    @BeforeEach
    void init() {
        final CellCliTexturePack texturePack = CellCliTexturePack.BASIC;
        final CliMask mask = new CliMask(texturePack);
        this.renderer = new CliRenderer(mask);
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
        // given
        final int height = 5;
        final int width = 10;
        final Cell[][] grid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || j == 0 || i == 2 || j == 4 || i == height - 1 || j == width - 1) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                }
            }
        }
        final Maze maze = new Maze(height, width, grid);
        String expectedResult = """
            ┌───┬────┐
            │   │    │
            ├───┼────┤
            │   │    │
            └───┴────┘
            """;

        // when
        String actualResult = renderer.render(maze);

        // then
        assertThat(actualResult)
            .isEqualTo(expectedResult);
    }

    @Test
    void testRenderWithPath() {
        // given
        final int height = 5;
        final int width = 10;
        final Cell[][] grid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || j == 0 || i == 2 || j == 4 || i == height - 1 || j == width - 1) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                }
            }
        }
        final Maze maze = new Maze(height, width, grid);
        final List<Coordinate> path = List.of(
            new Coordinate(1, 1),
            new Coordinate(1, 2),
            new Coordinate(1, 3),
            new Coordinate(1, 4),
            new Coordinate(1, 5),
            new Coordinate(1, 6),
            new Coordinate(1, 7),
            new Coordinate(1, 8),
            new Coordinate(2, 8),
            new Coordinate(3, 8)
        );
        String expectedResult = """
            ┌───┬────┐
            │▉▉▉▉▉▉▉▉│
            ├───┼───▉┤
            │   │   ▉│
            └───┴────┘
            """;

        // when
        String actualResult = renderer.render(maze, path);

        // then
        assertThat(actualResult)
            .isEqualTo(expectedResult);
    }
}
