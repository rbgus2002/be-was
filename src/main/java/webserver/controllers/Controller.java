package webserver.controllers;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.enums.HttpResponseStatus;

public interface Controller {
    HttpResponse handle(HttpRequest request);

    default HttpResponse createErrorResponse(HttpRequest request, HttpResponseStatus status) {
        HttpResponse.Builder builder = HttpResponse.newBuilder();
        return builder.version(request.version())
                .status(status)
                .body(status.getBody().getBytes())
                .build();
    }

}
