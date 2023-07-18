package webserver;

import support.ControllerResolver;
import support.exception.BadRequestException;
import support.exception.MethodNotAllowedException;
import support.exception.NotSupportedException;
import support.exception.ServerErrorException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.MIME;
import webserver.response.strategy.Found;
import webserver.response.strategy.NoHeader;
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

    public void doPost(HttpRequest request, HttpResponse response) throws InvocationTargetException, IllegalAccessException {
        String path = request.getRequestPath();

        if (interceptController(request, response, path)) {
            return;
        }

        response.setStatus(HttpStatus.NOT_FOUND);
        response.buildHeader(new NotFound());
    }

    private boolean interceptController(HttpRequest request, HttpResponse response, String path) {
        try {
            ControllerResolver.invoke(path, request);
            response.setStatus(HttpStatus.FOUND);
            response.buildHeader(new Found(MAIN_PAGE));
            return true;
        } catch (NotSupportedException e) {
            return false;
        } catch (MethodNotAllowedException e) {
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
            response.buildHeader(new NoHeader());
            return true;
        } catch (BadRequestException e) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.buildHeader(new NoHeader());
            return true;
        } catch (ServerErrorException e) {
            response.setStatus(HttpStatus.SERVER_ERROR);
            response.buildHeader(new NoHeader());
            return true;
        }
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
