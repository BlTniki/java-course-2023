package edu.project1;

import edu.project1.exception.BadDictionaryFormatException;
import java.io.FileNotFoundException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DictionaryFromResourcesTest {

    private final static Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Получение случайного слова: good")
    void randomWord() throws FileNotFoundException, BadDictionaryFormatException {
        // given
        Dictionary dictionary = new DictionaryFromResources("dict/good_dict.txt");
        List<String> expectedWords = List.of("foo", "bar", "lolkek", "cheburek");

        // when
        String word = dictionary.randomWord();

        LOGGER.trace(word);

        // then
        assertThat(word)
            .isIn(expectedWords);
    }

    @Test
    @DisplayName("Загрузка словаря: good")
    void readDictFromFile_good() throws FileNotFoundException, BadDictionaryFormatException {
        // given
        String fileName = "dict/good_dict.txt";
        List<String> expectedList = List.of("foo", "bar", "lolkek", "cheburek");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        // when
        List<String> returnedList = DictionaryFromResources.readDictFromFile(classloader, fileName);

        // then
        assertThat(returnedList).isEqualTo(expectedList);
    }

    @Test
    @DisplayName("Загрузка словаря: файл не найден")
    void readDictFromFile_bad_non_exist() {
        // given
        String fileName = "bad_dict_non_exist.txt";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        assertThatThrownBy(() -> DictionaryFromResources.readDictFromFile(classloader, fileName))
            .isInstanceOf(FileNotFoundException.class)
            .hasMessage("File '%s' not found".formatted(fileName));
    }

    @Test
    @DisplayName("Загрузка словаря: пустой словарь")
    void readDictFromFile_bad_empty() {
        // given
        String fileName = "dict/bad_dict_empty.txt";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        assertThatThrownBy(() -> DictionaryFromResources.readDictFromFile(classloader, fileName))
            .isInstanceOf(BadDictionaryFormatException.class)
            .hasMessage("File '%s' is empty".formatted(fileName));
    }

    @Test
    @DisplayName("Загрузка словаря: пустая строка")
    void readDictFromFile_bad_empty_string() {
        // given
        String fileName = "dict/bad_dict_empty_string.txt";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        assertThatThrownBy(() -> DictionaryFromResources.readDictFromFile(classloader, fileName))
            .isInstanceOf(BadDictionaryFormatException.class)
            .hasMessage("File contain bad word line like -> ''");
    }

    @Test
    @DisplayName("Загрузка словаря: плохая строка")
    void readDictFromFile_bad_string() {
        // given
        String fileName = "dict/bad_dict_string.txt";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        assertThatThrownBy(() -> DictionaryFromResources.readDictFromFile(classloader, fileName))
            .isInstanceOf(BadDictionaryFormatException.class)
            .hasMessage("File contain bad word line like -> 'kek kek'");
    }
}
