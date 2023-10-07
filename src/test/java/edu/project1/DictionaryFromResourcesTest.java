package edu.project1;

import edu.project1.exception.BadDictionaryFormatException;
import java.io.FileNotFoundException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class DictionaryFromResourcesTest {

    private final static Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Получение случайного слова: good")
    void randomWord() throws FileNotFoundException, BadDictionaryFormatException {
        // given
        Dictionary dictionary = new DictionaryFromResources("good_dict.txt");
        List<String> expectedWords = List.of("foo", "bar", "lolkek", "cheburek");

        // when
        String word = dictionary.randomWord();

        LOGGER.trace(word);

        // then
        assertThat(word)
            .isIn(expectedWords);
    }

    @Test
    void readDictFromFile() {
    }
}
