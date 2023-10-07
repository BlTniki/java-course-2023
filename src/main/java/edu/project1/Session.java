package edu.project1;

import org.jetbrains.annotations.NotNull;

class Session {
    private final String answer;
    private final char[] userAnswer;
    private final int maxAttempts;
    private int attempts;

    @NotNull GuessResult guess(char guess);

    @NotNull GuessResult giveUp();
}
