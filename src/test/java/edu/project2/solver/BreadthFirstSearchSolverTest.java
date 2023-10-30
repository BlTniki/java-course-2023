package edu.project2.solver;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class BreadthFirstSearchSolverTest {

    @Test
    void solve() {
        // given
        final int height = 5;
        final int width = 10;
        final Cell[][] grid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || j == 0 || i == height - 1 || j == width - 1) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                }
            }
        }
        final Maze maze = new Maze(height, width, grid);
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 8);
        final List<Coordinate> expectedPath = List.of(
            new Coordinate(1, 1),
            new Coordinate(2, 1),
            new Coordinate(3, 1),
            new Coordinate(3, 2),
            new Coordinate(3, 3),
            new Coordinate(3, 4),
            new Coordinate(3, 5),
            new Coordinate(3, 6),
            new Coordinate(3, 7),
            new Coordinate(3, 8)
        );
        Solver solver = new BreadthFirstSearchSolver();

        // when
        final List<Coordinate> actualPath = solver.solve(maze, start, end);

        // then
        assertThat(actualPath)
            .containsExactlyInAnyOrderElementsOf(expectedPath);
    }
}
