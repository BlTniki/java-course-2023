package edu.hw9.task3;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.renderer.cliRenderer.CliRendererManager;
import edu.project2.solver.Solver;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class BfsParallelSolverTest {

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
        Solver solver = new BfsParallelSolver();

        // when
        final List<Coordinate> actualPath = solver.solve(maze, start, end);

        System.out.println(new CliRendererManager().getRenderer().render(maze, actualPath));

        // then
        assertThat(actualPath)
            .contains(new Coordinate(1, 1), new Coordinate(3, 8));
    }
}
