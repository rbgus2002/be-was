package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


class HttpRequestTest {
    @Test
    @DisplayName("InputStream으로부터 Get 요청에 대한 HttpRequest 객체를 생성한다.")
    void createHttpRequest() {
        // given
        String httpGetRequest = "GET /index.html HTTP/1.1\r\n" +
                "Content-Type: application/json\r\n" +
                "User-Agent: PostmanRuntime/7.32.3\r\n" +
                "Accept: */*\r\n" +
                "Postman-Token: cbe20735-a35b-44b5-920d-0cef0c894b21\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 22\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(httpGetRequest.getBytes());

        // when
        HttpRequest request = HttpRequest.from(inputStream);

        // then
        assertAll("GET",
                () -> assertEquals(httpGetRequest, request.toString()),
                () -> assertEquals("/index.html", request.getPath()),
                () -> assertEquals("HTTP/1.1", request.getVersion()));
    }

    @Test
    @DisplayName("InputStream으로부터 Post 요청에 대한 HttpRequest 객체를 생성한다.")
    void createHttpRequestWithBody() {
        // given
        String httpPostRequest = "POST / HTTP/1.1\r\n" +
                "Content-Type: application/json\r\n" +
                "User-Agent: PostmanRuntime/7.32.3\r\n" +
                "Accept: */*\r\n" +
                "Postman-Token: cbe20735-a35b-44b5-920d-0cef0c894b21\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 22\r\n" +
                "\r\n" +
                "{\r\n" +
                "    \"test\": \"test\"\r\n" +
                "}";

        InputStream inputStream = new ByteArrayInputStream(httpPostRequest.getBytes());

        // when
        HttpRequest request = HttpRequest.from(inputStream);

        // then
        assertAll("POST",
                () -> assertEquals(httpPostRequest, request.toString()),
                () -> assertEquals("/", request.getPath()),
                () -> assertEquals("HTTP/1.1", request.getVersion()));
    }

    @Test
    @DisplayName("InputStream으로부터 Query String을 포함한 Get 요청에 대한 HttpRequest 객체를 생성한다.")
    void createHttpRequestWithQueryString() {
        // given
        String httpGetRequestWithQueryString = "GET /user?name=siun&age=24 HTTP/1.1\r\n" +
                "Content-Type: application/json\r\n" +
                "User-Agent: PostmanRuntime/7.32.3\r\n" +
                "Accept: */*\r\n" +
                "Postman-Token: cbe20735-a35b-44b5-920d-0cef0c894b21\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 22\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(httpGetRequestWithQueryString.getBytes());

        // when
        HttpRequest request = HttpRequest.from(inputStream);

        // then
        assertAll("GET",
                () -> assertEquals(httpGetRequestWithQueryString, request.toString()),
                () -> assertEquals("/user", request.getPath()),
                () -> assertEquals("HTTP/1.1", request.getVersion()));
    }

}
