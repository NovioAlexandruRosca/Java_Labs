package org.example.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class ClassLoaderUtil {

    public static Class<?> loadClass(String className, URLClassLoader classLoader) throws ClassNotFoundException {
        return classLoader.loadClass(className);
    }

    public static List<Class<?>> loadClassesFromDirectory(File directory, URLClassLoader classLoader) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        loadClassesFromDirectoryRecursive(directory, directory, classLoader, classes);
        return classes;
    }

    private static void loadClassesFromDirectoryRecursive(File rootDirectory, File directory, URLClassLoader classLoader, List<Class<?>> classes) throws IOException, ClassNotFoundException {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                loadClassesFromDirectoryRecursive(rootDirectory, file, classLoader, classes);
            } else if (file.getName().endsWith(".class")) {
                String className = getClassNameFromFile(file, new File("target/classes"));
                Class<?> clazz = ClassLoaderUtil.loadClass(className, classLoader);
                classes.add(clazz);
            }
        }
    }

    public static List<Class<?>> loadClassesFromJar(File jarFile) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        try (JarFile jar = new JarFile(jarFile)) {
            URL[] urls = {new URL("jar:file:" + jarFile.getAbsolutePath() + "!/")};
            URLClassLoader classLoader = URLClassLoader.newInstance(urls);
            jar.stream()
                    .filter(e -> e.getName().endsWith(".class"))
                    .forEach(entry -> {
                        String className = entry.getName().replace("/", ".").replace(".class", "");
                        try {
                            classes.add(loadClass(className, classLoader));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
        }
        return classes;
    }

    private static String getClassNameFromFile(File file, File rootDirectory) {
        String path = file.getAbsolutePath().replace(rootDirectory.getAbsolutePath() + File.separator, "");
        String className = path.substring(0, path.lastIndexOf('.')).replace(File.separatorChar, '.');
        return className;
    }
}
