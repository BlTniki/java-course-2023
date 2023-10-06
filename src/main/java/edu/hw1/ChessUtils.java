package edu.hw1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ChessUtils {

    private final static int BOARD_SIZE = 8;

    private final static String WRONG_ARRAY_SIZE_MESSAGE = "Array have wrong size";

    private ChessUtils() {
    }

    /**
     * Generates coordinates to which the knight can descend at given coordinates.
     * @param x the line on which the knight stands
     * @param y the column on which the knight stands
     * @return coordinates to check.
     */
    public static List<BoardCoordinates> generateCoordinatesToCheck(int x, int y) {
        ArrayList<BoardCoordinates> toCheck = new ArrayList<>();

        // generate all possible coordinates
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                final BoardCoordinates fCoordinates = new BoardCoordinates(
                    x + i,
                    y + (j * 2)
                );

                final BoardCoordinates sCoordinates = new BoardCoordinates(
                    x + (i * 2),
                    y + j
                );

                toCheck.add(fCoordinates);
                toCheck.add(sCoordinates);
            }
        }

        // filter invalid coordinates and return
        return toCheck.stream()
            .filter(BoardCoordinates::hasNoNegativeValues)
            .toList();
    }

    /**
     * Checks if the knights are placed on the chessboard so that no knight can capture another knight.
     * @param board 8x8 int array where 1 represent the knight and 0 represent empty place
     * @return true, if no knight can capture another knight, false otherwise
     */
    public static boolean knightBoardCapture(final int[][] board) {
        // validate board
        Objects.requireNonNull(board);
        if (board.length != BOARD_SIZE) {
            throw new IllegalArgumentException(WRONG_ARRAY_SIZE_MESSAGE);
        }
        for (var line : board) {
            Objects.requireNonNull(line);
            if (line.length != BOARD_SIZE) {
                throw new IllegalArgumentException(WRONG_ARRAY_SIZE_MESSAGE);
            }
        }

        //check
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                List<BoardCoordinates> toCheck = generateCoordinatesToCheck(i, j);
                for (var coordinates : toCheck) {
                    if (board[coordinates.x][coordinates.y] == 1) {
                        return false;
                    }
                }
            }
        }


        return true;
    }

    /**
     * Records that encapsulates board coordinates
     * @param x board line
     * @param y board column
     */
    public record BoardCoordinates(int x, int y) {
        /**
         * Checks if x or y are negative.
         * @return true if x or y are negative
         */
        public boolean hasNoNegativeValues() {
            return x >= 0 || y >= 0;
        }
    }
}
