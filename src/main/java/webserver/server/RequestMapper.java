package webserver.server;

import com.google.common.collect.ImmutableMap;
import controller.Controller;
import controller.HomeController;
import controller.SignupController;


public class RequestMapper {
    private final ImmutableMap<String, Controller> map;
    private static final RequestMapper requestMapper = new RequestMapper();

    private RequestMapper() {
        map = ImmutableMap.<String, Controller>builder()
                .put("/", new HomeController())
                .put("/index.html", new HomeController())
                .put("/user/create", new SignupController())
                .build();
    }

    public static RequestMapper createRequestMapper() {
        return requestMapper;
    }
    public Controller getController(String url) {
        return map.get(url);
    }
}
