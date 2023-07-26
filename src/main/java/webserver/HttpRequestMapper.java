package webserver;

import controller.Controller;
import controller.LoginController;
import controller.SignUpController;
import controller.view.Index;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestMapper {
    private final Map<String, Controller> controllerMap;
    private static HttpRequestMapper requestMapper;

    private HttpRequestMapper() {
        controllerMap = new HashMap<>();
        controllerMap.put("POST /user/create", SignUpController.getInstance());
        controllerMap.put("POST /user/login", LoginController.getInstance());
        controllerMap.put("GET /index.html", Index.getInstance());
    }

    public static HttpRequestMapper getInstance() {
        if (requestMapper == null) {
            requestMapper = new HttpRequestMapper();
        }
        return requestMapper;
    }

    public Controller getController(String method, String url) {
        return controllerMap.get(method + " " + url);
    }

}
