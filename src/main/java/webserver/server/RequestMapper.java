package webserver.server;

import controller.Controller;
import controller.HomeController;
import controller.SignupController;

import java.util.HashMap;

public class RequestMapper {
    private final HashMap<String, Controller> map = new HashMap<>();
    private static final RequestMapper requestMapper = new RequestMapper();

    private RequestMapper() {
        map.put("/", new HomeController());
        map.put("/index.html", new HomeController());
        map.put("/user/create", new SignupController());
    }

    public static RequestMapper createRequestMapper() {
        return requestMapper;
    }
    public Controller getController(String url) {
        return map.get(url);
    }
}
