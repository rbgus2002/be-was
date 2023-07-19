package webserver.http.response;

import webserver.service.FileService;
import webserver.http.HttpStatusCode;
import webserver.http.request.HttpRequest;

import java.io.IOException;

public class ResponseHandler {
    private ResponseHandler() {}

    public static HttpResponse createResponse(HttpRequest request) {
        try {
            byte[] body = FileService.getStaticResource(request.getPath());
            return HttpResponse.of(request.getVersion(), HttpStatusCode.OK, body);
        } catch (IOException exception) {
            return HttpResponse.of(request.getVersion(), HttpStatusCode.NOT_FOUND, new byte[0]);
        }
    }
}
