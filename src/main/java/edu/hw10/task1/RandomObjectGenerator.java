package edu.hw10.task1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Random;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class RandomObjectGenerator {
    private final Random random;

    public RandomObjectGenerator(Random random) {
        this.random = random;
    }

    private static Constructor<?> getPublicAndMostWideConstructor(Class<?> clazz) {
        final var declaredConstructors = clazz.getDeclaredConstructors();
        Constructor<?> result = null;
        for (var constr : declaredConstructors) {
            if (result == null) {
                if (Modifier.isPublic(constr.getModifiers())) {
                    result = constr;
                }
                continue;
            }
            if (constr.getParameters().length > result.getParameters().length) {
                result = constr;
            }
        }
        return result;
    }

    @SuppressWarnings("checkstyle:ReturnCount")
    private Object generateValForParameter(Parameter parameter) {
        Class<?> type = parameter.getType();

        // Note that we must handle the different types here! This is just an
        // example, so this list is not complete! Adapt this to your needs!
        if (type.isEnum()) {
            Object[] enumValues = type.getEnumConstants();
            return enumValues[random.nextInt(enumValues.length)];
        } else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return random.nextInt();
        } else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            return random.nextLong();
        } else if (type.equals(Double.TYPE) || type.equals(Double.class)) {
            return random.nextDouble();
        } else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
            return random.nextFloat();
        } else if (type.equals(String.class)) {
            return UUID.randomUUID().toString();
        }
        return nextObject(type);
    }

    public <T> @NotNull T nextObject(Class<T> clazz) {
        Constructor<?> declaredConstructor = getPublicAndMostWideConstructor(clazz);

        if (declaredConstructor == null) {
            throw new IllegalArgumentException("Given class have no public constructors");
        }


        Parameter[] parameters = declaredConstructor.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            args[i] = generateValForParameter(parameters[i]);
        }
        try {
            //noinspection unchecked
            return (T) declaredConstructor.newInstance(args);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        var r = new RandomObjectGenerator(new Random());
        r.nextObject(TestR.class);
    }
}
