package edu.project2.solver;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class BreadthFirstSearchSolver implements Solver {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private List<Coordinate> doSearch(Maze maze, Cell start, Cell end) {
        final Set<Cell> visitedCells = new HashSet<>();
        final Queue<Cell> toCheck = new LinkedList<>();
        final Map<Cell, Cell> cellParentMap = new HashMap<>();

        visitedCells.add(start);
        toCheck.add(start);
        cellParentMap.put(start, null);
        boolean foundedEnd = false;

        while (!toCheck.isEmpty() && !foundedEnd) {
            final Cell curCell = toCheck.poll();

            for (var dir : DIRECTIONS) {
                final Cell neighbor = maze.getCellAt(
                    curCell.row() + dir[0],
                    curCell.col() + dir[1]
                );
                if (neighbor != null && neighbor.type().equals(Cell.Type.PASSAGE) && !visitedCells.contains(neighbor)) {
                    visitedCells.add(neighbor);
                    toCheck.add(neighbor);
                    cellParentMap.put(neighbor, curCell);
                    if (neighbor.equals(end)) {
                        foundedEnd = true;
                        break;
                    }
                }
            }
        }

        if (!foundedEnd) {
            return List.of();
        }

        return buildPath(cellParentMap, end);
    }

    private List<Coordinate> buildPath(Map<Cell, Cell> cellParentMap, Cell cell) {
        final ArrayList<Coordinate> path = new ArrayList<>();
        path.add(Coordinate.of(cell));
        Cell parent = cellParentMap.get(cell);
        while (parent != null) {
            path.add(Coordinate.of(parent));
            parent = cellParentMap.get(parent);
        }
        return path;
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
            return List.of();
        }

        return doSearch(maze, startCell, endCell);
    }
}
