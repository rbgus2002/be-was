package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.utils.ContentTypeResolver;
import webserver.utils.FileUtils;
import webserver.utils.HttpField;

import java.io.IOException;

public class FileController {
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String filePath = httpRequest.get(HttpField.PATH);

        filePath = FileUtils.preprocessFilePath(filePath);

        byte[] body = FileUtils.readFile(filePath);
        HttpStatus status = resolveHttpStatus(filePath);
        String contentType = ContentTypeResolver.getContentType(filePath);

        httpResponse.setStatus(status);
        httpResponse.set(HttpField.CONTENT_TYPE, contentType);
        httpResponse.set(HttpField.CONTENT_LENGTH, body.length);
        httpResponse.setBody(body);
    }

    private static HttpStatus resolveHttpStatus(String path) {
        if (path.equals("/404.html")) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }
}
