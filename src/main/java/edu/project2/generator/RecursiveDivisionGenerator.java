package edu.project2.generator;

import edu.project2.Cell;
import edu.project2.Maze;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;
import static edu.project2.generator.GeneratorUtils.initGrid;

public class RecursiveDivisionGenerator implements Generator {
    private final Random random;

    public RecursiveDivisionGenerator(Random random) {
        this.random = random;
    }

    /**
     * Return good row index to cut.
     * @param grid maze grid =)
     * @param workArea area to check
     * @return row to cut or -1 if there is no good row
     */
    private int pickRowToCut(Cell[][] grid, WorkArea workArea) {
        // find all rows to cut index that does not contact with passage on work area edges
        List<Integer> matchingRows = IntStream.range(workArea.startCell.row() + 1, workArea.endCell.row())
            .filter(i ->
                grid[i][workArea.startCell.col() - 1].type().equals(Cell.Type.WALL)
                    && grid[i][workArea.endCell.col() + 1].type().equals(Cell.Type.WALL)
            ).boxed().toList();

        if (matchingRows.isEmpty()) {
            // end of recursion
            return -1;
        }

        // pick one
        return matchingRows.get(random.nextInt(matchingRows.size()));
    }

    /**
     * Return good col index to cut.
     * @param grid maze grid =)
     * @param workArea area to check
     * @return col to cut or -1 if there is no good col
     */
    private int pickColToCut(Cell[][] grid, WorkArea workArea) {
        // find all cols to cut index that does not contact with passage on work area edges
        List<Integer> matchingCols = IntStream.range(workArea.startCell.col() + 1, workArea.endCell.col())
            .filter(i ->
                grid[workArea.startCell.row() - 1][i].type().equals(Cell.Type.WALL)
                    && grid[workArea.endCell.row() + 1][i].type().equals(Cell.Type.WALL)
            ).boxed().toList();

        if (matchingCols.isEmpty()) {
            // end of recursion
            return -1;
        }

        // pick one
        return matchingCols.get(random.nextInt(matchingCols.size()));
    }

    private void fillRowWithWalls(WorkArea workArea, Cell[][] grid, int cutRow) {
        // define row to not fill
        int notFillIndex = random.nextInt(workArea.startCell.col(), workArea.endCell.col() + 1);

        // fill the row with walls leaving one passage
        for (int i = workArea.startCell.col(); i <= workArea.endCell.col(); i++) {
            Cell.Type type = Cell.Type.WALL;
            if (i == notFillIndex) {
                type = Cell.Type.PASSAGE;
            }
            grid[cutRow][i] = grid[cutRow][i].changeType(type);
        }
    }

    private void fillColWithWalls(Cell[][] grid, WorkArea workArea, int cutCol) {
        // define col to not fill
        int notFillIndex = random.nextInt(workArea.startCell.row(), workArea.endCell.row() + 1);

        // fill the col with walls leaving one passage
        for (int i = workArea.startCell.row(); i <= workArea.endCell.row(); i++) {
            Cell.Type type = Cell.Type.WALL;
            if (i == notFillIndex) {
                type = Cell.Type.PASSAGE;
            }
            grid[i][cutCol] = grid[i][cutCol].changeType(type);
        }
    }

    /**
     * Implements Recursive division of the maze.
     * @param grid maze cell grid. Must not contain grid edge rows and columns
     * @param workArea were alg should generate maze
     */
    private void doDivision(Cell[][] grid, WorkArea workArea) {
        // pick vertical or horizontal division randomly
        // the longer one side of the other, the more likely we are to divide it
        boolean divideHorizontal = random
            .nextInt(0, workArea.getHeight() + workArea.getWidth()) + 1 <= workArea.getHeight();

        // declare new work areas
        WorkArea leftArea;
        WorkArea rightArea;

        int cutRow = pickRowToCut(grid, workArea);
        int cutCol = pickColToCut(grid, workArea);

        // if we cant cut any side
        if (cutRow == -1 && cutCol == -1) {
            return;
        }

        if (divideHorizontal) {
            // in this case our main goal to cut Horizontal
            // but if we cant, then cut Vertical
            if (cutRow >= 0) {
                fillRowWithWalls(workArea, grid, cutRow);

                leftArea = new WorkArea(workArea.startCell, grid[cutRow - 1][workArea.endCell.col()]);
                rightArea = new WorkArea(grid[cutRow + 1][workArea.startCell.col()], workArea.endCell);
            } else {
                fillColWithWalls(grid, workArea, cutCol);

                leftArea = new WorkArea(workArea.startCell, grid[workArea.endCell.row()][cutCol - 1]);
                rightArea = new WorkArea(grid[workArea.startCell.row()][cutCol + 1], workArea.endCell);
            }
        } else {
            if (cutCol >= 0) {
                fillColWithWalls(grid, workArea, cutCol);

                leftArea = new WorkArea(workArea.startCell, grid[workArea.endCell.row()][cutCol - 1]);
                rightArea = new WorkArea(grid[workArea.startCell.row()][cutCol + 1], workArea.endCell);
            } else {
                fillRowWithWalls(workArea, grid, cutRow);

                leftArea = new WorkArea(workArea.startCell, grid[cutRow - 1][workArea.endCell.col()]);
                rightArea = new WorkArea(grid[cutRow + 1][workArea.startCell.col()], workArea.endCell);
            }
        }

        // and finally recursion
        doDivision(grid, leftArea);
        doDivision(grid, rightArea);
    }

    @Override
    public @NotNull Maze generate(final int height, final int width) {
        Cell[][] grid = initGrid(height, width);

        // Using work area that inside grid edges, so we will not touch the outer walls
        final WorkArea workArea = new WorkArea(
            grid[1][1],
            grid[height - 2][width - 2]
        );
        doDivision(grid, workArea);

        return new Maze(height, width, grid);
    }

    /**
     * Record that holds up left corner cell of work area and down right corner cell of work area
     * @param startCell up left corner cell
     * @param endCell down right corner cell
     */
    private record WorkArea(@NotNull Cell startCell, @NotNull Cell endCell) {
        public int getHeight() {
            return endCell.row() - startCell.row() + 1;
        }

        public int getWidth() {
            return endCell.col() - startCell.col() + 1;
        }
    }
}
