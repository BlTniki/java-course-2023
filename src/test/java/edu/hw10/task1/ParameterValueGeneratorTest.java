package edu.hw10.task1;

import java.lang.reflect.Parameter;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ParameterValueGeneratorTest {

    @Test
    void gen_prim_int() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.PrimIntegerGenerator();

        Parameter p = TestParams.PrimInt.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand).nextInt(anyInt(), anyInt());

        Parameter p2 = TestParams.PrimInt.class.getConstructors()[0].getParameters()[1];
        pvg.gen(p2, mockedRand);
        verify(mockedRand).nextInt(eq(-1), eq(2));
    }

    @Test
    void gen_prim_long() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.PrimLongGenerator();

        Parameter p = TestParams.PrimLong.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand).nextLong(anyLong(), anyLong());

        Parameter p2 = TestParams.PrimLong.class.getConstructors()[0].getParameters()[1];
        pvg.gen(p2, mockedRand);
        verify(mockedRand).nextLong(eq(-1L), eq(2L));
    }

    @Test
    void gen_prim_float() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.PrimFloatGenerator();

        Parameter p = TestParams.PrimFloat.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand).nextFloat(anyFloat(), anyFloat());

        Parameter p2 = TestParams.PrimFloat.class.getConstructors()[0].getParameters()[1];
        pvg.gen(p2, mockedRand);
        verify(mockedRand).nextFloat(eq(-1.0f), eq(2.0f));
    }

    @Test
    void gen_prim_double() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.PrimDoubleGenerator();

        Parameter p = TestParams.PrimDouble.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand).nextDouble(anyDouble(), anyDouble());

        Parameter p2 = TestParams.PrimDouble.class.getConstructors()[0].getParameters()[1];
        pvg.gen(p2, mockedRand);
        verify(mockedRand).nextDouble(eq(-1.0), eq(2.0));
    }

    @Test
    void gen_prim_boolean() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.PrimBooleanGenerator();

        Parameter p = TestParams.PrimBoolean.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand).nextBoolean();
    }

    @Test
    void gen_Integer() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.IntegerGenerator();

        Parameter p = TestParams.MyInteger.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand).nextBoolean();

        mockedRand = mock(Random.class);

        Parameter p2 = TestParams.MyInteger.class.getConstructors()[0].getParameters()[1];
        pvg.gen(p2, mockedRand);
        verify(mockedRand, never()).nextBoolean();
        verify(mockedRand).nextInt(anyInt(), anyInt());


        Parameter p3 = TestParams.MyInteger.class.getConstructors()[0].getParameters()[2];
        pvg.gen(p3, mockedRand);
        verify(mockedRand, never()).nextBoolean();
        verify(mockedRand).nextInt(eq(-1), eq(2));
    }

    @Test
    void gen_Long() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.LongGenerator();

        Parameter p = TestParams.MyLong.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand).nextBoolean();

        mockedRand = mock(Random.class);

        Parameter p2 = TestParams.MyLong.class.getConstructors()[0].getParameters()[1];
        pvg.gen(p2, mockedRand);
        verify(mockedRand, never()).nextBoolean();
        verify(mockedRand).nextLong(anyLong(), anyLong());

        Parameter p3 = TestParams.MyLong.class.getConstructors()[0].getParameters()[2];
        pvg.gen(p3, mockedRand);
        verify(mockedRand, never()).nextBoolean();
        verify(mockedRand).nextLong(eq(-1L), eq(2L));
    }

    @Test
    void gen_Float() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.FloatGenerator();

        Parameter p = TestParams.MyFloat.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand).nextBoolean();

        mockedRand = mock(Random.class);

        Parameter p2 = TestParams.MyFloat.class.getConstructors()[0].getParameters()[1];
        pvg.gen(p2, mockedRand);
        verify(mockedRand, never()).nextBoolean();
        verify(mockedRand).nextFloat(anyFloat(), anyFloat());

        Parameter p3 = TestParams.MyFloat.class.getConstructors()[0].getParameters()[2];
        pvg.gen(p3, mockedRand);
        verify(mockedRand, never()).nextBoolean();
        verify(mockedRand).nextFloat(eq(-1.0f), eq(2.0f));
    }

    @Test
    void gen_Double() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.DoubleGenerator();

        Parameter p = TestParams.MyDouble.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand).nextBoolean();

        mockedRand = mock(Random.class);

        Parameter p2 = TestParams.MyDouble.class.getConstructors()[0].getParameters()[1];
        pvg.gen(p2, mockedRand);
        verify(mockedRand, never()).nextBoolean();
        verify(mockedRand).nextDouble(anyDouble(), anyDouble());

        Parameter p3 = TestParams.MyDouble.class.getConstructors()[0].getParameters()[2];
        pvg.gen(p3, mockedRand);
        verify(mockedRand, never()).nextBoolean();
        verify(mockedRand).nextDouble(eq(-1.0), eq(2.0));
    }

    @Test
    void gen_Boolean() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.BooleanGenerator();

        Parameter p = TestParams.MyBoolean.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand, atLeast(1)).nextBoolean();

        mockedRand = mock(Random.class);

        Parameter p2 = TestParams.MyBoolean.class.getConstructors()[0].getParameters()[1];
        pvg.gen(p2, mockedRand);
        verify(mockedRand, times(1)).nextBoolean();
    }

    @Test
    void gen_String() {
        Random mockedRand = mock(Random.class);
        ParameterValueGenerator pvg = new ParameterValueGenerator.StringGenerator();

        Parameter p = TestParams.MyString.class.getConstructors()[0].getParameters()[0];
        pvg.gen(p, mockedRand);
        verify(mockedRand, times(1)).nextBoolean();

        mockedRand = mock(Random.class);

        Parameter p2 = TestParams.MyString.class.getConstructors()[0].getParameters()[1];
        Object arg = pvg.gen(p2, mockedRand);
        verify(mockedRand, never()).nextBoolean();
        assertThat((String) arg).isNotEmpty();
    }
}
