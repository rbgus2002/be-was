package webserver;

import annotation.RequestMapping;
import controller.HttpController;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ControllerScanner {
    private ControllerScanner() {}

    public static Map<String, HttpController> scan() {
        Map<String, HttpController> annotatedClasses = new HashMap<>();

        String packageName = "controller";

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File dir = new File(resource.getFile());

                if (dir.isDirectory()) {
                    String[] files = dir.list();
                    if (files != null) {
                        for (String file : files) {
                            String className = file.substring(0, file.length() - 6); // Remove ".class" extension
                            String fullClassName = packageName + "." + className;
                            Class<?> clazz = classLoader.loadClass(fullClassName);

                            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                                RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
                                annotatedClasses.put(annotation.path(), (HttpController) clazz.getDeclaredConstructor().newInstance());
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return annotatedClasses;
    }
}
