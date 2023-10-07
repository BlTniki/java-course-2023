package edu.project1;

import org.jetbrains.annotations.NotNull;

sealed interface GuessResult {
    char[] state();

    int attempt();

    int maxAttempts();

    @NotNull String message();

    record Defeat(char[] state, int attempt, int maxAttempts) implements GuessResult {

        @Override
        public @NotNull String message() {
            return "";
        }
    }

    record Win(...) implements GuessResult {
    }

    record SuccessfulGuess(...) implements GuessResult {
    }

    record FailedGuess(...) implements GuessResult {
    }
}
