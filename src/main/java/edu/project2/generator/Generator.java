package edu.project2.generator;

import edu.project2.Maze;
import org.jetbrains.annotations.NotNull;

public interface Generator {
    /**
     * Generate maze with given height and with
     * @param height rows count
     * @param width columns count
     * @return maze
     */
    @NotNull Maze generate(int height, int width);
}
