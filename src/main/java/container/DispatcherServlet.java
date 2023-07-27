package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewResolver;
import webserver.ControllerScanner;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class DispatcherServlet {
    private final Map<String, Controller> map;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    public static DispatcherServlet getInstance() {
        return Lazy.INSTANCE;
    }

    private static class Lazy {
        private static final DispatcherServlet INSTANCE = new DispatcherServlet();
    }
    private DispatcherServlet() {
        map = ControllerScanner.scan();
    }

    public void service(HTTPServletRequest request, HTTPServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException, InstantiationException {
        String url = request.getUrl();
        String viewPath;
        Controller controller = map.getOrDefault(url, new StaticController());
        viewPath = controller.process(request, response);
        ViewResolver view = new ViewResolver(viewPath, response, request);
        view.service();
        view.render();
    }

}
