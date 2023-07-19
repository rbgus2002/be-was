package webserver;

import support.ControllerResolver;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.MIME;
import webserver.response.strategy.BadRequest;
import webserver.response.strategy.Found;
import webserver.response.strategy.NotFound;
import webserver.response.strategy.OK;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static webserver.WebPageReader.readByPath;

public class HttpHandler {

    public static final String MAIN_PAGE = "/index.html";

    public void doGet(HttpRequest request, HttpResponse response) throws InvocationTargetException, IllegalAccessException {
        String path = request.getRequestPath();

        if (interceptController(request, response, path)) {
            return;
        }

        searchAndReturnPage(response, path);
    }

    private boolean interceptController(HttpRequest request, HttpResponse response, String path) throws InvocationTargetException, IllegalAccessException {
        try {
            if (ControllerResolver.invoke(path, request)) {
                response.setStatus(HttpStatus.FOUND);
                response.buildHeader(new Found(MAIN_PAGE));
                return true;
            }
        } catch (IllegalArgumentException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.buildHeader(new BadRequest());
            return true;
        }
        return false;
    }

    private void searchAndReturnPage(HttpResponse response, String path) {
        try {
            byte[] body = readByPath(path);
            response.setStatus(HttpStatus.OK);
            String extension = path.substring(path.lastIndexOf("."));
            response.buildHeader(new OK(MIME.getContentType(extension), body.length));
            response.setBody(body);
        } catch (IOException exception) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.buildHeader(new NotFound());
        }
    }

}
