package webserver.http.response;

import webserver.http.constant.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResponseMessageRender {
    public static HttpResponseMessage create404() throws IOException {
        HttpResponseMessage httpResponseMessage = new HttpResponseMessage();
        httpResponseMessage.setStatusLine(HttpStatus.NOT_FOUND);
        httpResponseMessage.setBody(Files.readAllBytes(Path.of("src/main/resources/templates/404.html")));
        return httpResponseMessage;
    }
}
