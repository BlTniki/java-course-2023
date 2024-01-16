package edu.project4;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import org.jetbrains.annotations.NotNull;

public final class ImageCreator {
    private ImageCreator() {
    }

    /**
     * Save color array on disk as png image file.
     * @param colors color array
     * @param fileName path to file
     */
    public static void createImage(@NotNull Color[][] colors, @NotNull Path fileName) {
        int xRes = colors.length;
        int yRes = colors[0].length;

        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < xRes; x++) {
            for (int y = 0; y < yRes; y++) {
                image.setRGB(x, y, colors[x][y].getRGB());
            }
        }

        try {
            ImageIO.write(image, "png", fileName.toFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
