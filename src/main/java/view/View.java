package view;

import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;
import common.http.ResponseLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static common.enums.ContentType.PLAIN;

public class View {
    private final String viewPath;

    public View(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) {
        ResponseLine responseLine = new ResponseLine(request.getVersion());
        Map<String, String> headers = new HashMap<>();
        byte[] body = new byte[0];

        if (viewPath == null) {
            responseLine.setResponseCode(ResponseCode.OK);
            response.setResponseLine(responseLine);
            return;
        }

        try {
            body = Files.readAllBytes(new File(viewPath).toPath());
            responseLine.setResponseCode(ResponseCode.OK);

            headers.put("Content-Type", request.getContentType().getDescription());
            headers.put("Content-Length", String.valueOf(body.length));
        } catch (IOException e) {
            body = new byte[0];
            responseLine.setResponseCode(ResponseCode.BAD_REQUEST);

            headers.put("Content-Type", PLAIN.getDescription());
            headers.put("Content-Length", String.valueOf(body.length));
        } finally {
            response.setResponseLine(responseLine);
            response.setHeaders(headers);
            response.setBody(body);
        }
    }
}