package webserver;

import support.ControllerResolver;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.strategy.Found;
import webserver.response.strategy.OK;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static webserver.WebPageReader.readByPath;

public class HttpHandler {

    public static final String MAIN_PAGE = "/index.html";

    public void doGet(HttpRequest httpRequest, HttpResponse response) throws IOException, InvocationTargetException, IllegalAccessException {
        String path = httpRequest.getRequestPath();
        response.setStatus(HttpStatus.OK);

        // controller 검색
        if (ControllerResolver.invoke(path, httpRequest)) {
            response.setStatus(HttpStatus.FOUND);
            response.buildHeader(new Found(MAIN_PAGE));
            return;
        }

        // 페이지 검색 및 반환
        byte[] body = readByPath(path);
        response.buildHeader(new OK(makeContentType(path), body.length));
        response.setBody(body);
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
