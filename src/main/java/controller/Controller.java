package controller;

import exception.BadRequestException;
import exception.CustomException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.MIME;
import view.Page;

import static db.SessionStorage.isSessionValid;
import static http.Extension.HTML;
import static http.FilePath.*;
import static http.HttpMethod.POST;
import static utils.FileUtils.*;

public abstract class Controller {
    private final Page page = new Page();

    public HttpResponse.ResponseBuilder loadFileByRequest(HttpRequest httpRequest) {
        try {
            if (httpRequest.getMethod().equals(POST)) {
                return doPost(httpRequest);
            }
            String uri = httpRequest.getUri();
            if (uri.contains("?")) {
                return doGet(httpRequest);
            }
            String[] uris = uri.split("\\.");
            String extension = uris[uris.length - 1];
            if (uri.endsWith(INDEX) || uri.endsWith(PROFILE) || uri.endsWith(LIST)) {
                return loadFileFromString(HttpStatus.OK, page.getDynamicPage(httpRequest, uri), uri);
            }
            if (uri.endsWith(FORM) && !isSessionValid(httpRequest.getSessionId())) {
                uri = LOGIN;
            }
            return loadFromPath(HttpStatus.OK, uri)
                    .setContentType(MIME.getMIME().get(extension));
        } catch (CustomException e) {
            return loadFromPath(e.getHttpStatus(), e.getFilePath())
                    .setContentType(MIME.getMIME().get(HTML));
        } catch (BadRequestException e) {
            return loadFileFromString(HttpStatus.NOT_FOUND, page.getErrorPage(e.getMessage()), ERROR)
                    .setContentType(MIME.getMIME().get(HTML));
        } catch (Exception e) {
            return null;
        }
    }

    public abstract HttpResponse.ResponseBuilder doGet(HttpRequest httpRequest);

    public abstract HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest);
}
