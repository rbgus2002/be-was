package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewResolver;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet {
    private static final Map<String, Controller> map = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String LOGIN_PATH = "/user/create";

    private static DispatcherServlet instance = null;

    public static DispatcherServlet getInstance(){
        if (instance == null) {
            initialize();
            logger.debug("map.size() = {}", map.size());
            return instance = new DispatcherServlet();
        }
        return instance;
    }

    private DispatcherServlet() {
    }

    public static void initialize(){
        map.put("/user/login", new LogInTestController());
        map.put(LOGIN_PATH, new LogInController());
        map.put("/user/list", new ListController());
        map.put("/user/list.html", new ListController());
    }

    public void service(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        String url = request.getUrl();
        Controller controller = map.get(url);
        String viewPath = url;
        if (controller != null) {
            viewPath = controller.process(request, response);
        }
        logger.debug("viewName = {}", viewPath);
        ViewResolver view = new ViewResolver(viewPath, response, request);
        view.service();
        view.render();
    }
}
