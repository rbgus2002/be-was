package webserver.handlers;

import webserver.http.message.Mime;
import webserver.http.message.HttpHeaders;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.session.Session;
import webserver.model.Model;
import webserver.utils.ExtensionSeparator;
import webserver.utils.FileUtils;

import static webserver.http.message.HttpHeaders.ACCEPT;

public class StaticFileHandler implements Handler {
    @Override
    public HttpResponse handle(HttpRequest request, Session session, Model model) {
        if (request.containsHeader(ACCEPT)) {
            HttpResponse response = makeResponseUsingAccept(request);
            HttpHeaders headers = request.getHttpHeaders();
            if (!headers.getSingleValue(ACCEPT).equals(Mime.DEFAULT.getContentType()))
                return response;
        }
        return makeResponseUsingExt(request);
    }

    private HttpResponse makeResponseUsingExt(HttpRequest request) {
        String path = request.getURL().getPath();
        byte[] file = FileUtils.readFileFromStatic(path);
        String ext = ExtensionSeparator.separateExtension(path);
        Mime mime = Mime.findByExt(ext);
        return HttpResponse.okWithFile(file, mime);
    }

    private static HttpResponse makeResponseUsingAccept(HttpRequest request) {
        String path = request.getURL().getPath();
        String contentType = request.getHttpHeaders().getSingleValue(ACCEPT);
        byte[] file = FileUtils.readFileFromStatic(path);
        Mime mime = Mime.findByContentType(contentType);
        return HttpResponse.okWithFile(file, mime);
    }
}
