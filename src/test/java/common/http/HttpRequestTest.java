package common.http;

import common.enums.RequestMethod;
import common.wrapper.Headers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static common.enums.RequestMethod.*;
import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

    @Test
    @DisplayName("요청 메서드를 가져올 수 있다.")
    void getRequestMethod() {
        HttpRequest request = createRequest(GET, "/index.html", "key1: value1\r\nkey2: value2", "");
        Assertions.assertEquals(GET, request.getRequestMethod());
    }



    private HttpRequest createRequest(RequestMethod rm, String uri, String headerString, String bodyString) {
        RequestLine requestLine = new RequestLine(rm, new Uri(uri));
        Headers headers = new Headers(headerString);
        RequestBody requestBody = new RequestBody(bodyString);

        return new HttpRequest(requestLine, headers, requestBody);
    }
}