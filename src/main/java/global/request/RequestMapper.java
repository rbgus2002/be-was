package global.request;

import controller.Controller;
import exception.BadRequestException;
import global.constant.HttpMethod;
import global.handler.GetHandler;
import global.handler.Handler;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {
    private final RequestLine requestLine;
    private final RequestBody requestBody;
    private final Controller controller;

    public RequestMapper(RequestLine requestLine, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestBody = requestBody;
        this.controller = new Controller();
    }

    public String response() throws Exception {
        final HttpMethod httpMethod = requestLine.getHttpMethod();
        final Handler handler = findHandler(httpMethod);
        return handler.startController(requestLine, controller);
    }

    private Handler findHandler(HttpMethod httpMethod) {
        final List<Handler> handlers = new ArrayList<>();
        handlers.add(new GetHandler());

        return handlers.stream()
                .filter(handler -> handler.matchHttpMethod(httpMethod))
                .findFirst()
                .orElseThrow(BadRequestException::new);
    }
}