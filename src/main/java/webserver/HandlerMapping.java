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

    private static final String ROOT_PATH = "src/main/java";
    private static final Logger logger = LoggerFactory.getLogger(HandlerMapping.class);

    /**
     * URL을 handle하는 Controller를 찾아 반환
     * @param request
     * @return Object, Class<?> Controller를 Object로 Casting한 객체 반환
     * null, Controller 클래스가 존재하지 않거나 매핑되는 Controller가 존재하지 않을 때 null 반환
     */
    protected Class<?> getHandler(HttpRequest request) {

        Set<Class<?>> controllers = reflectionControllerFinder(ROOT_PATH);

        String uri = request.getRequestURI();

        //Controller 클래스가 존재하지 않을 때 null 반환
        if(controllers.isEmpty()) {
            return null;
        }

        //매핑되는 URI를 가진 메서드가 존재하는 controller 반환, 없으면 NULL
        return controllers.stream().filter(controller ->
            Arrays.stream(controller.getDeclaredMethods()).anyMatch(method -> {
                if(method.isAnnotationPresent(RequestMapping.class)) {
                    return method.getAnnotation(RequestMapping.class).value().equals(uri);
                }
                return false;
            })
        ).findFirst().orElse(null);
    }

    /**
     * Controller 어노테이션이 붙은 Controller 클래스 Set을 반환
     * @param path, 검색을 시작할 디렉토리 위치
     * @return Set<Class<?>>, Controller 클래스 Set, 하나도 존재하지 않으면 emptySet() 반환
     */
    private Set<Class<?>> reflectionControllerFinder(String path) {
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
                controllers.addAll(reflectionControllerFinder(subPath));
                continue;
            }

            if(subPath.endsWith(".java")) {
                try {
                    subPath = subPath.substring(ROOT_PATH.length() + 1).replace('/', '.');
                    subPath = subPath.substring(0, subPath.length() - ".java".length());
                    Class<?> clazz = Class.forName(subPath);
                    if(isController(clazz)) {
                        controllers.add(clazz);
                    }
                } catch (Exception e) {
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
