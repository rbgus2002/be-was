package controller;

import exception.BadRequestException;
import exception.CustomException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.MIME;
import view.View;

import static db.SessionStorage.isSessionValid;
import static http.Extension.HTML;
import static http.FilePath.*;
import static http.HttpMethod.POST;
import static utils.FileUtils.*;

public abstract class Controller {
    private final View view = new View();

    public HttpResponse.ResponseBuilder loadFileByRequest(HttpRequest httpRequest) {
        try {
            if (httpRequest.getMethod().equals(POST)) {
                return doPost(httpRequest);
            }
            String uri = httpRequest.getUri();
            if (uri.contains("?")) {
                return doGet(httpRequest);
            }
            if (uri.equals(FORM) && !isSessionValid(httpRequest.getSessionId())) {
                uri = LOGIN;
            }
            if (uri.equals(INDEX) || uri.equals(PROFILE) || uri.equals(LIST) || uri.equals(SHOW)) {
                return loadFileFromString(HttpStatus.OK, view.getDynamicView(httpRequest, uri), uri);
            }
            String[] uris = uri.split("\\.");
            String extension = uris[uris.length - 1];
            return loadFromPath(HttpStatus.OK, uri)
                    .setContentType(MIME.getMIME().get(extension));
        } catch (CustomException e) {
            return loadFromPath(e.getHttpStatus(), e.getFilePath())
                    .setContentType(MIME.getMIME().get(HTML));
        } catch (BadRequestException e) {
            return loadFromPath(HttpStatus.NOT_FOUND, NOT_FOUND_ERROR)
                    .setContentType(MIME.getMIME().get(HTML));
        } catch (Exception e) {
            return loadFileFromString(HttpStatus.INTERNAL_SERVER_ERROR, view.getErrorView(e.getMessage()), ERROR)
                    .setContentType(MIME.getMIME().get(HTML));
        }
    }

    public abstract HttpResponse.ResponseBuilder doGet(HttpRequest httpRequest);

    public abstract HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest);
}
