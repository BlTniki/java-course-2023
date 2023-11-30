package edu.project4;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.jetbrains.annotations.NotNull;

public class ImageCreator {
    public static void createImage(@NotNull Color[][] colors, @NotNull String fileName) {
        int xRes = colors.length;
        int yRes = colors[0].length;

        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < xRes; x++) {
            for (int y = 0; y < yRes; y++) {
                image.setRGB(x, y, colors[x][y].getRGB());
            }
        }

        try {
            ImageIO.write(image, "png", new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
