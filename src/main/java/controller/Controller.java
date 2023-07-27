package controller;

import exception.BadRequestException;
import exception.CustomException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.MIME;
import view.Page;

import static exception.ExceptionList.INVALID_URI;
import static http.Extension.HTML;
import static http.FilePath.*;
import static http.HttpMethod.POST;
import static http.MIME.getExtension;
import static utils.FileIOUtils.*;

public abstract class Controller {
    private final Page page = new Page();

    public HttpResponse.ResponseBuilder loadFileByRequest(HttpRequest httpRequest) {
        try {
            if (httpRequest.getMethod().equals(POST)) {
                return doPost(httpRequest);
            }
            String uri = httpRequest.getUri();
            if (uri.contains("?")) {
                return doGet(uri);
            }
            String[] uris = uri.split("\\.");
            String extension = uris[uris.length - 1];
            if (getExtension().stream().noneMatch(entry -> entry.getKey().equals(extension))) {
                throw new BadRequestException(INVALID_URI);
            }
            if (uri.endsWith(INDEX) || uri.endsWith(PROFILE) || uri.endsWith(LIST)) {
                return loadFileFromString(HttpStatus.OK, page.getDynamicPage(httpRequest, uri), uri);
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

    public abstract HttpResponse.ResponseBuilder doGet(String uri);

    public abstract HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest);
}
