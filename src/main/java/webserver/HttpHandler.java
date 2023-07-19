package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.ControllerResolver;
import support.annotation.ResponseStatus;
import support.exception.BadRequestException;
import support.exception.MethodNotAllowedException;
import support.exception.NotSupportedException;
import support.exception.ServerErrorException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.MIME;
import webserver.response.strategy.NotFound;
import webserver.response.strategy.OK;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static webserver.WebPageReader.readByPath;

public class HttpHandler {

    public static final String MAIN_PAGE = "/index.html";
    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

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
            ResponseStatus responseStatus = ControllerResolver.invoke(path, request, response);
            response.setStatus(responseStatus.status());
            response.appendHeader("Location", responseStatus.redirectionUrl());
            return true;
        } catch (NotSupportedException e) {
            return false;
        } catch (MethodNotAllowedException e) {
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
            return true;
        } catch (BadRequestException e) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            return true;
        } catch (ServerErrorException e) {
            response.setStatus(HttpStatus.SERVER_ERROR);
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
