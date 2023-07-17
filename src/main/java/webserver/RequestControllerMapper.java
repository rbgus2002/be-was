package webserver;

import controller.Controller;
import common.HttpRequest.Method;

import java.util.HashMap;
import java.util.Map;

public class RequestControllerMapper {

    private static final RequestControllerMapper instance = new RequestControllerMapper();

    private RequestControllerMapper() {

    }

    public static RequestControllerMapper getInstance() {
        return instance;
    }

    private static Map<String, Map<Method, Controller>> mapper = new HashMap<>();

    public void put(String path, Method method, Controller controller) {
        if (!mapper.containsKey(path)) {
            mapper.put(path, new HashMap<>());
        }
        mapper.get(path).put(method, controller);
    }

    public Controller get(String path, Method method) {
        Map<Method, Controller> controllers = mapper.get(path);
        if (controllers == null) {
            return null;
        }
        return controllers.get(method);
    }

    public boolean contains(String path) {
        return mapper.containsKey(path);
    }

    public boolean contains(String path, Method method) {
        return contains(path) && mapper.get(path).containsKey(method);
    }
}