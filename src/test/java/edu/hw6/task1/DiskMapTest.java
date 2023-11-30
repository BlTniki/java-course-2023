package edu.hw6.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiskMapTest {
    private static final String TEST_FILE_PATH = "testFile.txt";


    @AfterEach
    void tearDown() throws IOException {
        // Clean up the test file after each test
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    @DisplayName("Проверим сохранение и загрузку карты")
    void putAndGet() {
        DiskMap diskMap = new DiskMap(TEST_FILE_PATH);

        diskMap.put("key1", "value1");
        diskMap.put("key2", "value2");

        diskMap = new DiskMap(TEST_FILE_PATH);

        Assertions.assertThat(diskMap.get("key1")).isEqualTo("value1");
        Assertions.assertThat(diskMap.get("key2")).isEqualTo("value2");
    }

    @Test
    @DisplayName("Проверим сохранение и загрузку строк с двоеточием")
    void putAndGetStringWithDoubleDot() {
        DiskMap diskMap = new DiskMap(TEST_FILE_PATH);

        diskMap.put("ke:y1", "val:ue1");

        diskMap = new DiskMap(TEST_FILE_PATH);

        Assertions.assertThat(diskMap.get("ke:y1")).isEqualTo("val:ue1");
    }

    @Test
    @DisplayName("Проверим удаление из карты")
    void remove() {
        DiskMap diskMap = new DiskMap(TEST_FILE_PATH);

        diskMap.put("key1", "value1");
        diskMap.put("key2", "value2");

        diskMap = new DiskMap(TEST_FILE_PATH);

        diskMap.remove("key1");

        diskMap = new DiskMap(TEST_FILE_PATH);

        Assertions.assertThat(diskMap.size()).isEqualTo(1);
        Assertions.assertThat(diskMap.get("key1")).isNull();
    }

}
