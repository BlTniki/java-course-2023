package edu.hw9.task3;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.solver.Solver;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.NotNull;

public class BfsParallelSolver implements Solver {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private List<Coordinate> doSearch(Maze maze, Cell start, Cell end) {
        final ConcurrentLinkedDeque<Cell> visitedCells = new ConcurrentLinkedDeque<>();
        final ConcurrentLinkedQueue<Cell> toCheck = new ConcurrentLinkedQueue<>();
        final ConcurrentHashMap<Cell, Cell> cellParentMap = new ConcurrentHashMap<>();

        visitedCells.add(start);
        toCheck.add(start);
        cellParentMap.put(start, start);
        AtomicBoolean foundedEnd = new AtomicBoolean(false);

        try (ExecutorService executorService =
                 Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            while (visitedCells.size() < maze.getHeight() * maze.getWidth() && !foundedEnd.get()) {
                final Cell curCell = toCheck.poll();

                if (curCell == null) {
                    continue;
                }

                executorService.submit(() -> {
                    for (var dir : DIRECTIONS) {
                        final Cell neighbor = maze.getCellAt(
                            curCell.row() + dir[0],
                            curCell.col() + dir[1]
                        );
                        if (neighbor != null
                                && neighbor.type().equals(Cell.Type.PASSAGE)
                                    && !visitedCells.contains(neighbor)) {
                            visitedCells.add(neighbor);
                            toCheck.add(neighbor);
                            cellParentMap.put(neighbor, curCell);
                            if (neighbor.equals(end)) {
                                foundedEnd.set(true);
                                break;
                            }
                        }
                    }
                });
            }
        }

        if (!foundedEnd.get()) {
            return List.of();
        }

        return buildPath(cellParentMap, start, end);
    }

    private List<Coordinate> buildPath(Map<Cell, Cell> cellParentMap, Cell dest, Cell cell) {
        final ArrayList<Coordinate> path = new ArrayList<>();
        path.add(Coordinate.of(cell));
        Cell parent = cellParentMap.get(cell);
        while (parent != dest) {
            path.add(Coordinate.of(parent));
            parent = cellParentMap.get(parent);
        }
        path.add(Coordinate.of(dest));
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
