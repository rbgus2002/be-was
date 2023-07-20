package webserver;

import annotations.Controller;
import annotations.RequestMapping;
import model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HandlerMapping {

    private static final String ROOT_PATH = "src/main";
    private static final Logger logger = LoggerFactory.getLogger(HandlerMapping.class);

    protected Object getHandler(HttpRequest request) {

        Set<Class<?>> controllers = reflection(ROOT_PATH);

        String uri = request.getRequestURI();

        //TODO: 예외처리 부분 404 페이지 반환하도록 수정
        if(controllers.isEmpty()) {
            return null;
        }

        //TODO: 예외처리 부분 404 페이지 반환하도록 수정
        return controllers.stream().filter(controller ->
            Arrays.stream(controller.getDeclaredMethods()).anyMatch(method -> {
                if(method.isAnnotationPresent(RequestMapping.class)) {
                    return method.getAnnotation(RequestMapping.class).value().equals(uri);
                }
                return false;
            })
        ).findFirst().orElseThrow(() -> new RuntimeException("매핑되는 컨트롤러가 존재하지 않습니다."));
    }

    private Set<Class<?>> reflection(String path) {
        Set<Class<?>> controllers = new HashSet<>();

        File rootDir = new File(path);
        String[] files = null;
        if(rootDir.exists() && rootDir.isDirectory()) {
            files = rootDir.list();
        }

        if(files == null) {
            return Collections.emptySet();
        }

        for(String file : files) {
            String subPath = path + File.separator + file;

            File subFile = new File(subPath);

            if(subFile.isDirectory()) {
                controllers.addAll(reflection(subPath));
                continue;
            }

            if(subPath.endsWith(".class")) {
                String className = subPath.substring(0, subPath.length() - ".class".length());

                try {
                    Class<?> clazz = Class.forName(path.replace('/', '.') + "." + className);
                    if(isController(clazz)) {
                        controllers.add(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        return controllers;
    }

    private boolean isController(Class<?> clazz) {
        return clazz.isAnnotationPresent(Controller.class);
    }
}
