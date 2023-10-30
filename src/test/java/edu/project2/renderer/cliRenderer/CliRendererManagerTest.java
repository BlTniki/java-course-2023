package edu.project2.renderer.cliRenderer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CliRendererManagerTest {

    @Test
    @DisplayName("Ок ок я просто набиваю покрытие кода")
    void getRenderer() {
        assertThat(new CliRendererManager().getRenderer())
            .isInstanceOf(CliRenderer.class);
    }
}
