package edu.project2;

import org.jetbrains.annotations.NotNull;

public record Cell(int row, int col, Type type) {
    public enum Type {
        WALL,
        PASSAGE
    }

    public @NotNull Cell changeType(Type type) {
        return new Cell(row, col, type);
    }
}
