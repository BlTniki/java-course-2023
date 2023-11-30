package edu.project4.fractalFrame;

import java.awt.Color;

public class HistoPoint {
    public int counter;
    public Color color;

    public HistoPoint() {
        this.counter = 0;
        this.color = Color.BLACK;
    }

    public void increaseCounter() {
        counter++;
    }
}

