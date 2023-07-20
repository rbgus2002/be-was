package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class FrontController {
    private final HttpRequest httpRequest;
    private final DataOutputStream dos;

    public FrontController(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        this.httpRequest = httpRequest;
        this.dos = dos;
    }

    public void doDispatch() throws IOException {
        Controller controller = HandlerMapper.findHandler(httpRequest.getRequestUri());
        if (controller == null) {
            HttpResponse httpResponse = HttpResponse.createStatic(httpRequest.getRequestUri());
            httpResponse.responseStatic(dos);
            return;
        }

        HttpResponse httpResponse = HttpResponse.createRedirect();
        controller.doGet(httpRequest, httpResponse);
        httpResponse.responseDynamic(dos);
    }

}
