package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AttrFilesFilterTest {

    @Test
    @DisplayName("Проверим что файлы фильтруются по атрибуту")
    void check() {
        String os = System.getProperty("os.name").toLowerCase();

        AttrFilesFilter filter;
        if (os.contains("win")) {
            filter = new AttrFilesFilter(List.of(
                new AttrFilesFilter.Attr("dos:readonly", true)
            ));

            try (var ds = Files.newDirectoryStream(Path.of("./src/test/resources/files/attr"), filter)) {
                List<Path> list = StreamSupport.stream(ds.spliterator(), false).toList();
                assertThat(list)
                    .containsOnly(Path.of("./src/test/resources/files/attr/nonwritable"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Помогите....
//            filter = new AttrFilesFilter(List.of(
//                new AttrFilesFilter.Attr("unix:mode", 777)
//            ));
        }


    }
}
