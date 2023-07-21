package webserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResolveController {
    private final static ResolveController RESOLVE_CONTROLLER = new ResolveController();
    private final static Logger logger = LoggerFactory.getLogger(ResolveController.class);
    private final static Controller staticFileController = new StaticFileController();
    private final static Map<String, Controller> requestControllers = new HashMap<>() {{
        put("/user/create", new UserCreateController());
        put("static", staticFileController);
    }};

    public static ResolveController getInstance() {
        return RESOLVE_CONTROLLER;
    }

    public Controller resolveRequest(HttpRequest request) {
        return requestControllers.getOrDefault(request.path(), staticFileController);
    }
}
