package webserver.httpcontroller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultController extends HttpController {
    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException {
        Path path;
        byte[] body;
        try {
            path = Paths.get("src/main/resources/static" + request.getUrl());
            body = Files.readAllBytes(path);
        } catch (IOException e) {
            path = Paths.get("src/main/resources/templates" + request.getUrl());
            body = Files.readAllBytes(path);
        }
        String filename = path.getFileName().toString();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        response.setBody(body, extension);
    }
}
