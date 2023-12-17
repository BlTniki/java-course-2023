package edu.hw11.task3;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public enum LongSum implements StackManipulation {

    INSTANCE; // singleton

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Size apply(
            MethodVisitor methodVisitor,
            Implementation.Context implementationContext) {
        methodVisitor.visitInsn(Opcodes.LADD);
        return new Size(-1, 0);
    }
}
