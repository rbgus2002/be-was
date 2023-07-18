package mapper;

import model.HttpHeader;
import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpStatusCode;
import model.enums.MIME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseMapper {
    private static final String PAGE_NOT_FOUND = "요청하신 페이지가 없습니다.";
    private static final String PAGE_BAD_REQUEST = "잘못된 요청입니다.";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";

    public HttpResponse createHttpResponse(HttpRequest request, HttpStatusCode statusCode, String body, MIME extension) {
        int lengthOfBody = body.getBytes().length;

        HttpHeader httpHeader = createHeader(
                List.of(HEADER_CONTENT_TYPE, extension.getContentType()),
                List.of(HEADER_CONTENT_LENGTH, String.valueOf(lengthOfBody))
        );

        return HttpResponse.of(request, statusCode, httpHeader, body);
    }

    public HttpResponse createRedirectResponse(HttpRequest request, HttpStatusCode statusCode, String redirectPath) {
        HttpHeader httpHeader = createHeader(
                List.of("Location", redirectPath)
        );
        return HttpResponse.of(request, statusCode, httpHeader, "");
    }

    @SafeVarargs
    private HttpHeader createHeader(List<String>... contents) {
        Map<String, String> header = new HashMap<>();
        for (List<String> content : contents) {
            String key = content.get(0);
            String value = content.get(1);
            header.put(key, value);
        }
        return HttpHeader.of(header);
    }

    public HttpResponse createNotFoundResponse(HttpRequest httpRequest) {
        return createHttpResponse(httpRequest, HttpStatusCode.NOT_FOUND, PAGE_NOT_FOUND, MIME.JSON);
    }

    public HttpResponse createBadRequestResponse(HttpRequest httpRequest) {
        return createHttpResponse(httpRequest, HttpStatusCode.BAD_REQUEST, PAGE_BAD_REQUEST, MIME.JSON);
    }
}
