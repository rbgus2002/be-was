package webserver.controllers;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.enums.HttpResponseStatus;

import java.util.Map;

import static webserver.http.enums.HttpResponseStatus.FOUND;
import static webserver.http.enums.HttpResponseStatus.OK;

public interface Controller {

    // TODO: Exception으로 빼서 handle?
    default HttpResponse createErrorResponse(HttpRequest request, HttpResponseStatus status) {
        HttpResponse.Builder builder = HttpResponse.newBuilder();
        return builder.version(request.version())
                .status(status)
                .build();
    }

    default HttpResponse createFoundResponse(HttpRequest request, String redirect) {
        HttpResponse.Builder builder = HttpResponse.newBuilder();
        return builder.version(request.version())
                .status(FOUND)
                .redirect(redirect)
                .build();
    }

    default HttpResponse createOkResponse(HttpRequest request, String filePath, Map<String, String> attr) {
        HttpResponse.Builder builder = HttpResponse.newBuilder();
        return builder.version(request.version())
                .status(OK)
                .fileName(filePath)
                .setAttribute(attr)
                .build();
    }

}
