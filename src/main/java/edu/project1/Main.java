package edu.project1;

import edu.project1.exception.BadDictionaryFormatException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    private final static Logger LOGGER = LogManager.getLogger();
    private static final String DICT_FILE_NAME = "dict.txt";
    private static final int MAX_ATTEMPTS = 5;

    private Main() {
    }

    public static void main(final String[] args) throws FileNotFoundException, BadDictionaryFormatException {
        final Dictionary dictionary = new DictionaryFromResources(DICT_FILE_NAME);
        final Scanner scanner = new Scanner(System.in);

        LOGGER.info("Starting new game!");
        LOGGER.info("max attempts: " + MAX_ATTEMPTS);
        LOGGER.info("dictionary file: " + DICT_FILE_NAME);
        LOGGER.info("Type any character to guess the word! Guess is not case sensitive.");
        LOGGER.info("You can give up by pressing Ctrl+D...\n");

        final ConsoleHangman hangman = new ConsoleHangman(LOGGER, scanner, dictionary, MAX_ATTEMPTS);
        hangman.run();
    }
}
