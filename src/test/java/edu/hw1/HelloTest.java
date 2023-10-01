package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelloTest {

    @Test
    @DisplayName("Проверим работу логера")
    void sayHi() {
        Hello.sayHi();
    }
}
