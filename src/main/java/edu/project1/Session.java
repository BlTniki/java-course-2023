package edu.project1;

import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

class Session {
    private final String answer;
    private final int maxAttempts;
    private final Set<Character> unguessedCharacters;
    private int attempts;
    private boolean dead;

    Session(String answer, int maxAttempts) {
        this.answer = answer;
        this.maxAttempts = maxAttempts;
        this.attempts = 0;
        this.dead = false;

        this.unguessedCharacters = answer.toLowerCase().chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toSet());
    }

    private String provideUserAnswer() {
        String userAnswer = answer;

        for (char c : unguessedCharacters) {
            userAnswer = userAnswer.replace(Character.toLowerCase(c), '*');
            userAnswer = userAnswer.replace(Character.toUpperCase(c), '*');
        }

        return userAnswer;
    }

    /**
     * Checking player guess.
     * @param guess player guess. Guess is case-insensitive
     * @return guess result
     */
    @NotNull GuessResult evaluatePlayerGuess(char guess) {
        if (unguessedCharacters.remove(guess)) {
            if (unguessedCharacters.isEmpty()) {
                dead = true;
                return new GuessResult.Win(provideUserAnswer(), attempts, maxAttempts);
            } else {
                return new GuessResult.SuccessfulGuess(provideUserAnswer(), attempts, maxAttempts);
            }
        } else {
            attempts += 1;
            if (attempts >= maxAttempts) {
                dead = true;
                return new GuessResult.Defeat(provideUserAnswer(), attempts, maxAttempts);
            } else {
                return new GuessResult.FailedGuess(provideUserAnswer(), attempts, maxAttempts);
            }
        }
    }

    /**
     * Killing session.
     * @return give up message
     */
    @NotNull GuessResult giveUp() {
        dead = true;
        return new GuessResult.GiveUp(provideUserAnswer(), attempts, maxAttempts);
    }

    boolean isNotDead() {
        return !this.dead;
    }
}
