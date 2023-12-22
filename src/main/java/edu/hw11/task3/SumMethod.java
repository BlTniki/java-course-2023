package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

enum SumMethod implements ByteCodeAppender {

    INSTANCE; // singleton

    @Override
    public Size apply(
            MethodVisitor mv,
            Implementation.Context implementationContext,
            MethodDescription instrumentedMethod) {
//        if (!instrumentedMethod.getReturnType().asErasure().represents(int.class)) {
//            throw new IllegalArgumentException(instrumentedMethod + " must return int");
//        }
        mv.visitCode();

        mv.visitInsn(Opcodes.ICONST_2);
        mv.visitInsn(Opcodes.ICONST_2);
        mv.visitInsn(Opcodes.IADD);

        mv.visitInsn(Opcodes.IRETURN);

        mv.visitMaxs(2, 1);
        mv.visitEnd();

        return new Size(2, 1);
    }
}
