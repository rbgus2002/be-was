package webserver.http.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.HttpMime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpRequest start line test")
class HttpRequestStartLineTest {

    @Test
    @DisplayName("start line에서 method, path, MIME이 올바르게 추출되어야 한다.")
    void extractStartLineInfo() {
        String startLine = "GET /index.html HTTP/1.1";

        HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(startLine);

        assertEquals(HttpMethod.GET, httpRequestStartLine.getHttpMethod());
        assertEquals("/index.html", httpRequestStartLine.getRequestPath());
        assertEquals(HttpMime.HTML, httpRequestStartLine.getMime());
    }

    @Test
    @DisplayName("start line의 query string에서 parameter가 올바르게 추출되어야 한다.")
    void parseQueryString() {
        String startLine = "GET /user/create?userId=user123&password=1234&name=userA&email=test@gmail.com HTTP/1.1";

        HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(startLine);

        assertEquals("user123", httpRequestStartLine.getParams().get("userId"));
        assertEquals("1234", httpRequestStartLine.getParams().get("password"));
        assertEquals("userA", httpRequestStartLine.getParams().get("name"));
        assertEquals("test@gmail.com", httpRequestStartLine.getParams().get("email"));
    }
}
