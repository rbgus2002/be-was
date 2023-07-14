package service;

import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpStatusCode;

public class ResponseService {
    private static final String PAGE_NOT_FOUND = "요청하신 페이지가 없습니다.";
    private static final String PAGE_BAD_REQUEST = "잘못된 요청입니다.";

    public HttpResponse createHttpResponse(HttpRequest request, HttpStatusCode statusCode, String body) {
        return HttpResponse.of(request, statusCode, body);
    }

    public HttpResponse createNotFoundResponse(HttpRequest httpRequest) {
        return createHttpResponse(httpRequest, HttpStatusCode.NOT_FOUND, PAGE_NOT_FOUND);
    }

    public HttpResponse createBadRequestResponse(HttpRequest httpRequest) {
        return createHttpResponse(httpRequest, HttpStatusCode.BAD_REQUEST, PAGE_BAD_REQUEST);
    }
}
