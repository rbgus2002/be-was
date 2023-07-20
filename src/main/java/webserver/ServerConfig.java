package webserver;

import controller.Controller;

public class ServerConfig {
    public static final String STATIC_PATH = "./src/main/resources/static";
    public static final String TEMPLATE_PATH = "./src/main/resources/templates";
    public static final String ERROR_PAGE = "/error.html";
    public static final Class<Controller> CONTROLLER_CLASS = Controller.class;
}
