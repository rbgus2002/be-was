package webserver.httpcontroller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultController extends HttpController {
    @Override
    public String process(HttpRequest request, HttpResponse response) throws IOException {
        return request.getUrl();
    }
}
