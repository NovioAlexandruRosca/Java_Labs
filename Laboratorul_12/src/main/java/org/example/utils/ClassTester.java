package org.example.utils;

import org.example.tools.JavaPTool;
import org.example.tools.TestExecutorUtil;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import javax.tools.*;
import java.io.*;

public class ClassTester {
    public static void main(String[] args) throws Exception {

        String inputPath = "D:\\Fac\\java\\Rezolvari\\Laboratorul_12\\target\\classes\\org\\example\\tests\\MyTestClass.class";
//        String inputPath = "D:\\Fac\\java\\Rezolvari\\Laboratorul_12\\src\\main\\java\\org\\example\\tests\\MyTestClass.java";
//        String inputPath = "D:\\Fac\\java\\Rezolvari\\Laboratorul_12\\target\\classes\\org\\example\\tools";
        File inputFile = new File(inputPath);

        try {
            if (inputFile.isFile()) {
                if (inputFile.getName().endsWith(".class")) {
                    handleClassFile(inputFile);
                } else if (inputFile.getName().endsWith(".java")) {
                    compileAndHandleJavaFile(inputFile);
                } else if (inputFile.getName().endsWith(".jar")) {
                    handleJarFile(inputFile);
                }
            } else if (inputFile.isDirectory()) {
                handleDirectory(inputFile);
            } else {
                System.out.println("Invalid input. Provide a .class file, directory, or .jar file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClassFile(File classFile) throws Exception {
        URL[] urls = {classFile.getParentFile().toURI().toURL()};
        try (URLClassLoader classLoader = URLClassLoader.newInstance(urls)) {
            String className = getClassNameFromFile(classFile, new File("target/classes"));
            System.out.println(className);
            Class<?> clazz = ClassLoaderUtil.loadClass(className, classLoader);
            processClass(clazz);
        }
    }

    private static void handleJarFile(File jarFile) throws Exception {
        List<Class<?>> classes = ClassLoaderUtil.loadClassesFromJar(jarFile);
        for (Class<?> clazz : classes) {
            processClass(clazz);
        }
    }

    private static void handleDirectory(File directory) throws Exception {
        URL[] urls = {directory.toURI().toURL()};
        try (URLClassLoader classLoader = URLClassLoader.newInstance(urls)) {
            List<Class<?>> classes = ClassLoaderUtil.loadClassesFromDirectory(directory, classLoader);
            for (Class<?> clazz : classes) {
                processClass(clazz);
            }
        }
    }

    private static void processClass(Class<?> clazz) {
        System.out.println("Prototype of class: " + clazz.getName());
        System.out.println("-------------------------------------------");
        JavaPTool.printClassDetails(clazz);
        System.out.println("Executing @Test methods:");
        TestExecutorUtil.executeTestMethods2(clazz);
    }

    private static void compileAndHandleJavaFile(File javaFile) throws IOException, ClassNotFoundException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        File outputDir = new File(javaFile.getParent());
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(List.of(javaFile));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        fileManager.close();

        String className = getClassNameFromSourcePath(javaFile.getAbsolutePath());
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{outputDir.toURI().toURL()})) {
            Class<?> clazz = classLoader.loadClass("org.example.tests." + className);
            processClass(clazz);
        }
    }

    private static String getClassNameFromSourcePath(String sourcePath) {
        String className = sourcePath.substring(sourcePath.lastIndexOf(File.separator) + 1);
        return className.substring(0, className.lastIndexOf(".java"));
    }

    private static String getClassNameFromFile(File classFile, File root) {
        String path = classFile.getAbsolutePath().replace(root.getAbsolutePath(), "").replace(File.separator, ".");
        if (path.startsWith(".")) {
            path = path.substring(1);
        }
        return path.replace(".class", "");
    }
}