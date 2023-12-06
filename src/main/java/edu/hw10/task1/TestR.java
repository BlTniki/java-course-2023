package edu.hw10.task1;

import edu.project2.Coordinate;

public record TestR(@MinValue(-10) @MaxValue(5) int i,
                    @MinValue(20) @MaxValue(40) double d,
                    @MyNotNull String s,
                    Coordinate coordinate
) {
    public static TestR create(int i, double d) {
        return new TestR(i, d, "factory", new Coordinate(i, (int) d));
    }
}
