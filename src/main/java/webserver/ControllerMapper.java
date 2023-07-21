package webserver;

import annotation.RequestMapping;
import common.enums.RequestMethod;
import exception.NoSuchControllerMethodException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static webserver.ServerConfig.CONTROLLER_CLASS;

public class ControllerMapper {
    private static final ControllerMapper INSTANCE = new ControllerMapper();
    private final Map<RequestMethod, Map<String, Method>> map;

    private ControllerMapper() {
        map = new HashMap<>();
        map.put(RequestMethod.GET, new HashMap<>());
        map.put(RequestMethod.POST, new HashMap<>());
        map.put(RequestMethod.PUT, new HashMap<>());
        map.put(RequestMethod.DELETE, new HashMap<>());
    }

    public static ControllerMapper getInstance() {
        return INSTANCE;
    }

    public void initialize() {
        Method[] methods = CONTROLLER_CLASS.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);

                RequestMethod requestMethod = annotation.method();
                String path = annotation.path();

                map.get(requestMethod).put(path, method);
            }
        }
    }

    public Method getControllerMethod(RequestMethod requestMethod, String path) {
        if (map.containsKey(requestMethod) && map.get(requestMethod).containsKey(path)) {
            return map.get(requestMethod).get(path);
        }
        throw new NoSuchControllerMethodException("처리할 컨트롤러 없음");
    }
}
