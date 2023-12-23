package edu.project3.reader;

import edu.project3.exception.BadFilePathException;
import java.io.IOException;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UrlFileReaderTest {

    @Test
    @DisplayName("Получение существующего файла по юрл")
    void getStringsFrom() throws IOException, BadFilePathException {
        String urlString =
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

        Collection<String> actual = new UrlFileReader().getStringsFrom(urlString);

        assertThat(actual)
            .isNotEmpty();
    }

    @Test
    @DisplayName("Получение ошибки по плохому юрл (синтекс)")
    void getStringsFrom_bad_syntax() throws IOException, BadFilePathException {
        String urlString =
            "httpsdwaes/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

        assertThatThrownBy(() -> new UrlFileReader().getStringsFrom(urlString))
            .isInstanceOf(BadFilePathException.class);
    }

    @Test
    @DisplayName("Получение ошибки по плохому юрл (несуществует")
    void getStringsFrom_bad_not_exist() throws IOException, BadFilePathException {
        String urlString =
            "https://google.com/master/Commodwax_logs/nginx_logs";

        assertThatThrownBy(() -> new UrlFileReader().getStringsFrom(urlString))
            .isInstanceOf(BadFilePathException.class);
    }
}
