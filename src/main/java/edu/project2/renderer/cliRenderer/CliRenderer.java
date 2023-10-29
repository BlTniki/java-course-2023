package edu.project2.renderer.cliRenderer;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.renderer.Renderer;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class CliRenderer implements Renderer {
    private final CliMask mask;

    public CliRenderer(CliMask mask) {
        this.mask = mask;
    }

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
    public String render(final @NotNull Maze maze) {
        final char[][] field = renderCharArray(maze);

        // convert char array to string
        StringBuilder result = new StringBuilder();
        for (char[] row : field) {
            result.append(row);
            result.append('\n'); // Add newline character after each row
        }

        return result.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        return null;
    }
}
