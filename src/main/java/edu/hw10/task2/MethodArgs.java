package edu.hw10.task2;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

/**
 * Box class for specific method and args array.
 * This box using args array copy and not original array, so we can safely use hash of the array.
 */
public record MethodArgs(String method, Object[] args) implements Serializable {
    public static @NotNull MethodArgs of(@NotNull Method method, @NotNull Object[] args) {
        return new MethodArgs(method.getName(), args);
    }

    public String getMethod() {
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
        if (!method.equals(other.method)) {
            return false;
        }
        return Arrays.equals(args, other.args);
    }
}
