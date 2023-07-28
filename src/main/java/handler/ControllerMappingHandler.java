package handler;

import controller.BasicController;
import controller.Controller;
import controller.BoardController;
import controller.UserController;
import http.HttpRequest;

public class ControllerMappingHandler {

    UserController userController = UserController.getInstance();
    BoardController boardController = BoardController.getInstance();
    BasicController basicController = BasicController.getInstance();

    private ControllerMappingHandler() {
    }

    private static class Holder {
        private static final ControllerMappingHandler INSTANCE = new ControllerMappingHandler();
    }

    public static ControllerMappingHandler getInstance() {
        return Holder.INSTANCE;
    }

    public Controller mappingController(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        if (uri.startsWith("/user")) {
            return userController;
        }
        if (uri.startsWith("/qna")) {
            return boardController;
        }
        return basicController;
    }
}
