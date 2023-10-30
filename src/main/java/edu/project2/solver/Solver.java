package edu.project2.solver;

import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Solver {
    @NotNull List<Coordinate> solve(
        @NotNull Maze maze,
        @NotNull Coordinate start,
        @NotNull Coordinate end
    );
}
