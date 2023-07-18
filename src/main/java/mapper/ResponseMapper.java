package mapper;

import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpStatusCode;
import model.enums.MIME;

public class ResponseMapper {
    private static final String PAGE_NOT_FOUND = "요청하신 페이지가 없습니다.";
    private static final String PAGE_BAD_REQUEST = "잘못된 요청입니다.";

    public HttpResponse createHttpResponse(HttpRequest request, HttpStatusCode statusCode, String body, MIME extension) {
        return HttpResponse.of(request, statusCode, body, extension);
    }

    public HttpResponse createNotFoundResponse(HttpRequest httpRequest) {
        return createHttpResponse(httpRequest, HttpStatusCode.NOT_FOUND, PAGE_NOT_FOUND, MIME.JSON);
    }

    public HttpResponse createBadRequestResponse(HttpRequest httpRequest) {
        return createHttpResponse(httpRequest, HttpStatusCode.BAD_REQUEST, PAGE_BAD_REQUEST, MIME.JSON);
    }
}
