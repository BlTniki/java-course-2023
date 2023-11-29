package edu.project4;

import edu.project4.fractalFrame.Pixel;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageCreator {
    public static void createImage(Pixel[][] pixels, String fileName) {
        int xRes = pixels.length;
        int yRes = pixels[0].length;

        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < xRes; x++) {
            for (int y = 0; y < yRes; y++) {
                int rgb = pixels[x][y].color.getRGB();
                image.setRGB(x, y, rgb);
            }
        }

        try {
            ImageIO.write(image, "png", new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Ваш массив pixels
        Pixel[][] pixels = new Pixel[1920][1080];

        // Заполните массив pixels данными, например, с помощью вашей функции render

        // Создайте изображение и сохраните его в файл
        createImage(pixels, "output.png");
    }
}
