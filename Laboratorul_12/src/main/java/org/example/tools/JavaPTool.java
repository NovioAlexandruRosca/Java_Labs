package org.example.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class JavaPTool {

    public static void printClassDetails(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        printClassDetails(clazz);
    }

    public static void printClassDetails(Class<?> clazz) {
        System.out.println(Modifier.toString(clazz.getModifiers()) + " class " + clazz.getSimpleName() + " {");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("\t" + Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName() + ";");
        }

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.print("\t" + Modifier.toString(constructor.getModifiers()) + " " + constructor.getName() + "(");
            printParameters(constructor.getParameterTypes());
            System.out.println(");");
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.print("\t" + Modifier.toString(method.getModifiers()) + " ");
            System.out.print(method.getReturnType().getSimpleName() + " ");
            System.out.print(method.getName() + "(");
            printParameters(method.getParameterTypes());
            System.out.println(");");

            printAnnotations(method.getDeclaredAnnotations());
        }

        printAnnotations(clazz.getDeclaredAnnotations());

        System.out.println("}");
    }

    private static void printParameters(Class<?>[] parameterTypes) {
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(parameterTypes[i].getSimpleName() + " arg" + (i + 1));
        }
    }

    private static void printAnnotations(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            System.out.println("\t@" + annotation.annotationType().getSimpleName());
        }
    }
}
