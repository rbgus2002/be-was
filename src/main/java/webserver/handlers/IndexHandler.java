package webserver.handlers;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.utils.FileUtils;

public class IndexHandler implements Handler {
    private static final String INDEX_FILE_PATH = "src/main/resources/templates/index.html";
    @Override
    public HttpResponse handle(HttpRequest request) {
        byte[] file = FileUtils.readFile(INDEX_FILE_PATH);
        return HttpResponse.okWithFile(file);
    }
}
