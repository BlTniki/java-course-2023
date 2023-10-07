package edu.project1;

import org.jetbrains.annotations.NotNull;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This class stores dictionary for game.
 * Class read strings from file in resources assuming
 * that each line it's separate word.
 */
public class DictionaryFromResources implements Dictionary {
    /**
     * Dictionary where stored all words from file.
     */
    private final List<String> dict;

    public DictionaryFromResources(String dictFileName) throws FileNotFoundException {
        this.dict = readDictFromFile(dictFileName);
    }

    /**
     * Reading dictionary from file in resources dir Util.
     * @param fileName where dict is stored
     * @return dictionary of words for game
     * @throws FileNotFoundException if a file with this name is not found
     */
    public static List<String> readDictFromFile(String fileName) throws FileNotFoundException {
        List<String> strings = new ArrayList<>();

        ClassLoader classLoader = Main.class.getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream);

            while (scanner.hasNextLine()) {
                strings.add(scanner.nextLine());
            }

            scanner.close();
        } else {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        return strings;
    }
    @Override
    public @NotNull String randomWord() {
        Random rand = new Random();
        return dict.get(rand.nextInt(dict.size()));
    }
}
