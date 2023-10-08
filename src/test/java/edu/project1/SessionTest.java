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
        GuessResult successGuessResult1 = session.guess('t');
        GuessResult successGuessResult2 = session.guess('e');
        GuessResult winGuessResult = session.guess('s');

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
