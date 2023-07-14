package utils;

import http.HttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static http.HttpRequestParser.parseUrlFromRequestLine;

class HttpRequestTest {

    private final String requestLine = "GET /create?userId=jaehong&password=123 HTTP/1.1";
    private HttpRequest httpRequest;

    @BeforeEach
    void setup() {
        httpRequest = new HttpRequest(requestLine);
    }

    @Test
    @DisplayName("Request line 으로부터 method 를 분리한다")
    void getMethod() {
        String method = httpRequest.getMethod();
        assertThat(method).isEqualTo("GET");
    }

    @Test
    @DisplayName("Request line 으로부터 url 를 분리한다")
    void getUrl() {
        String url = parseUrlFromRequestLine(requestLine);
        assertThat(url).isEqualTo("/create?userId=jaehong&password=123");
    }

    @Test
    @DisplayName("Request line 으로부터 path 를 분리한다")
    void getPath() {
        String path = httpRequest.getPath();
        assertThat(path).isEqualTo("/create");
    }

    @Test
    @DisplayName("Request line 으로부터 파라미터를 분리한다")
    void getParams() {
        Map<String, String> params = httpRequest.getParams();
        assertThat(params).hasSize(2).contains(entry("userId", "jaehong"), entry("password", "123"));
    }

    @Test
    @DisplayName("Request line 으로부터 HTTP version 을 분리한다")
    void getVersion() {
        String version = httpRequest.getVersion();
        assertThat(version).isEqualTo("HTTP/1.1");
    }
}