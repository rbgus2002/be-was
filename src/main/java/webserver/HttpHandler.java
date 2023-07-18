package webserver;

import support.ControllerResolver;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.strategy.BadRequest;
import webserver.response.strategy.Found;
import webserver.response.strategy.NotFound;
import webserver.response.strategy.OK;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static webserver.WebPageReader.readByPath;

public class HttpHandler {

    public static final String MAIN_PAGE = "/index.html";

    public void doGet(HttpRequest request, HttpResponse response) throws IOException, InvocationTargetException, IllegalAccessException {
        String path = request.getRequestPath();

        // controller 검색
        try {
            if (ControllerResolver.invoke(path, request)) {
                response.setStatus(HttpStatus.FOUND);
                response.buildHeader(new Found(MAIN_PAGE));
                return;
            }
        } catch (IllegalArgumentException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.buildHeader(new BadRequest());
            return;
        }

        // 페이지 검색 및 반환
        try {
            byte[] body = readByPath(path);
            response.setStatus(HttpStatus.OK);
            response.buildHeader(new OK(makeContentType(path), body.length));
            response.setBody(body);
        } catch (IOException exception) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.buildHeader(new NotFound());
        }
    }

    private String makeContentType(String url) {
        String extension = url.substring(url.lastIndexOf("."));
        switch (extension) {
            case ".html":
                return "text/html";
            case ".css":
                return "text/css";
            case ".js":
                return "text/javascript";
            default:
                return "text/plain";
        }
    }

}
