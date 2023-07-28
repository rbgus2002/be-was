package webserver;

public class ServerConfig {
    public static final String STATIC_PATH = "./src/main/resources/static";
    public static final String TEMPLATE_PATH = "./src/main/resources/templates";
    public static final String BAD_REQUEST_PAGE = "/errors/400.html";
    public static final String UNAUTHORIZED_PAGE = "/errors/401.html";
    public static final String NOT_FOUND_PAGE = "/errors/404.html";
    public static final String METHOD_NOT_ALLOWED_PAGE = "/errors/405.html";
    public static final String INTERNAL_SERVER_ERROR_PAGE = "/errors/500.html";
    public static final Class<Controller> CONTROLLER_CLASS = Controller.class;
}
