package handler;

import controller.BasicController;
import controller.Controller;
import controller.UserController;
import http.HttpRequest;

public class ControllerMappingHandler {

    UserController userController = new UserController();
    BasicController basicController = new BasicController();

    public Controller mappingController(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        if (uri.startsWith("/user")) {
            return userController;
        }
        return basicController;
    }
}
