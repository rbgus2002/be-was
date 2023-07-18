package webserver.myframework.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static webserver.myframework.utils.FileUtils.*;

public class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static List<Class<?>> getClassesInPackage(String packageName) throws ReflectiveOperationException, FileNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        URL packageURL = getPackageURL(packageName);
        processDirectory(getPackageDirectory(packageURL), packageName, classes);
        return classes;
    }

    public static boolean classHasAnnotation(Class<?> clazz, Class<? extends Annotation> targetAnnotation) {
        List<? extends Class<? extends Annotation>> annotations =
                getClassesHasCustomAnnotation(clazz);
        if(annotations.contains(targetAnnotation)) {
            return true;
        }

        return annotations.stream()
                .anyMatch(annotation -> classHasAnnotation(annotation, targetAnnotation));
    }

    private static List<? extends Class<? extends Annotation>> getClassesHasCustomAnnotation(Class<?> clazz) {
        return Arrays.stream(clazz.getAnnotations())
                .map(Annotation::annotationType)
                .filter(ReflectionUtils::isCustomAnnotation)
                .collect(Collectors.toList());
    }

    public static List<Field> getFieldsHaveAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }

    public static List<Method> getMethodsHaveAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }

    public static boolean classHaveAnnotatedMethod(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(clazz.getMethods())
                .anyMatch(method -> method.isAnnotationPresent(annotationClass));
    }

    private static URL getPackageURL(String packageName) throws FileNotFoundException {
        String packagePath = packageName.replace(".", "/");
        URL packageURL = Thread.currentThread().getContextClassLoader().getResource(packagePath);
        if (packageURL == null) {
            throw new FileNotFoundException("패키지 URL을 가져오는데 실패하였습니다");
        }
        return packageURL;
    }

    //TODO: 메소드 길이 줄이기
    private static void processDirectory(File directory,
                                         String packageName,
                                         List<Class<?>> classes) throws FileNotFoundException, ClassNotFoundException {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new FileNotFoundException("패키지 디렉토리를 나열할 수 없습니다");
        }
        for (File file : files) {
            if (file.isDirectory()) {
                String temp = packageName.isBlank() ? file.getName() : packageName + "." + file.getName();
                processDirectory(file, temp, classes);
                continue;
            }

            if (file.isFile()) {
                getClassFromFile(packageName, classes, file);
            }
        }
    }

    private static void getClassFromFile(String packageName, List<Class<?>> classes, File file) throws ClassNotFoundException {
        String fileName = file.getName();
        if (!fileName.endsWith(".class")) {
            return;
        }

        String className = packageName + "." + fileName.substring(0, fileName.length() - 6);
        Class<?> clazz = Class.forName(className);
        classes.add(clazz);
    }

    public static boolean isCustomAnnotation(Class<? extends Annotation> annotationType) {
        return !annotationType.getPackage().getName().equals("java.lang.annotation");
    }
}
