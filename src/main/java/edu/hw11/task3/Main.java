package edu.hw11.task3;

import net.bytebuddy.ByteBuddy;
import static net.bytebuddy.matcher.ElementMatchers.named;

public final class Main {
    private Main() {
    }

    @SuppressWarnings("checkstyle:RegexpSinglelineJava")
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        Class<?> dynamicType = new ByteBuddy()
            .subclass(FibAbstract.class)
            .method(named("fib"))
            .intercept(FibImplementation.INSTANCE)
            .make()
            .load(Main.class.getClassLoader())
            .getLoaded();

        FibAbstract instance = (FibAbstract) dynamicType.newInstance();
        System.out.println(instance.fib(1));
    }
}
