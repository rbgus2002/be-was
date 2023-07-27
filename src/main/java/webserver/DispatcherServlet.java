package webserver;

import application.controller.Controller;
import application.controller.ControllerResolver;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

public class DispatcherServlet {
    private static DispatcherServlet instance;

    private static final ControllerResolver controllerResolver = ControllerResolver.getInstance();

    private DispatcherServlet() {
    }

    public static DispatcherServlet getInstance() {
        if (instance == null) {
            instance = new DispatcherServlet();
        }
        return instance;
    }

    public void dispatch(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Controller controller = resolveController(httpRequest);
        controller.process(httpRequest, httpResponse);
    }

    private Controller resolveController(HttpRequest httpRequest) {
        String path = httpRequest.getPath();
        String method = httpRequest.getMethod();
        return controllerResolver.resolve(path, method);
    }
}
