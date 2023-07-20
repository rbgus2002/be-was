package webserver.controllers;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import static utils.ExceptionUtils.getErrorMessage;

public interface Controller {
    HttpResponse handle(HttpRequest request);

    static HttpResponse createErrorResponse(HttpRequest request, int statusCode) {
        HttpResponse.Builder builder = HttpResponse.newBuilder();
        return builder.version(request.version())
                .statusCode(statusCode)
                .body(getErrorMessage(statusCode).getBytes())
                .build();
    }

}
