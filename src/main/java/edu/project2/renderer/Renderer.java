package edu.project2.renderer;

import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface Renderer {
    @NotNull String render(@NotNull Maze maze);

    @NotNull String render(@NotNull Maze maze, @NotNull List<Coordinate> path);
}
