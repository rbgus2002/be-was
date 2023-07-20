package webserver.controllers;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticFileController implements Controller {

    @Override
    public HttpResponse handle(HttpRequest request) {
        String fileName = request.uri();

        String path = System.getProperty("user.dir") + "/src/main/resources/templates/" + fileName;
        if (!request.contentType().equals("text/html"))
            // .html이 아닌 경우 static 쪽을 살펴보아야 한다.
            path = System.getProperty("user.dir") + "/src/main/resources/static" + fileName;

        HttpResponse.Builder builder = HttpResponse.newBuilder();

        // TODO: file이 존재하지 않는 경우 404 response return
        byte[] body;
        try {
            body = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return Controller.createErrorResponse(request, 404);
        }

        builder.version(request.version())
                .statusCode(200)
                .contentType(request.contentType())
                .body(body);

        return builder.build();
    }
}
