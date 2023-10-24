package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SessionTest {

    @Test
    @DisplayName("Проверка флоу игры: победа")
    void guess_win() {
        // given
        Session session = new Session("Test", 1);

        // when
        GuessResult successGuessResult1 = session.evaluatePlayerGuess('t');
        GuessResult successGuessResult2 = session.evaluatePlayerGuess('e');
        GuessResult winGuessResult = session.evaluatePlayerGuess('s');

        // then
        assertThat(successGuessResult1)
            .isInstanceOf(GuessResult.SuccessfulGuess.class);
        assertThat(successGuessResult1.message())
            .isEqualTo("Hit!\n\nTheWord: T**t");

        assertThat(successGuessResult2)
            .isInstanceOf(GuessResult.SuccessfulGuess.class);
        assertThat(successGuessResult2.message())
            .isEqualTo("Hit!\n\nTheWord: Te*t");

        assertThat(winGuessResult)
            .isInstanceOf(GuessResult.Win.class);
        assertThat(winGuessResult.message())
            .isEqualTo("Hit!\n\nTheWord: Test\n\nYou win!");

        assertThat(session.isNotDead()).isFalse();
    }

    @Test
    @DisplayName("Проверка флоу игры: проигрыш")
    void guess_defeat() {
        // given
        Session session = new Session("Test", 2);

        // when
        GuessResult failedGuessResult = session.evaluatePlayerGuess('d');
        GuessResult successGuessResult = session.evaluatePlayerGuess('e');
        GuessResult defeatGuessResult = session.evaluatePlayerGuess('q');

        // then
        assertThat(failedGuessResult)
            .isInstanceOf(GuessResult.FailedGuess.class);
        assertThat(failedGuessResult.message())
            .isEqualTo("Missed, mistake 1 out of 2.\n\nTheWord: ****");

        assertThat(successGuessResult)
            .isInstanceOf(GuessResult.SuccessfulGuess.class);
        assertThat(successGuessResult.message())
            .isEqualTo("Hit!\n\nTheWord: *e**");

        assertThat(defeatGuessResult)
            .isInstanceOf(GuessResult.Defeat.class);
        assertThat(defeatGuessResult.message())
            .isEqualTo("Missed, mistake 2 out of 2.\n\nTheWord: *e**\n\nYou lost!");

        assertThat(session.isNotDead()).isFalse();
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
        assertThat(session.isNotDead()).isFalse();
    }
}
