package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.LongConstant;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.jar.asm.MethodVisitor;

public enum FibMethod implements ByteCodeAppender {
    INSTANCE;

    @SuppressWarnings("checkstyle:MagicNumber") @Override
    public Size apply(
            MethodVisitor methodVisitor,
            Implementation.Context implementationContext,
            MethodDescription instrumentedMethod) {
        if (!instrumentedMethod.getReturnType().asErasure().represents(long.class)) {
            throw new IllegalArgumentException(instrumentedMethod + " must return long");
        }
        StackManipulation.Size operandStackSize = new StackManipulation.Compound(
            LongConstant.forValue(10L),
            LongConstant.forValue(50L),
            LongSum.INSTANCE,
            MethodReturn.LONG
        ).apply(methodVisitor, implementationContext);
        return new Size(operandStackSize.getMaximalSize(),
            instrumentedMethod.getStackSize());
    }
}
