package global.request;

import controller.Controller;
import exception.BadRequestException;
import global.constant.Headers;
import global.constant.HttpMethod;
import global.constant.StatusCode;
import global.handler.GetHandler;
import global.handler.Handler;
import global.response.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static global.constant.ContentType.existContentType;

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
        if (isFileType(requestLine)) {
            return ResponseEntity
                    .statusCode(StatusCode.OK)
                    .addHeaders(Headers.LOCATION, requestLine.getUri())
                    .responseResource(requestLine.getUri())
                    .build();
        }
        return handler.startController(requestLine, controller);
    }

    private boolean isFileType(RequestLine requestLine) {
        String path = requestLine.getUri();

        int dotIndex = path.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < path.length() - 1) {
            String extension = path.substring(dotIndex + 1).toLowerCase();
            return existContentType(extension);
        }

        return false;
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