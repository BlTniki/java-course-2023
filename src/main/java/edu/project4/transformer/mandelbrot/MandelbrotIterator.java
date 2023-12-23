package edu.project4.transformer.mandelbrot;

import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public class MandelbrotIterator implements Iterator<ComplexPoint> {
    private final ComplexPoint c;
    @NotNull private ComplexPoint curZ;

    public MandelbrotIterator(@NotNull ComplexPoint c) {
        this(c, new ComplexPoint(0, 0));
    }

    public MandelbrotIterator(@NotNull ComplexPoint c, @NotNull ComplexPoint z0) {
        this.c = c;
        this.curZ = z0;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public ComplexPoint next() {
        curZ = curZ.square().plus(c);
        return curZ;
    }
}
