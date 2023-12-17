package edu.hw11.task1;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class  HelloWordByteBuddy {
    private final static Logger LOGGER = LogManager.getLogger();

    private HelloWordByteBuddy() {
    }

    @SuppressWarnings("checkstyle:UncommentedMain")
    public static void main(String[] args) throws
        IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // Create a dynamic class
        try (DynamicType.Unloaded<Object> unloaded = new ByteBuddy()
                .subclass(Object.class) // Extend Object class
                .method(ElementMatchers.named("toString")) // Match the toString() method
                .intercept(FixedValue.value("Hello, ByteBuddy")) // Provide the implementation
                .make()) {
            Class<?> dynamicType = unloaded
                .load(HelloWordByteBuddy.class.getClassLoader())
                .getLoaded();
            // Instantiate the dynamic class
            Object instance = dynamicType.getDeclaredConstructor().newInstance();

            LOGGER.info(instance);
        }

    }
}
