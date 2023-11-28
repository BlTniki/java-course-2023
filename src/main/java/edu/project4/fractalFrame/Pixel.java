package edu.project4.fractalFrame;

import java.awt.Color;

public class Pixel {
    public int counter;
    public Color color;

    Pixel() {
        this.counter = 0;
        this.color = Color.BLACK;
    }

    public void increaseCounter() {
        counter++;
    }
}

