package edu.project2.generator;

import edu.project2.Maze;
import org.jetbrains.annotations.NotNull;

public interface Generator {
    @NotNull Maze generate(int height, int width);
}
