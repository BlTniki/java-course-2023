package edu.project1;

import java.util.Scanner;
import org.apache.logging.log4j.Logger;

/**
 * Handle Hangman game in a console. It's read user input and print game state.
 */
public class ConsoleHangman {
    private final Logger logger;
    private final Scanner scanner;
    private final Dictionary dictionary;
    private final int maxAttempts;

    public ConsoleHangman(
        final Logger logger,
        final Scanner scanner,
        final Dictionary dictionary,
        final int maxAttempts
    ) {
        this.logger = logger;
        this.scanner = scanner;
        this.dictionary = dictionary;
        this.maxAttempts = maxAttempts;
    }

    /**
     * Init game session and running it while session is not dead.
     */
    public void run() {
        final String word = dictionary.randomWord();
        final Session session = new Session(word, maxAttempts);

        while (session.isNotDead()) {
            final char userGuess = readUserInput(scanner);
            final GuessResult guessResult = tryGuess(session, userGuess);
            printState(guessResult);
        }
    }

    /**
     * Read user input and validate it.
     * If input failed validation method will ask for the input again.
     * @return User guess or '\0' if user press Ctrl+D
     */
    private char readUserInput(final Scanner scanner) {
        logger.info("Guess a letter:");

        while (true) {
            if (!scanner.hasNextLine()) {
                return '\0';
            }

            final String line = scanner.nextLine();

            if (line.length() != 1) {
                logger.info("Bad input! Try again:");
            } else {
                return line.toLowerCase().charAt(0);
            }
        }
    }

    /**
     * Provide user guess to session. If guess equal '\0' method will execute kill session
     * @param session user session
     * @param guess user guess or '\0'
     * @return guess result from session.
     */
    private GuessResult tryGuess(final Session session, final char guess) {
        if (guess == '\0') {
            return session.giveUp();
        } else {
            return session.evaluatePlayerGuess(guess);
        }
    }

    /**
     * Print guess result into logger in a beautiful form.
     * @param guess guess result from session
     */
    private void printState(final GuessResult guess) {
        for (String line: guess.message().split("\n")) {
            logger.info(line);
        }
        logger.info("");
    }
}
