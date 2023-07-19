package webserver.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerMapper {
    private static final Map<String, Controller> handlerMapper = new ConcurrentHashMap<>();

    static {
        handlerMapper.put("/user/create", new UserController());
    }

    public static Controller findHandler(String requestUri) {
        if (requestUri.contains("?")) {
            requestUri = requestUri.substring(0, requestUri.indexOf("?"));
        }

        return handlerMapper.get(requestUri);
    }
}
