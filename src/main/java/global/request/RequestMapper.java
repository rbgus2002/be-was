package global.request;

import controller.Controller;
import exception.BadRequestException;
import global.constant.Headers;
import global.constant.HttpMethod;
import global.constant.StatusCode;
import global.handler.GetHandler;
import global.handler.Handler;
import global.handler.PostHandler;
import global.response.ResponseEntity;
import global.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;


public class RequestMapper {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;
    private final SessionUtil sessionUtil;
    private final Controller controller;

    public RequestMapper(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody, SessionUtil sessionUtil) {
        this.requestLine = requestLine;
        this.requestBody = requestBody;
        this.requestHeader = requestHeader;
        this.sessionUtil = sessionUtil;
        this.controller = new Controller();
    }

    public byte[] response() throws Exception {
        final HttpMethod httpMethod = requestLine.getHttpMethod();
        final Handler handler = findHandler(httpMethod);
        try {
            return handler.startController(requestLine, controller);
        } catch (Exception e) {
            return ResponseEntity
                    .statusCode(StatusCode.OK)
                    .addHeaders(Headers.LOCATION, requestLine.getUri())
                    .responseResource(requestLine.getUri())
                    .build();
        }
    }

    private Handler findHandler(HttpMethod httpMethod) {
        final List<Handler> handlers = new ArrayList<>();
        handlers.add(new GetHandler(requestHeader, requestBody, sessionUtil));
        handlers.add(new PostHandler(requestHeader, requestBody, sessionUtil));

        return handlers.stream()
                .filter(handler -> handler.matchHttpMethod(httpMethod))
                .findFirst()
                .orElseThrow(BadRequestException::new);
    }
}