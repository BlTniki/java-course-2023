package edu.project2.renderer;

import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Renderer {
    /**
     * Render given maze into string representation
     * @param maze maze to render
     * @return string representation
     */
    @NotNull String render(@NotNull Maze maze);

    /**
     * Render given maze into string representation and highlight given path
     * @param maze maze to render
     * @return string representation with highlighted path
     */
    @NotNull String render(@NotNull Maze maze, @NotNull List<Coordinate> path);
}
