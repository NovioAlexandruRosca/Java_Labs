package org.example.tools;

import org.example.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Test
public class TestExecutorUtil {
    static public void executeTestMethods(Class<?> clazz){
        int passed = 0, failed = 0;
        for (Method m : clazz.getMethods()) {

            int modifiers = m.getModifiers();

            if (m.isAnnotationPresent(Test.class) && m.getParameterCount() == 0 && Modifier.isStatic(modifiers)) {
                try {
                    m.invoke(null);
                    passed++;
                } catch (Throwable ex) {
                    System.out.printf("Test %s failed: %s %n",
                            m, ex.getCause());
                    failed++;
                }
            }
        }
        System.out.printf("Passed: %d, Failed %d%n", passed, failed);
    }

    static public void executeTestMethods2(Class<?> clazz){
        int passed = 0, failed = 0;

        int classModifiers = clazz.getModifiers();

        if(clazz.isAnnotationPresent(Test.class) && Modifier.isPublic(classModifiers)) {

            for (Method m : clazz.getMethods()) {

                if(m.isAnnotationPresent(Test.class)) {

                    try {

                        if (isInheritedFromObject(m)) {
                            System.out.println("Skipping method " + m.getName() + " inherited from Object class.");
                            continue;
                        }

                        if (m.getParameterCount() == 0) {
                            m.invoke(null);
                        } else {
                            Object[] arguments = generateMockArguments(m.getParameterTypes());
                            m.invoke(clazz.getDeclaredConstructor().newInstance(), arguments);
                        }


                        passed++;
                    } catch (Throwable ex) {
                        System.out.printf("Test %s failed: %s %n",
                                m, ex.getCause());
                        failed++;
                    }
                }
            }
            System.out.printf("Passed: %d, Failed %d%n", passed, failed);

        }
    }

    private static Object[] generateMockArguments(Class<?>[] parameterTypes) {
        Object[] arguments = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (parameterType.equals(int.class)) {
                arguments[i] = 0;
            } else if (parameterType.equals(String.class)) {
                arguments[i] = "test";
            } else {
                arguments[i] = null;
            }
        }
        return arguments;
    }

    @Test
    public static void anotherTestMethod() {
        System.out.println("This is another test method.");
    }

    private static boolean isInheritedFromObject(Method method) {
        return method.getDeclaringClass().equals(Object.class);
    }
}
