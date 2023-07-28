package webserver;

import annotation.View;
import view.ViewBase;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ViewScanner {
    public static Map<String, ViewBase> scan() {
        Map<String, ViewBase> annotatedClasses = new HashMap<>();

        String packageName = "view";

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

                            if (clazz.isAnnotationPresent(View.class)) {
                                View annotation = clazz.getAnnotation(View.class);
                                annotatedClasses.put(annotation.path(), (ViewBase) clazz.getDeclaredConstructor().newInstance());
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
