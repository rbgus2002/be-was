package controller;

import exception.BadRequestException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.MIME;
import view.Page;

import static exception.ExceptionList.NOT_EXIST_USER;
import static http.Extension.HTML;
import static http.HttpMethod.POST;
import static utils.FileIOUtils.*;
import static http.FilePath.LOGIN_FAILED;
import static http.FilePath.WRONG_ACCESS;

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
            if (MIME.getMIME().entrySet().stream().noneMatch(entry -> entry.getKey().equals(extension))) {
                return loadTemplatesFromPath(HttpStatus.NOT_FOUND, WRONG_ACCESS);
            }
            if (extension.equals(HTML)) {
                return loadTemplatesFromPath(HttpStatus.OK, uri)
                        .setContentType(MIME.getMIME().get(HTML));
            }
            return loadStaticFromPath(HttpStatus.OK, uri)
                    .setContentType(MIME.getMIME().get(extension));
        } catch (BadRequestException e) {
            if (e.getMessage().equals(NOT_EXIST_USER))
                return loadTemplatesFromPath(HttpStatus.UNAUTHORIZED, LOGIN_FAILED);
            String errorPage = page.getErrorPage(e.getMessage());
            return loadErrorFromPath(HttpStatus.NOT_FOUND, errorPage)
                    .setContentType(MIME.getMIME().get(HTML));
        } catch (Exception e) {
            return null;
        }
    }

    public abstract HttpResponse.ResponseBuilder doGet(String uri);

    public abstract HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest);
}
