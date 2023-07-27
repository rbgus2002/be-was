package mapper;

import com.google.common.net.HttpHeaders;
import model.HttpHeader;
import model.HttpResponse;
import model.enums.HttpStatusCode;
import model.enums.Mime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.StringUtils.NO_CONTENT;

public class ResponseMapper {
    private static final String PAGE_NOT_FOUND = "요청하신 페이지가 없습니다.";
    private static final String PAGE_BAD_REQUEST = "잘못된 요청입니다.";
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    public static HttpResponse createNoBodyHttpResponse(HttpStatusCode statusCode) {
        return createHttpResponse(statusCode, NO_CONTENT.getBytes(), Mime.DEFAULT);
    }

    public static HttpResponse createHttpResponse(HttpStatusCode statusCode, byte[] body, Mime extension) {
        int lengthOfBody = body.length;

        HttpHeader httpHeader = createHeader(
                List.of(HttpHeaders.CONTENT_TYPE, extension.getContentType()),
                List.of(HttpHeaders.CONTENT_LENGTH, String.valueOf(lengthOfBody))
        );
        return HttpResponse.of(statusCode, httpHeader, body);
    }

    public static HttpResponse createRedirectResponse(HttpStatusCode statusCode, String redirectPath) {
        HttpHeader httpHeader = createHeader(
                List.of(HttpHeaders.LOCATION, redirectPath)
        );
        return HttpResponse.of(statusCode, httpHeader, NO_CONTENT.getBytes());
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

    public static HttpResponse createNotFoundResponse() {
        return createHttpResponse(HttpStatusCode.NOT_FOUND, PAGE_NOT_FOUND.getBytes(), Mime.DEFAULT);
    }

    public static HttpResponse createBadRequestResponse() {
        return createHttpResponse(HttpStatusCode.BAD_REQUEST, PAGE_BAD_REQUEST.getBytes(), Mime.DEFAULT);
    }
}
