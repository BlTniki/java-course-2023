package edu.hw11.task3;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static net.bytebuddy.matcher.ElementMatchers.named;

public final class Main {
    private Main() {
    }

    private static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings("checkstyle:MagicNumber") public static void main(String[] args) throws
        IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        try (DynamicType.Unloaded<Fib> unloaded = new ByteBuddy()
                .subclass(Fib.class)
                .method(named("fib"))
                .intercept(FibImplementation.INSTANCE)
                .make()) {
            Class<?> dynamicType = unloaded
                .load(Main.class.getClassLoader())
                .getLoaded();
            Fib instance = (Fib) dynamicType.newInstance();

            LOGGER.info(instance.fib(8));
        }
    }
}
