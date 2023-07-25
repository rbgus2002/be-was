package controller;

import exception.BadRequestException;
import exception.CustomException;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.MIME;
import view.IndexPage;
import view.ListPage;
import view.Page;
import view.ProfilePage;

import static exception.ExceptionList.INVALID_URI;
import static http.Extension.HTML;
import static http.FilePath.*;
import static http.HttpMethod.POST;
import static http.MIME.getExtension;
import static utils.FileIOUtils.*;

public abstract class Controller {
    private final Page page = new Page();
    private final ProfilePage profilePage = new ProfilePage();
    private final IndexPage indexPage = new IndexPage();
    private final ListPage listPage = new ListPage();

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
            if (extension.equals(HTML)) {
                if (uri.endsWith(INDEX))
                    return loadFileFromString(HttpStatus.OK, indexPage.getIndexPage(httpRequest), INDEX)
                            .setContentType(MIME.getMIME().get(HTML));
                if (uri.endsWith(PROFILE))
                    return loadFileFromString(HttpStatus.OK, profilePage.getProfilePage(httpRequest), PROFILE)
                            .setContentType(MIME.getMIME().get(HTML));
                if (uri.endsWith(LIST))
                    return loadFileFromString(HttpStatus.OK, listPage.getListPage(httpRequest), LIST)
                            .setContentType(MIME.getMIME().get(HTML));
            }
            return loadFromPath(HttpStatus.OK, uri)
                    .setContentType(MIME.getMIME().get(extension));
        } catch (CustomException e) {
            return loadFromPath(e.getHttpStatus(), e.getFilePath())
                    .setContentType(MIME.getMIME().get(HTML));
        } catch (BadRequestException e) {
            String errorPage = page.getErrorPage(e.getMessage());
            return loadFileFromString(HttpStatus.NOT_FOUND, errorPage, ERROR)
                    .setContentType(MIME.getMIME().get(HTML));
        } catch (Exception e) {
            return null;
        }
    }

    public abstract HttpResponse.ResponseBuilder doGet(String uri);

    public abstract HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest);
}
