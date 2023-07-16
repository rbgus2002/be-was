package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("request line 테스트")
class RequestLineTest {
    @Test
    @DisplayName("응답 line을 생성한다.")
    void create() {
        // given
        String line = "GET /index.html HTTP/1.1";

        // when
        RequestLine requestLine = new RequestLine(line);
        String startLine = requestLine.getRequestLine();
        String method = requestLine.getMethod();
        String path = requestLine.getPath();
        String version = requestLine.getVersion();

        // then
        assertAll(
                () -> assertEquals(startLine, line),
                () -> assertEquals("GET", method),
                () -> assertEquals("/index.html", path),
                () -> assertEquals("HTTP/1.1", version)
        );
    }

    @Test
    void createBlank() {
        // given
        String line = "";

        // when
        RequestLine requestLine = new RequestLine(line);
    }
}