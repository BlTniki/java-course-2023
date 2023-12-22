package edu.hw11.task3;

import edu.hw11.task2.SumToMult;
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

    public static void main(String[] args) throws
        IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        try (DynamicType.Unloaded<SumExample> unloaded = new ByteBuddy()
                .subclass(SumExample.class)
                .method(named("calculate"))
                .intercept(SumImplementation.INSTANCE)
                .make()) {
            Class<?> dynamicType = unloaded
                .load(SumToMult.class.getClassLoader())
                .getLoaded();
            SumExample instance = (SumExample) dynamicType.newInstance();

            LOGGER.info(instance.calculate());
        }
    }
}
