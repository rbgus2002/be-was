package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("요청 메시지 클래스 테스트")
class HttpRequestTest {
    @Test
    @DisplayName("응답 메시지 객체가 생성된다.")
    void create() {
        // given
        String message = "GET /test HTTP/1.1\n" +
                "Host: test.com\n" +
                "Accept: application/json\n";

        // when
        HttpRequest httpRequest = new HttpRequest(message);

        String method = httpRequest.getMethod();
        String path = httpRequest.getPath();
        String version = httpRequest.getVersion();
        String host = httpRequest.getMetaData("Host");
        boolean hasExtension = httpRequest.isHasExtension();

        // then
        assertAll(
                () -> assertEquals("GET", method),
                () -> assertEquals("/test", path),
                () -> assertEquals("HTTP/1.1", version),
                () -> assertEquals("test.com", host),
                () -> assertFalse(hasExtension)
        );
    }

    @Test
    @DisplayName("Path에 파라미터를 포함한 GET 메소드의 객체가 생성된다.")
    void createGetWithParameters() {
        // given
        String message = "GET /test?user=ddingmin&password=1234&name=gildong HTTP/1.1\n" +
                "Host: test.com\n" +
                "Accept: application/json\n";

        // when
        HttpRequest httpRequest = new HttpRequest(message);

        String user = httpRequest.getParameter("user");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");

        // then
        assertAll(
                () -> assertEquals("ddingmin", user),
                () -> assertEquals("1234", password),
                () -> assertEquals("gildong", name)
        );
    }
}
