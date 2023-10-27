package edu.project2;

import org.jetbrains.annotations.Nullable;

public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
    }

    public Maze(int height, int width, Cell[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Return cell at given coordinates if cell exist.
     * @param row cell row in maze
     * @param col cell column in maze
     * @return cell if exist or null
     */
    public @Nullable Cell getCellAt(int row, int col) {
        if (row < 0 || row >= height
            || col < 0 || col >= width) {
            return null;
        }

        return grid[row][col];
    }
}
