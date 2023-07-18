package webserver;

import support.ControllerResolver;
import support.exception.BadRequestException;
import support.exception.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.MIME;
import webserver.response.strategy.*;

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

    public void doPost(HttpRequest request, HttpResponse response) throws InvocationTargetException, IllegalAccessException {
        String path = request.getRequestPath();

        interceptController(request, response, path);
    }

    private boolean interceptController(HttpRequest request, HttpResponse response, String path) throws InvocationTargetException, IllegalAccessException {
        try {
            if (ControllerResolver.invoke(path, request)) {
                response.setStatus(HttpStatus.FOUND);
                response.buildHeader(new Found(MAIN_PAGE));
                return true;
            }
        } catch (MethodNotAllowedException e) {
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
            response.buildHeader(new BadRequest());
            return true;
        } catch (BadRequestException e) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.buildHeader(new NoHeader());
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
