package edu.hw6.task4;

import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileWriterUtilsTest {

    @Test
    @DisplayName("Проверим запись цитаты в файла")
    void writeAUFWord() {
        Path path = Path.of("./src/test/resources/files/auf");

        FileWriterUtils.writeAUFWord(path);
    }
}
