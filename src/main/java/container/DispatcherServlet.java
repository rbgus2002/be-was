package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewResolver;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class DispatcherServlet {
    private static final ConcurrentHashMap<String, Controller> map = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String LOGIN_PATH = "/user/create";

    public DispatcherServlet() {
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
