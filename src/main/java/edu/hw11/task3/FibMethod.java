package edu.hw11.task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

enum FibMethod implements ByteCodeAppender {

    INSTANCE; // singleton

    @SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:MultipleStringLiterals"})
    @Override
    public Size apply(
            MethodVisitor mv,
            Implementation.@NotNull Context implementationContext,
            MethodDescription instrumentedMethod) {
        Label l0 = new Label();

        mv.visitCode();

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
