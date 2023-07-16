package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("응답 메시지 클래스 테스트")
class RequestMessageTest {
    @Test
    @DisplayName("응답 메시지 객체가 생성된다.")
    void create() {
        // given
        String message = "GET /test HTTP/1.1\n" +
                "Host: test.com\n" +
                "Accept: application/json\n";

        // when
        RequestMessage requestLine = new RequestMessage(message);

        String method = requestLine.getMethod();
        String path = requestLine.getPath();
        String version = requestLine.getVersion();
        String host = requestLine.getMetaData("Host");

        // then
        assertAll(
                () -> assertEquals("GET", method),
                () -> assertEquals("/test", path),
                () -> assertEquals("HTTP/1.1", version),
                () -> assertEquals("test.com", host)
        );
    }
}