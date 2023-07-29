package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.constant.HttpMethod;
import webserver.http.request.HttpRequestMessage;
import webserver.http.request.HttpRequestParser;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.NEW_LINE;
import static webserver.http.request.HttpRequestParser.parseRequest;

@DisplayName("요청 메시지 클래스 테스트")
class CookiesMessageTest {
    @Test
    @DisplayName("body가 없는 Get 응답 메시지 객체가 생성한다.")
    void createWithoutBody() throws Exception {
        // given
        String message = "GET /test HTTP/1.1" + NEW_LINE +
                "Host: test.com" + NEW_LINE +
                "Accept: application/json" + NEW_LINE + NEW_LINE;

        // when
        HttpRequestMessage httpRequestMessage = parseRequest(new ByteArrayInputStream(message.getBytes()));

        HttpMethod method = httpRequestMessage.getMethod();
        String version = httpRequestMessage.getVersion();
        String host = httpRequestMessage.getHeader("Host");

        // then
        assertAll(
                () -> assertEquals(HttpMethod.GET, method),
                () -> assertEquals("HTTP/1.1", version),
                () -> assertEquals("test.com", host)
        );
    }

    @Test
    @DisplayName("body가 존재하는 Post 응답 메시지 객체가 생성한다.")
    void createWithBody() throws Exception {
        // given
        String message = "POST /api/data.image?user=me&name=김&id=iidd HTTP/1.1" + NEW_LINE +
                "Host: example.com" + NEW_LINE +
                "Content-Type: application/json" + NEW_LINE +
                "Content-Length: 27" + NEW_LINE +
                NEW_LINE +
                "{\"name\": \"John\", \"age\": 30}";
        String targetBody = "{\"name\": \"John\", \"age\": 30}";

        // when
        HttpRequestMessage httpRequestMessage = parseRequest(new ByteArrayInputStream(message.getBytes()));

        HttpMethod method = httpRequestMessage.getMethod();
        String version = httpRequestMessage.getVersion();
        String body = httpRequestMessage.getBody();

        // then
        assertAll(
                () -> assertEquals(HttpMethod.POST, method),
                () -> assertEquals("HTTP/1.1", version),
                () -> assertEquals(targetBody, body)
        );
    }

    @Test
    @DisplayName("헤더가 없는 응답 메시지를 생성한다.")
    void name() throws Exception {
        // given
        HttpMethod method = HttpMethod.GET;
        String url = "/index.html";
        String version = "HTTP/1.1";
        Map<String, String> headers = new HashMap<>();
        String body = "<!DOCTYPE html>" + NEW_LINE +
                "<html>" + NEW_LINE +
                "<head>" + NEW_LINE +
                "	<title>My First HTML Document</title>" + NEW_LINE +
                "</head>" + NEW_LINE +
                "<body>" + NEW_LINE +
                "	<h1>Hello, World!</h1>" + NEW_LINE +
                "</body>" + NEW_LINE +
                "</html>";

        // when
        HttpRequestMessage httpRequestMessage = HttpRequestParser.parseRequest(createRequestMessage(method, url, version, headers, body));

        // then
        assertAll(
                () -> assertEquals(method, httpRequestMessage.getMethod()),
                () -> assertEquals(version, httpRequestMessage.getVersion()),
                () -> assertEquals("", httpRequestMessage.getBody())
        );
    }

    private ByteArrayInputStream createRequestMessage(HttpMethod method, String url, String
            version, Map<String, String> headers, String body) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(method).append(" ").append(url).append(" ").append(version).append(NEW_LINE);
        for (String key : headers.keySet()) {
            stringBuilder.append(key).append(": ").append(headers.get(key)).append(NEW_LINE);
        }
        stringBuilder.append(NEW_LINE).append(body);
        return new ByteArrayInputStream(stringBuilder.toString().getBytes());
    }

    private String createHeader(String method, String url, String version) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(method).append(" ").append(url).append(" ").append(version).append(NEW_LINE);
        return stringBuilder.toString();
    }
}
