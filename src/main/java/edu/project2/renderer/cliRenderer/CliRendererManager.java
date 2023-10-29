package edu.project2.renderer.cliRenderer;

import edu.project2.renderer.Renderer;
import edu.project2.renderer.RendererManager;
import org.jetbrains.annotations.NotNull;

public class CliRendererManager implements RendererManager {
    private Renderer renderer;

    public CliRendererManager() {
    }

    @Override
    public @NotNull Renderer getRenderer() {
        if (renderer != null) {
            return renderer;
        }

        final CliMask mask = new CliMask(CellCliTexturePack.BASIC);
        this.renderer = new CliRenderer(mask);
        return this.renderer;
    }
}
