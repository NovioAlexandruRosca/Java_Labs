package org.example.tools;

import org.objectweb.asm.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class BytecodeManipulation {

    public static void main(String[] args) throws IOException {
        String className = "org/example/tests/MyTestClass";
        byte[] originalBytecode = readClassBytes(className);
        byte[] modifiedBytecode = instrumentMethod(originalBytecode, "testMethod");

        try (FileOutputStream fos = new FileOutputStream("D:\\Fac\\java\\Rezolvari\\Laboratorul_12\\target\\classes\\org\\example\\tests\\MyTestClassModified.class")) {
            fos.write(modifiedBytecode);
        }
    }

    private static byte[] readClassBytes(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classReader.accept(classWriter, 0);
        return classWriter.toByteArray();
    }

    private static byte[] instrumentMethod(byte[] bytecode, String methodName) {
        ClassReader classReader = new ClassReader(bytecode);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM7, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (name.equals(methodName)) {

                    MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
                    return new MethodVisitor(Opcodes.ASM7, mv) {
                        @Override
                        public void visitCode() {

                            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            mv.visitLdcInsn("Newly injected code");
                            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                            super.visitCode();
                        }
                    };
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        };

        classReader.accept(classVisitor, 0);
        return classWriter.toByteArray();
    }
}
