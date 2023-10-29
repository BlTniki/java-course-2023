package edu.project2.generator;

import edu.project2.Maze;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RecursiveDivisionGeneratorTest {

    @Test
    @DisplayName("Проверим что генератор не падает и создаёт лабиринт")
    void generate() {
        // given
        int height = 4;
        int width = 4;
        Generator generator = new RecursiveDivisionGenerator(new Random());

        // when
        Maze maze = generator.generate(height, width);

        // then
        assertThat(maze)
            .isNotNull();
        assertThat(maze.getHeight()).isEqualTo(height);
        assertThat(maze.getWidth()).isEqualTo(width);
    }
}
