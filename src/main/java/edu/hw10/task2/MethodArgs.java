package edu.hw10.task2;

import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Box class for specific method and args array.
 * This box using args array copy and not original array, so we can safely use hash of the array.
 */
public final class MethodArgs {
    private final Method method;
    private final Object[] args;

    private MethodArgs(@NotNull Method method, @NotNull Object[] args) {
        this.method = method;
        this.args = args.clone();
    }

    public static @NotNull MethodArgs of(@NotNull Method method, @NotNull Object[] args) {
        return new MethodArgs(method, args);
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + method.hashCode();
        result = prime * result + Arrays.hashCode(args);

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MethodArgs other = (MethodArgs) obj;
        return Arrays.equals(args, other.args);
    }
}
