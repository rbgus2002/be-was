package webserver.handlers;

import webserver.http.Mime;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.utils.FileExtensionSeparator;
import webserver.utils.FileUtils;

import java.util.List;
import java.util.Map;

public class StaticFileHandler implements Handler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        if (request.getMetaData().containsKey("Accept")) {
            HttpResponse response = makeResponseUsingAccept(request);
            Map<String, List<String>> headers = response.getMetaData();
            if (!headers.get("Content-Type").get(0).equals(Mime.DEFAULT.getContentType()))
                return response;
        }
        return makeResponseUsingExt(request);
    }

    private HttpResponse makeResponseUsingExt(HttpRequest request) {
        String path = request.getURL().getPath();
        byte[] file = FileUtils.readFileFromStatic(path);
        String ext = FileExtensionSeparator.separateExtension(path);
        Mime mime = Mime.findByExt(ext);
        return HttpResponse.okWithFile(file, mime);
    }

    private static HttpResponse makeResponseUsingAccept(HttpRequest request) {
        String path = request.getURL().getPath();
        String contentType = request.getMetaData().get("Accept").get(0);
        byte[] file = FileUtils.readFileFromStatic(path);
        Mime mime = Mime.findByContentType(contentType);
        return HttpResponse.okWithFile(file, mime);
    }
}
