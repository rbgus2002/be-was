package webserver;

import controller.Controller;
import controller.HomeController;
import controller.SignupController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class RequestMapping {

    private static final Logger logger = LoggerFactory.getLogger(RequestMapping.class);
    private final ConcurrentHashMap<String, Controller> map = new ConcurrentHashMap<>();

    public RequestMapping() {
        logger.info("RequestMapping Create");
        map.put("/", new HomeController());
        map.put("/index.html", new HomeController());
        map.put("/user/create", new SignupController());
    }

    public Controller getController(HttpRequest req) {
        logger.info("RequestMapping GetController");
        return map.get(req.getUrl());
    }
}
