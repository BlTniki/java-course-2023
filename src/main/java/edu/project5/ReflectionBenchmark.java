package edu.project5;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@SuppressWarnings({"checkstyle:InnerTypeLast", "checkstyle:MultipleStringLiterals", "MagicNumber"})
@State(Scope.Thread)
public class ReflectionBenchmark {
    @SuppressWarnings("checkstyle:UncommentedMain")
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(5))
            .build();

        new Runner(options).run();
    }

    record Student(String name, String surname) {
    }

    private static Function<Object, Object> createGetter(final MethodHandles.Lookup lookup,
        final MethodHandle getter) throws Exception {
        final CallSite site = LambdaMetafactory.metafactory(lookup, // MethodHandles.Lookup
            "apply", // Name of lambda method from interface
            // (Function.class) in our case
            MethodType.methodType(Function.class), // MethodType of interface
            MethodType.methodType(Object.class, Object.class), //Signature of method Function.apply after type erasure
            getter, // MethodHandle
            getter.type()); //Actual signature of getter
        try {
            //noinspection unchecked
            return (Function<Object, Object>) site.getTarget().invokeExact();
        } catch (final Exception e) {
            throw e;
        } catch (final Throwable e) {
            throw new Error(e);
        }
    }

    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private Function<Object, Object> function;

    @Setup
    public void setup() throws Throwable {
        student = new Student("Alexander", "Biryukov");

        method = student.getClass().getMethod("name");

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class);
        methodHandle = lookup.findVirtual(Student.class, "name", methodType);

        function = createGetter(lookup, methodHandle);
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHandle(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invokeExact(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambda(Blackhole bh) {
        String name = (String) function.apply(student);
        bh.consume(name);
    }
}
