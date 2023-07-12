package webserver;

import controller.Controller;
import controller.HomeController;

import java.util.HashMap;

public class RequestMapping {

    private final HashMap<String, Controller> map = new HashMap<>();

    public RequestMapping() {
        map.put("/", new HomeController());
        map.put("/index.html", new HomeController());

    }

    public Controller getController(HttpRequest req) {
        return map.get(req.getUrl());
    }
}
