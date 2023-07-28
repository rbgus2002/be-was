package webserver;

import annotation.RequestMapping;
import common.enums.RequestMethod;
import exception.NotFoundException;
import exception.MethodNotAllowedException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static webserver.ServerConfig.CONTROLLER_CLASS;

public class ControllerMapper {
    private static final ControllerMapper INSTANCE = new ControllerMapper();
    private final Map<String, Map<RequestMethod, Method>> map;


    private ControllerMapper() {
        map = new HashMap<>();
    }

    public static ControllerMapper getInstance() {
        return INSTANCE;
    }

    public void initialize() {
        registerControllerMethod(CONTROLLER_CLASS);
    }

    public Method getControllerMethod(String path, RequestMethod requestMethod) {
        if (notAllowMethod(path, requestMethod)) {
            throw new MethodNotAllowedException("잘못된 메소드 요청");
        }

        if (notFoundPath(path)) {
            throw new NotFoundException("처리할 컨트롤러 없음");
        }

        return map.get(path).get(requestMethod);
    }

    private boolean notAllowMethod(String path, RequestMethod requestMethod) {
        return map.containsKey(path) && !map.get(path).containsKey(requestMethod);
    }

    private boolean notFoundPath(String path) {
        return !map.containsKey(path);
    }

    private void registerControllerMethod(Class<?> clazz) {
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);

                String path = annotation.path();
                RequestMethod requestMethod = annotation.method();

                if (!map.containsKey(path)) {
                    map.put(path, new HashMap<>());
                }
                map.get(path).put(requestMethod, method);
            }
        }
    }

}
