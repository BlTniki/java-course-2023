package edu.hw6.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class DiskMapTest {
    private static final String TEST_FILE_PATH = "testFile.txt";


    @AfterEach
    void tearDown() throws IOException {
        // Clean up the test file after each test
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    void putAndGet() {
        DiskMap diskMap = new DiskMap(TEST_FILE_PATH);
        diskMap.put("key1", "value1");
        diskMap.put("key2", "value2");

        diskMap = new DiskMap(TEST_FILE_PATH);

        Assertions.assertThat(diskMap.get("key1")).isEqualTo("value1");
        Assertions.assertThat(diskMap.get("key2")).isEqualTo("value2");
    }

//    @Test
//    void size() {
//        diskMap.put("key1", "value1");
//        diskMap.put("key2", "value2");
//
//        Assertions.assertThat(diskMap.size()).isEqualTo(2);
//    }
//
//    @Test
//    void remove() {
//        diskMap.put("key1", "value1");
//        diskMap.put("key2", "value2");
//
//        Assertions.assertThat(diskMap.remove("key1")).isEqualTo("value1");
//        Assertions.assertThat(diskMap.size()).isEqualTo(1);
//        Assertions.assertThat(diskMap.get("key1")).isNull();
//    }
//
//    @Test
//    void containsKey() {
//        diskMap.put("key1", "value1");
//        diskMap.put("key2", "value2");
//
//        Assertions.assertThat(diskMap.containsKey("key1")).isTrue();
//        Assertions.assertThat(diskMap.containsKey("nonexistentKey")).isFalse();
//    }
//
//    @Test
//    void containsValue() {
//        diskMap.put("key1", "value1");
//        diskMap.put("key2", "value2");
//
//        Assertions.assertThat(diskMap.containsValue("value1")).isTrue();
//        Assertions.assertThat(diskMap.containsValue("nonexistentValue")).isFalse();
//    }

}
