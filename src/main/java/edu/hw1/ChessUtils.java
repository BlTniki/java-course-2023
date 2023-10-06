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
     * @param coordinates record with x and y board coordinates. Must pass BoardCoordinates::isThisPossibleCoordinates
     * @return coordinates to check.
     */
    public static List<BoardCoordinates> generateCoordinatesToCheck(BoardCoordinates coordinates) {
        if (!coordinates.isThisPossibleCoordinates()) {
            throw new IllegalArgumentException("This coordinates is not possible for 8x8 board");
        }

        ArrayList<BoardCoordinates> toCheck = new ArrayList<>();

        // generate all possible coordinates
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                final BoardCoordinates fCoordinates = new BoardCoordinates(
                    coordinates.x + i,
                    coordinates.y + (j * 2)
                );

                final BoardCoordinates sCoordinates = new BoardCoordinates(
                    coordinates.x + (i * 2),
                    coordinates.y + j
                );

                toCheck.add(fCoordinates);
                toCheck.add(sCoordinates);
            }
        }

        // filter invalid coordinates and return
        return toCheck.stream()
            .filter(BoardCoordinates::isThisPossibleCoordinates)
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
                if (board[i][j] == 0) {
                    continue;
                }
                List<BoardCoordinates> toCheck = generateCoordinatesToCheck(new BoardCoordinates(i, j));
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
         * Checks if coordinates can be possible at 8x8 board.
         * @return true if possible
         */
        public boolean isThisPossibleCoordinates() {
            return x >= 0 && y >= 0 && x < BOARD_SIZE && y < BOARD_SIZE;
        }
    }
}
