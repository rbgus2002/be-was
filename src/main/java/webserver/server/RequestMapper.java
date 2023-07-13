package webserver.server;

import controller.Controller;
import controller.HomeController;
import controller.SignupController;

import java.util.concurrent.ConcurrentHashMap;

public class RequestMapper {
    private final ConcurrentHashMap<String, Controller> map = new ConcurrentHashMap<>();

    public RequestMapper() {
        map.put("/", new HomeController());
        map.put("/index.html", new HomeController());
        map.put("/user/create", new SignupController());
    }

    public Controller getController(String url) {
        return map.get(url);
    }
}
