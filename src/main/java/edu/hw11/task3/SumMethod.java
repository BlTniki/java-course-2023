package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

enum SumMethod implements ByteCodeAppender {

    INSTANCE; // singleton

    @SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:MultipleStringLiterals"})
    @Override
    public Size apply(
            MethodVisitor mv,
            Implementation.Context implementationContext,
            MethodDescription instrumentedMethod) {
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
//        mv.visitVarInsn(Opcodes.ILOAD, 1);
//        Label label = new Label();
//        mv.visitJumpInsn(Opcodes.IFEQ, label);
//        mv.visitInsn(Opcodes.ICONST_5);
//        mv.visitInsn(Opcodes.IRETURN);
//
//        mv.visitLabel(label);
//        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//        mv.visitInsn(Opcodes.ICONST_4);
//
//        mv.visitInsn(Opcodes.IRETURN);

        // if statement with frame interactions
//        mv.visitVarInsn(Opcodes.ILOAD, 1);
//        Label label = new Label();
//        mv.visitJumpInsn(Opcodes.IFEQ, label);
//
//        mv.visitVarInsn(Opcodes.ALOAD, 0);
//        mv.visitVarInsn(Opcodes.ILOAD, 1);
//        mv.visitInsn(Opcodes.ICONST_1);
//        mv.visitInsn(Opcodes.ISUB);
//
//        mv.visitMethodInsn(
//            Opcodes.INVOKEVIRTUAL,
//            instrumentedMethod.getDeclaringType().asErasure().getInternalName(),
//            instrumentedMethod.getName(),
//            instrumentedMethod.getDescriptor(),
//            false
//        );
//
//        mv.visitInsn(Opcodes.LRETURN);
//
//        mv.visitLabel(label);
//        mv.visitFrame(
//            Opcodes.F_SAME,
//            2,
//            new Object[] {
//                instrumentedMethod.getInternalName(),
//                Opcodes.INTEGER
//            },
//            0,
//            null
//        );
//        mv.visitVarInsn(Opcodes.ILOAD, 1); // repeat for if statement
//        mv.visitInsn(Opcodes.ICONST_5);
//        mv.visitInsn(Opcodes.IADD);
//        mv.visitInsn(Opcodes.I2L);
//        mv.visitInsn(Opcodes.LRETURN);

        Label l0 = new Label();

        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitJumpInsn(Opcodes.IF_ICMPGT, l0);

        // if n <= 1
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(Opcodes.I2L);
        mv.visitInsn(Opcodes.LRETURN);

        // if n > 1
        mv.visitLabel(l0);
        mv.visitFrame(Opcodes.F_SAME, 2, new Object[] {
            instrumentedMethod.getInternalName(),
            Opcodes.INTEGER
        }, 0, null);

        // fib(n - 1)
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitInsn(Opcodes.ISUB);
        mv.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            instrumentedMethod.getDeclaringType().asErasure().getInternalName(),
            instrumentedMethod.getName(),
            instrumentedMethod.getDescriptor(),
            false
        );

        // fib(n - 2)
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInsn(Opcodes.ICONST_2);
        mv.visitInsn(Opcodes.ISUB);
        mv.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            instrumentedMethod.getDeclaringType().asErasure().getInternalName(),
            instrumentedMethod.getName(),
            instrumentedMethod.getDescriptor(),
            false
        );

        mv.visitInsn(Opcodes.LADD);
        mv.visitInsn(Opcodes.LRETURN);

        mv.visitMaxs(5, 2);
        mv.visitEnd();

        return new Size(5, 2);
    }
}
