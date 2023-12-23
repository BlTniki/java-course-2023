package edu.hw8.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PasswordUtilsTest {

    @Test
    @DisplayName("Проверим соответствие номера и буквы")
    void getCharByItsNumber() {
        String expected = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 62; i++) {
            builder.append(PasswordUtils.getCharByItsNumber(i));
        }

        assertThat(builder.toString())
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверим соответствие номера и буквы")
    void getPasswordByItsNumber() {
        String expected = "Zz9";

        // Z -> 61, z -> 35, 9 -> 9
        int num = (62*62)*61 + 62*35 + 9;

        assertThat(PasswordUtils.getPasswordByItsNumber(num, 0))
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверим корректность хеширования пароля")
    void encodeString() {
        String toEncode = "lolkek";
        String expectedHash = "72b539c7a3e2391537445b2b876e2320";

        String actualHash = PasswordUtils.encodeToMD5(toEncode);

        assertThat(actualHash)
            .isEqualTo(expectedHash);
    }
}
