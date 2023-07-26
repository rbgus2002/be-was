package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpRequest headers test")
class HttpRequestHeadersTest {

    @Test
    @DisplayName("headers 문자열을 Map으로 파싱해야 한다.")
    void parseRequestBody() {
        String headersString = "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 59\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n";

        HttpRequestHeaders httpRequestHeaders = new HttpRequestHeaders(headersString);

        assertEquals("localhost:8080", httpRequestHeaders.getHeader().get("Host"));
        assertEquals("keep-alive", httpRequestHeaders.getHeader().get("Connection"));
        assertEquals("59", httpRequestHeaders.getHeader().get("Content-Length"));
        assertEquals("application/x-www-form-urlencoded", httpRequestHeaders.getHeader().get("Content-Type"));
        assertEquals("*/*", httpRequestHeaders.getHeader().get("Accept"));
    }
}
