package mapper;

import com.google.common.net.HttpHeaders;
import model.HttpHeader;
import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpStatusCode;
import model.enums.MIME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.StringUtils.NO_CONTENT;

public class ResponseMapper {
    private static final String PAGE_NOT_FOUND = "요청하신 페이지가 없습니다.";
    private static final String PAGE_BAD_REQUEST = "잘못된 요청입니다.";
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    public static HttpResponse createNoBodyHttpResponse(HttpRequest request, HttpStatusCode statusCode) {
        return createHttpResponse(request, statusCode, NO_CONTENT.getBytes(), MIME.DEFAULT);
    }

    public static HttpResponse createHttpResponse(HttpRequest request, HttpStatusCode statusCode, byte[] body, MIME extension) {
        int lengthOfBody = body.length;

        HttpHeader httpHeader = createHeader(
                List.of(HttpHeaders.CONTENT_TYPE, extension.getContentType()),
                List.of(HttpHeaders.CONTENT_LENGTH, String.valueOf(lengthOfBody))
        );
        return HttpResponse.of(request, statusCode, httpHeader, body);
    }

    public static HttpResponse createRedirectResponse(HttpRequest request, HttpStatusCode statusCode, String redirectPath) {
        HttpHeader httpHeader = createHeader(
                List.of(HttpHeaders.LOCATION, redirectPath)
        );
        return HttpResponse.of(request, statusCode, httpHeader, NO_CONTENT.getBytes());
    }

    @SafeVarargs
    private static HttpHeader createHeader(List<String>... contents) {
        Map<String, String> header = new HashMap<>();
        for (List<String> content : contents) {
            String key = content.get(KEY_INDEX);
            String value = content.get(VALUE_INDEX);
            header.put(key, value);
        }
        return HttpHeader.of(header);
    }

    public static HttpResponse createNotFoundResponse(HttpRequest httpRequest) {
        return createHttpResponse(httpRequest, HttpStatusCode.NOT_FOUND, PAGE_NOT_FOUND.getBytes(), MIME.DEFAULT);
    }

    public static HttpResponse createBadRequestResponse(HttpRequest httpRequest) {
        return createHttpResponse(httpRequest, HttpStatusCode.BAD_REQUEST, PAGE_BAD_REQUEST.getBytes(), MIME.DEFAULT);
    }
}
