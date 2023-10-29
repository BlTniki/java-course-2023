package edu.project2.generator;

import edu.project2.Cell;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class GeneratorUtils {
    private GeneratorUtils() {
    }

    private final static int MIN_MAZE_HEIGHT = 3;
    private final static int MIN_MAZE_WIDTH = 3;

    /**
     * Initialization of 2d cells gird. The cells on the edges of the grid are walls, the rest are passages
     * @param height rows count
     * @param width columns count
     * @return 2d cells gird
     */
    public static @NotNull Cell[][] initGrid(
            @Range(from = MIN_MAZE_HEIGHT, to = Integer.MAX_VALUE) int height,
            @Range(from = MIN_MAZE_WIDTH, to = Integer.MAX_VALUE) int width) {
        final Cell[][] grid = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i == 0 && j == 1) || (i == height - 1 && j == width - 2)) {
                    // add passage on the maze top and bottom
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                } else if (i == 0 || i == height - 1
                    // add walls on the maze edges
                    || j == 0 || j == width - 1) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                }
            }
        }

        return grid;
    }
}
