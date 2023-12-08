package edu.hw10.task1;

public sealed interface TestParams {
    record PrimInt(int p, @MinValue(-1L) @MaxValue(2L) int p2) implements TestParams {}
    record PrimLong(long p, @MinValue(-1L) @MaxValue(2L) long p2) implements TestParams {}
    record PrimFloat(float p, @MinValue(-1L) @MaxValue(2L) float p2) implements TestParams {}
    record PrimDouble(double p, @MinValue(-1L) @MaxValue(2L) double p2) implements TestParams {}
    record PrimBoolean(boolean p) implements TestParams {}
    record MyInteger(Integer p, @MyNotNull Integer p2, @MyNotNull @MinValue(-1L) @MaxValue(2L) Integer p3) implements
        TestParams {}
    record MyLong(Long p, @MyNotNull Long p2, @MyNotNull @MinValue(-1L) @MaxValue(2L) Long p3) implements
        TestParams {}
    record MyFloat(Float p, @MyNotNull Float p2, @MyNotNull @MinValue(-1L) @MaxValue(2L) Float p3) implements
        TestParams {}
    record MyDouble(Double p, @MyNotNull Double p2, @MyNotNull @MinValue(-1L) @MaxValue(2L) Double p3) implements
        TestParams {}
    record MyBoolean(Boolean p, @MyNotNull Boolean p2) implements TestParams {}
    record MyString(String p, @MyNotNull String p2) implements TestParams {}
}
