package edu.hw11.task3;

import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import org.jetbrains.annotations.NotNull;

enum FibImplementation implements Implementation {

    INSTANCE; // singleton

    @Override
    public @NotNull InstrumentedType prepare(@NotNull InstrumentedType instrumentedType) {
        return instrumentedType;
    }

    @Override
    public @NotNull ByteCodeAppender appender(@NotNull Target implementationTarget) {
        return FibMethod.INSTANCE;
    }
}
