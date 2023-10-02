package edu.hw1;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyStringUtilsTest {

    @Test
    @DisplayName("Проверка на строках")
    void fixString() {
        // given
        List<String> brokenStrings = List.of("", "123456", "of oabr", "hTsii  s aimex dpus rtni.g", "badce", "оПомигети псаривьтс ртко!и");
        List<String> expectedStrings = List.of("", "214365", "foo bar", "This is a mixed up string.", "abcde", "Помогите исправить строки!");

        // when
        List<String> returnedStrings = brokenStrings.stream()
            .map(MyStringUtils::fixString)
            .toList();

        // then
        assertEquals(expectedStrings, returnedStrings);
    }
}
