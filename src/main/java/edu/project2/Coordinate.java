package edu.project2;

public record Coordinate(int row, int col) {
    public static Coordinate of(Cell cell) {
        return new Coordinate(cell.row(), cell.col());
    }
}
