package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.NEW_LINE;
import static webserver.request.HttpRequestParser.parseRequest;

@DisplayName("요청 메시지 클래스 테스트")
class HttpRequestTest {
    @Test
    @DisplayName("body가 없는 응답 메시지 객체가 생성된다.")
    void createWithoutBody() throws IOException {
        // given
        String message = "GET /test HTTP/1.1" + NEW_LINE +
                "Host: test.com" + NEW_LINE +
                "Accept: application/json" + NEW_LINE + NEW_LINE;

        // when
        HttpRequest httpRequest = parseRequest(new ByteArrayInputStream(message.getBytes()));

        String method = httpRequest.getMethod();
        String url = httpRequest.getUrl();
        String version = httpRequest.getVersion();
        String host = httpRequest.getMetaData("Host");

        // then
        assertAll(
                () -> assertEquals("GET", method),
                () -> assertEquals("/test", url),
                () -> assertEquals("HTTP/1.1", version),
                () -> assertEquals("test.com", host)
        );
    }

    @Test
    @DisplayName("body가 존재하는 응답 메시지 객체가 생성된다.")
    void createWithBody() throws IOException {
        // given
        String message = "POST /api/data HTTP/1.1" + NEW_LINE +
                "Host: example.com" + NEW_LINE +
                "Content-Type: application/json" + NEW_LINE +
                "Content-Length: 25" + NEW_LINE +
                NEW_LINE +
                "{\"name\": \"John\", \"age\": 30}" + NEW_LINE;
        String targetBody = "{\"name\": \"John\", \"age\": 30}" + NEW_LINE;

        // when
        HttpRequest httpRequest = parseRequest(new ByteArrayInputStream(message.getBytes()));

        String method = httpRequest.getMethod();
        String url = httpRequest.getUrl();
        String version = httpRequest.getVersion();
        String body = httpRequest.getBody();

        // then
        assertAll(
                () -> assertEquals("POST", method),
                () -> assertEquals("/api/data", url),
                () -> assertEquals(targetBody, body)
        );
    }
}
