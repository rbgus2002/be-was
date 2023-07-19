package webserver;

import controller.Controller;
import controller.SignUpController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestMapper {
    private static final Map<String, Controller> controllerMap = new HashMap<>();
    private static final HttpRequestMapper requestMapper = new HttpRequestMapper();
    private HttpRequestMapper(){
        controllerMap.put("GET /user/create", new SignUpController());
    }

    public static HttpRequestMapper getInstance(){
        return requestMapper;
    }

    public Controller getController(String method, String url){
        return controllerMap.get(method + " " + url);
    }

}
