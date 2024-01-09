package edu.hw11.task2;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SumToMult {
    private final static Logger LOGGER = LogManager.getLogger();

    private SumToMult() {
    }

    @SuppressWarnings("checkstyle:UncommentedMain")
    public static void main(String[] args) throws
        IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        try (DynamicType.Unloaded<ArithmeticUtils> unloaded = new ByteBuddy()
            .subclass(ArithmeticUtils.class)
            .method(ElementMatchers.named("sum"))
            .intercept(MethodDelegation.to(Target.class))
            .make()) {
            Class<?> dynamicType = unloaded
                .load(SumToMult.class.getClassLoader())
                .getLoaded();
            ArithmeticUtils instance = (ArithmeticUtils) dynamicType.newInstance();

            LOGGER.info(instance.sum(1, 1));
        }
    }

}
