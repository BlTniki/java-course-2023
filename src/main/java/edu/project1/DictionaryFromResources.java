package edu.project1;

import edu.project1.exception.BadDictionaryFormatException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.jetbrains.annotations.NotNull;

/**
 * This class stores dictionary for game.
 * Class read strings from file in resources assuming
 * that each line it's separate word.
 */
public class DictionaryFromResources implements Dictionary {
    /**
     * Regex that match with string were several words.
     */
    @SuppressWarnings({"RegExpSimplifiable"})
    private final static String INCORRECT_WORD_REGEX = "^.*[\\s].*$";

    /**
     * Dictionary where stored all words from file.
     */
    private final List<String> dict;

    public DictionaryFromResources(String dictFileName)
        throws FileNotFoundException, BadDictionaryFormatException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        this.dict = readDictFromFile(classloader, dictFileName);
    }

    @Override
    public @NotNull String randomWord() {
        Random rand = new Random();
        return dict.get(rand.nextInt(dict.size()));
    }

    /**
     * Reading dictionary from file in resources dir Util.
     * @param classLoader context loader were that in resources
     * @param fileName where dict is stored
     * @return dictionary of words for game. On each line should be one word.
     * @throws FileNotFoundException if a file with this name is not found
     */
    public static List<String> readDictFromFile(ClassLoader classLoader, String fileName)
        throws FileNotFoundException, BadDictionaryFormatException {
        List<String> strings = new ArrayList<>();

        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream);

            if (!scanner.hasNextLine()) {
                throw new BadDictionaryFormatException("File %s is empty".formatted(fileName));
            }

            while (scanner.hasNextLine()) {
                final String word = scanner.nextLine();

                if (word.isEmpty() || word.matches(INCORRECT_WORD_REGEX)) {
                    throw new BadDictionaryFormatException(
                        "File contain bad word line like -> '%s'".formatted(word)
                    );
                }

                strings.add(word);
            }

            scanner.close();
        } else {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        return strings;
    }
}
