package edu.project1;

import org.jetbrains.annotations.NotNull;

sealed interface GuessResult {

    @NotNull String message();

    record Defeat(String state, int attempt, int maxAttempts) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "Missed, mistake %d out of %d.\n\nTheWord: %s\n\nYou lost!".formatted(attempt, maxAttempts, state);
        }
    }

    record Win(String state, int attempt, int maxAttempts) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "Hit!\n\nTheWord: %s\n\nYou win!".formatted(state);
        }
    }

    record GiveUp(String state, int attempt, int maxAttempts) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "You give up!\n\nTheWord: %s\n\nEnd game.".formatted(state);
        }
    }

    record SuccessfulGuess(String state, int attempt, int maxAttempts) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "Hit!\n\nTheWord: %s".formatted(state);
        }
    }

    record FailedGuess(String state, int attempt, int maxAttempts) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "Missed, mistake %d out of %d.\n\nTheWord: %s".formatted(attempt, maxAttempts, state);
        }
    }
}
