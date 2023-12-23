package edu.hw10.task1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RandomObjectGenerator {
    private final Random random;
    private final Map<Class<?>, ParameterValueGenerator> generatorHandler;

    public RandomObjectGenerator(Random random) {
        this.random = random;
        this.generatorHandler = new HashMap<>();
        this.generatorHandler.put(int.class, new ParameterValueGenerator.PrimIntegerGenerator());
        this.generatorHandler.put(Integer.class, new ParameterValueGenerator.IntegerGenerator());
        this.generatorHandler.put(long.class, new ParameterValueGenerator.PrimLongGenerator());
        this.generatorHandler.put(Long.class, new ParameterValueGenerator.LongGenerator());
        this.generatorHandler.put(float.class, new ParameterValueGenerator.PrimFloatGenerator());
        this.generatorHandler.put(Float.class, new ParameterValueGenerator.FloatGenerator());
        this.generatorHandler.put(double.class, new ParameterValueGenerator.PrimDoubleGenerator());
        this.generatorHandler.put(Double.class, new ParameterValueGenerator.DoubleGenerator());
        this.generatorHandler.put(boolean.class, new ParameterValueGenerator.PrimBooleanGenerator());
        this.generatorHandler.put(Boolean.class, new ParameterValueGenerator.BooleanGenerator());
        this.generatorHandler.put(String.class, new ParameterValueGenerator.StringGenerator());
    }

    @Nullable private Constructor<?> getPublicAndMostWideConstructor(Class<?> clazz) {
        final var declaredConstructors = clazz.getConstructors();
        Constructor<?> result = null;
        for (var constr : declaredConstructors) {
            if (result == null) {
                result = constr;
            } else if (constr.getParameters().length > result.getParameters().length) {
                result = constr;
            }
        }
        return result;
    }

    @Nullable private static <T> Method getMostWideFactoryMethodOverload(Class<T> clazz, String factoryMethodName) {
        Method factoryMethod = null;
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(factoryMethodName) && Modifier.isStatic(method.getModifiers())) {
                if (factoryMethod == null) {
                    factoryMethod = method;
                } else if (method.getParameters().length > factoryMethod.getParameters().length) {
                    // pick the most wide method
                    factoryMethod = method;
                }
            }
        }
        return factoryMethod;
    }

    private Object generateValForParameter(Parameter parameter) {
        Class<?> type = parameter.getType();

        ParameterValueGenerator generator = generatorHandler.get(type);
        if (generator != null) {
            return generator.gen(parameter, random);
        }

        boolean canBeNull = !parameter.isAnnotationPresent(MyNotNull.class);
        if (canBeNull && random.nextBoolean()) {
            return null;
        }
        return nextObject(type);
    }

    @NotNull private Object[] getArgsForParams(Parameter[] parameters) {
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            args[i] = generateValForParameter(parameters[i]);
        }
        return args;
    }

    public <T> @NotNull T nextObject(Class<T> clazz) {
        Constructor<?> constructor = getPublicAndMostWideConstructor(clazz);

        if (constructor == null) {
            throw new IllegalArgumentException("Given class have no public constructors");
        }

        Object[] args = getArgsForParams(constructor.getParameters());
        try {
            //noinspection unchecked
            return (T) constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> @NotNull T nextObject(Class<T> clazz, String factoryMethodName) {
        Method factoryMethod = getMostWideFactoryMethodOverload(clazz, factoryMethodName);

        if (factoryMethod == null) {
            throw new IllegalArgumentException(
                "Given class have no public factory method called: " + factoryMethodName
            );
        }

        Object[] args = getArgsForParams(factoryMethod.getParameters());
        try {
            //noinspection unchecked
            return (T) factoryMethod.invoke(null, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
