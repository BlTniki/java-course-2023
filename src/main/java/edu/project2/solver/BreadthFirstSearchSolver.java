package edu.project2.solver;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class BreadthFirstSearchSolver implements Solver {
    private List<Coordinate> doSearch(Maze maze, Cell start, Cell end) {
//        final Set<Cell> visitedCells = new HashSet<>();
//        final Set<Cell> edgeCells = new HashSet<>();
//        final Set<Cell>  cellsToCheck = new HashSet<>();
//        edgeCells.add(start);
//        boolean foundEnd = false;
//
//        while (!foundEnd) {
//            for (Cell edgeCell : edgeCells) {
//                final Cell left = maze.getCellAt()
//            }
//        }
    }

    @Override
    public @NotNull List<Coordinate> solve(
        @NotNull Maze maze,
        @NotNull Coordinate start,
        @NotNull Coordinate end
    ) {
        final Cell startCell = maze.getCellAt(start.row(), start.col());
        final Cell endCell = maze.getCellAt(end.row(), end.col());

        if (startCell == null || endCell == null
            || startCell.type().equals(Cell.Type.WALL) || endCell.type().equals(Cell.Type.WALL)) {
            throw new IllegalArgumentException()
        }
    }
}
