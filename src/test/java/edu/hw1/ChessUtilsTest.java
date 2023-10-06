package edu.hw1;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChessUtilsTest {

    @Test
    @DisplayName("Генерация координат: центр")
    void generateCoordinatesToCheck_center() {
        // given
        ChessUtils.BoardCoordinates coordinates = new ChessUtils.BoardCoordinates(3, 3);
        List<ChessUtils.BoardCoordinates> expectedAnswer = List.of(
            new ChessUtils.BoardCoordinates(1, 2),
            new ChessUtils.BoardCoordinates(2, 1),
            new ChessUtils.BoardCoordinates(1, 4),
            new ChessUtils.BoardCoordinates(2, 5),
            new ChessUtils.BoardCoordinates(4, 1),
            new ChessUtils.BoardCoordinates(5, 2),
            new ChessUtils.BoardCoordinates(4, 5),
            new ChessUtils.BoardCoordinates(5, 4)
        );

        // when
        List<ChessUtils.BoardCoordinates> returnedAnswer = ChessUtils.generateCoordinatesToCheck(coordinates);

        // then
        assertThat(returnedAnswer)
            .containsExactlyInAnyOrderElementsOf(expectedAnswer);
    }

    @Test
    @DisplayName("Генерация координат: левый верхний угол")
    void generateCoordinatesToCheck_leftUpCorner() {
        // given
        ChessUtils.BoardCoordinates coordinates = new ChessUtils.BoardCoordinates(0, 0);
        List<ChessUtils.BoardCoordinates> expectedAnswer = List.of(
            new ChessUtils.BoardCoordinates(1, 2),
            new ChessUtils.BoardCoordinates(2, 1)
        );

        // when
        List<ChessUtils.BoardCoordinates> returnedAnswer = ChessUtils.generateCoordinatesToCheck(coordinates);

        // then
        assertThat(returnedAnswer)
            .containsExactlyInAnyOrderElementsOf(expectedAnswer);
    }

    @Test
    @DisplayName("Генерация координат: правый верхний угол")
    void generateCoordinatesToCheck_rightUpCorner() {
        // given
        ChessUtils.BoardCoordinates coordinates = new ChessUtils.BoardCoordinates(0, 7);
        List<ChessUtils.BoardCoordinates> expectedAnswer = List.of(
            new ChessUtils.BoardCoordinates(1, 5),
            new ChessUtils.BoardCoordinates(2, 6)
        );

        // when
        List<ChessUtils.BoardCoordinates> returnedAnswer = ChessUtils.generateCoordinatesToCheck(coordinates);

        // then
        assertThat(returnedAnswer)
            .containsExactlyInAnyOrderElementsOf(expectedAnswer);
    }

    @Test
    @DisplayName("Генерация координат: правый нижний угол")
    void generateCoordinatesToCheck_rightDownCorner() {
        // given
        ChessUtils.BoardCoordinates coordinates = new ChessUtils.BoardCoordinates(7, 7);
        List<ChessUtils.BoardCoordinates> expectedAnswer = List.of(
            new ChessUtils.BoardCoordinates(5, 6),
            new ChessUtils.BoardCoordinates(6, 5)
        );

        // when
        List<ChessUtils.BoardCoordinates> returnedAnswer = ChessUtils.generateCoordinatesToCheck(coordinates);

        // then
        assertThat(returnedAnswer)
            .containsExactlyInAnyOrderElementsOf(expectedAnswer);
    }

    @Test
    @DisplayName("Генерация координат: левый нижний угол")
    void generateCoordinatesToCheck_leftDownCorner() {
        // given
        ChessUtils.BoardCoordinates coordinates = new ChessUtils.BoardCoordinates(7, 0);
        List<ChessUtils.BoardCoordinates> expectedAnswer = List.of(
            new ChessUtils.BoardCoordinates(5, 1),
            new ChessUtils.BoardCoordinates(6, 2)
        );

        // when
        List<ChessUtils.BoardCoordinates> returnedAnswer = ChessUtils.generateCoordinatesToCheck(coordinates);

        // then
        assertThat(returnedAnswer)
            .containsExactlyInAnyOrderElementsOf(expectedAnswer);
    }

    @Test
    @DisplayName("Генерация координат: невозможные координаты")
    void generateCoordinatesToCheck_impossibleCoordinates() {
        // given
        List<ChessUtils.BoardCoordinates> coordinatesList = List.of(
            new ChessUtils.BoardCoordinates(123, 0),
            new ChessUtils.BoardCoordinates(0, 123),
            new ChessUtils.BoardCoordinates(-1, 0),
            new ChessUtils.BoardCoordinates(0, -1)
        );

        // expect
        for (var coordinates : coordinatesList) {
            assertThatThrownBy(() -> ChessUtils.generateCoordinatesToCheck(coordinates))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("Проверка досок: валидные доски")
    void knightBoardCapture() {
        // given
        List<int[][]> boards = new ArrayList<>();
        boards.add(new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        });
        boards.add(new int[][]{
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        });
        boards.add(new int[][]{
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        });
        boards.add(new int[][]{
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0}
        });
        List<Boolean> expectedAnswers = List.of(
            true,
            true,
            false,
            false
        );

        // when
        List<Boolean> returnedAnswers = boards.stream()
                .map(ChessUtils::knightBoardCapture)
                .toList();

        // then
        assertThat(returnedAnswers)
            .isEqualTo(expectedAnswers);
    }

    @Test
    @DisplayName("Проверка досок: неверное кол-во строк")
    void knightBoardCapture_badLinesCount() {
        // given
        int[][] board = {{1,2,3,4,5,6,7,8}};

        // expect
        assertThatThrownBy(() -> ChessUtils.knightBoardCapture(board))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверка досок: неверное кол-во колонок")
    void knightBoardCapture_badColumnsCount() {
        // given
        int[][] board = {
            {1,2,3,4,5,6,7, 8},
            {1,2,3,4,5,6,7, 8},
            {1,2,3,4,5,6,7, 8},
            {1,2,3,4,5,6,7},
            {1,2,3,4,5,6,7, 8},
            {1,2,3,4,5,6,7, 8},
            {1,2,3,4,5,6,7, 8},
            {1,2,3,4,5,6,7, 8}
        };

        // expect
        assertThatThrownBy(() -> ChessUtils.knightBoardCapture(board))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверка досок: null pointer #1")
    void knightBoardCapture_nullpointer1() {
        // given
        int[][] board = null;

        // expect
        assertThatThrownBy(() -> ChessUtils.knightBoardCapture(board))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка досок: null pointer #2")
    void knightBoardCapture_nullpointer2() {
        // given
        int[][] board = new int[8][8];
        board[0] = null;

        // expect
        assertThatThrownBy(() -> ChessUtils.knightBoardCapture(board))
            .isInstanceOf(NullPointerException.class);
    }
}
