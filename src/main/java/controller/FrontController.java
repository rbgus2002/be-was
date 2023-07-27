package controller;

import controller.RestController;
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
    private final RestController restController;

    public FrontController(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        this.restController = new RestController();
    }

    public HttpResponse response() throws Exception {
        Handler handler = findHandler();
        return handler.runController(restController, httpRequest);
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
