package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ClassListener {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static List<Class<?>> scanClass(String packageName) {
        String path = packageName.replace('.', '/');
        List<Class<?>> classes = new ArrayList<>();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            File packageDirectory = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());

            if (packageDirectory.exists() && packageDirectory.isDirectory()) {
                File[] files = packageDirectory.listFiles();

                assert files != null;
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);

                        try {
                            Class<?> clazz = Class.forName(className);
                            classes.add(clazz);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            logger.error("존재하지 않는 패키지 입니다. : {}", packageName);
        }

        return classes;
    }
}
