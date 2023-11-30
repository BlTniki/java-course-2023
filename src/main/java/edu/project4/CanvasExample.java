package edu.project4;

import edu.project4.fractalFrame.HistoPoint;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:InnerTypeLast"})
public class CanvasExample extends JFrame {
    private final Canvas canvas;
    private final HistoPoint[][] colors;

    public CanvasExample(int width, int height, HistoPoint[][] colors) {
        this.colors = colors;
        canvas = new Canvas();
        canvas.setSize(width, height); // Устанавливаем размеры Canvas

        // Создаем таймер для обновления Canvas каждые 100 миллисекунд
        Timer timer = new Timer(100, e -> {
            // Обновляем Canvas с новыми цветами
            canvas.repaint();
        });
        timer.start();

        // Добавляем Canvas в окно
        add(canvas);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    class Canvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Отрисовываем массив цветов
            for (int i = 0; i < colors.length; i++) {
                for (int j = 0; j < colors[0].length; j++) {
                    g.setColor(colors[i][j].color);
                    g.fillRect(i, j, 10, 10);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HistoPoint[][] colors = new HistoPoint[1000][1000];

        // Инициализация массива цветов (например, все белые)
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[0].length; j++) {
                colors[i][j] = new HistoPoint();
                colors[i][j].color = Color.WHITE;
            }
        }

        SwingUtilities.invokeLater(() -> new CanvasExample(colors.length, colors[0].length, colors));
        Thread.sleep(2000);

        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[0].length; j++) {
                colors[i][j] = new HistoPoint();
                colors[i][j].color = Color.BLACK;
            }
        }
    }
}
