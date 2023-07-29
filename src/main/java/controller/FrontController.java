package controller;

import controller.annotation.GetHandler;
import controller.annotation.Handler;
import controller.annotation.PostHandler;
import exception.HttpException;
import model.HttpRequest;
import model.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class FrontController {
    private final HttpRequest httpRequest;
    private final ControllerImpl controllerImpl;

    public FrontController(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        this.controllerImpl = new ControllerImpl();
    }

    public HttpResponse response() throws Exception {
        Handler handler = findHandler();
        return handler.runController(controllerImpl, httpRequest);
    }

    public Handler findHandler() {
        final List<Handler> handlers = new ArrayList<>();
        handlers.add(new GetHandler());
        handlers.add(new PostHandler(httpRequest));

        return handlers.stream()
                .filter(handler -> handler.matchHttpMethod(httpRequest.getMethod()))
                .findFirst()
                .orElseThrow(() -> new HttpException("sdf"));
    }
}
