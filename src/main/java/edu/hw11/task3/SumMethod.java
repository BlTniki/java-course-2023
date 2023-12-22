package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
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

        // simple sum two const
//        mv.visitInsn(Opcodes.ICONST_2);
//        mv.visitInsn(Opcodes.ICONST_2);
//        mv.visitInsn(Opcodes.IADD);

        // sum argument with const
//        mv.visitVarInsn(Opcodes.ILOAD, 1);
//        mv.visitInsn(Opcodes.ICONST_2);
//        mv.visitInsn(Opcodes.IADD);

        // simple if statement
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        Label label = new Label();
        mv.visitJumpInsn(Opcodes.IFEQ, label);
        mv.visitInsn(Opcodes.ICONST_5);
        mv.visitInsn(Opcodes.IRETURN);

        mv.visitLabel(label);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitInsn(Opcodes.ICONST_4);

        mv.visitInsn(Opcodes.IRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();

        return new Size(2, 2);
    }
}
