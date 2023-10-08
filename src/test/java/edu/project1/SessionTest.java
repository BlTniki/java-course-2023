package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @Test
    @DisplayName("Проверка флоу игры: победа")
    void guess_win() {
        // given
        Session session = new Session("Test", 1);

        // when
        GuessResult successGuessResult1 = session.guess('t');
        GuessResult successGuessResult2 = session.guess('e');
        GuessResult winGuessResult = session.guess('s');

        // then
        assertThat(successGuessResult1)
            .isInstanceOf(GuessResult.SuccessfulGuess.class);
        assertThat(successGuessResult2)
            .isInstanceOf(GuessResult.SuccessfulGuess.class);
        assertThat(winGuessResult)
            .isInstanceOf(GuessResult.Win.class);
    }

    @Test
    @DisplayName("Проверка флоу игры: проигрыш")
    void guess_defeat() {
        // given
        Session session = new Session("Test", 2);

        // when
        GuessResult failedGuessResult = session.guess('d');
        GuessResult successGuessResult = session.guess('e');
        GuessResult defeatGuessResult = session.guess('q');

        // then
        assertThat(failedGuessResult)
            .isInstanceOf(GuessResult.FailedGuess.class);
        assertThat(successGuessResult)
            .isInstanceOf(GuessResult.SuccessfulGuess.class);
        assertThat(defeatGuessResult)
            .isInstanceOf(GuessResult.Defeat.class);
    }

    @Test
    @DisplayName("Проверка сдачи")
    void giveUp() {
        // given
        Session session = new Session("test", 1);
        assertThat(session.isNotDead()).isEqualTo(true);

        // when
        session.giveUp();

        // then
        assertThat(session.isNotDead()).isEqualTo(false);
    }
}
