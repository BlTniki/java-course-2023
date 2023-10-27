package edu.project2;

import java.util.Optional;

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

    /**
     * Return cell at given coordinates if cell exist.
     * @param coordinate cell coordinates in maze
     * @return cell if exist
     */
    public Optional<Cell> getCellAt(Coordinate coordinate) {
        if (coordinate == null) {
            return Optional.empty();
        }

        if (coordinate.row() < 0 || coordinate.row() >= height
            || coordinate.col() < 0 || coordinate.row() >= width) {
            return Optional.empty();
        }

        return Optional.of(grid[coordinate.row()][coordinate.col()]);
    }
}
