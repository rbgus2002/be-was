package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.web.ControllerResolver;
import support.exception.FoundException;
import support.exception.HttpException;
import support.exception.NotSupportedException;
import support.web.ViewResolver;
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

        try {
            ViewResolver.buildView(response, path);
            response.setStatus(HttpStatus.OK);
        } catch (IOException e) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.buildHeader(new NotFound());
        }
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
            String viewName = ControllerResolver.invoke(path, request, response);
            response.setStatus(HttpStatus.FOUND);
            response.appendHeader("Location", viewName);
            return true;
        } catch (NotSupportedException e) {
            return false;
        } catch (FoundException e) {
            response.setStatus(e.getHttpStatus());
            response.appendHeader("Location", e.getRedirectionUrl());
            return true;
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatus());
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
