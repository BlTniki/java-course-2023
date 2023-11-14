package edu.project2.solver;

import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Solver {
    /**
     * Returns path (list of coordinates) from start point to the end.
     * @param maze where find path
     * @param start from point
     * @param end to point
     * @return path (list of coordinates). Empty list if path don't exist
     */
    @NotNull List<Coordinate> solve(
        @NotNull Maze maze,
        @NotNull Coordinate start,
        @NotNull Coordinate end
    );
}
