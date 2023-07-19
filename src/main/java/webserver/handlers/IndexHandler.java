package webserver.handlers;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.utils.FileUtils;

public class IndexHandler implements Handler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        String path = request.getURL().getPath();
        byte[] file = FileUtils.readFileFromTemplate(path);
        return HttpResponse.okWithFile(file);
    }
}
