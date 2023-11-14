package edu.project2.renderer.cliRenderer;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.renderer.Renderer;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class CliRenderer implements Renderer {
    private final CliMask mask;

    public CliRenderer(@NotNull CliMask mask) {
        this.mask = mask;
    }

    /**
     * Converts given field to a single string.
     * @param field to convert
     * @return single string
     */
    private static @NotNull String convertFieldToString(char[][] field) {
        StringBuilder result = new StringBuilder();
        for (char[] row : field) {
            result.append(row);
            result.append('\n'); // Add newline character after each row
        }
        return result.toString();
    }

    /**
     * Returns char 2d array of maze string representation
     * @param maze maze to render
     * @return char 2d array
     */
    private char[][] renderCharArray(final @NotNull Maze maze) {
        final char[][] field = new char[maze.getHeight()][maze.getWidth()];

        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                final Cell curCell = maze.getCellAt(i, j);

                if (curCell == null) {
                    throw new NullPointerException("Ain't expect null at that point(%d, %d). ".formatted(i, j)
                        + "Something really went wrong");
                }

                if (curCell.type().equals(Cell.Type.PASSAGE)) {
                    field[i][j] = mask.getPassageTexture();
                    continue;
                }

                // checkout cell neighbors to determine wall texture
                final Cell leftCell = maze.getCellAt(i, j - 1);
                final Cell upCell = maze.getCellAt(i - 1, j);
                final Cell rightCell = maze.getCellAt(i, j + 1);
                final Cell downCell = maze.getCellAt(i + 1, j);
                field[i][j] = mask.determineWallTexture(leftCell, upCell, rightCell, downCell);
            }
        }

        return field;
    }

    @Override
    public @NotNull String render(final @NotNull Maze maze) {
        final char[][] field = renderCharArray(maze);

        return convertFieldToString(field);
    }

    @Override
    public @NotNull String render(@NotNull Maze maze, @NotNull List<Coordinate> path) {
        final char[][] field = renderCharArray(maze);

        // highlight path
        for (var coordinate : path) {
            if (coordinate.row() < 0 || coordinate.row() >= field.length
                || coordinate.col() < 0 || coordinate.col() >= field[0].length) {
                throw new IllegalArgumentException("Given path is invalid!");
            }
            field[coordinate.row()][coordinate.col()] = mask.getHighlightedPathTexture();
        }

        return convertFieldToString(field);
    }
}
