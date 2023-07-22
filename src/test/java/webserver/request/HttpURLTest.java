package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("요청 url 클래스 테스트")
class HttpURLTest {
    @Test
    @DisplayName("리소스가 없는 url 클래스를 생성한다.")
    void createSimple() {
        // given
        String url = "/user/create";

        // when
        HttpURL httpURL = HttpRequestParser.parseUrl(url);

        // then
        assertAll(
                () -> assertEquals(url, httpURL.getUrl()),
                () -> assertNull(httpURL.getExtension()),
                () -> assertEquals("/user/create", httpURL.getPath())
        );
    }

    @Test
    @DisplayName("리소스를 가져오는 url 클래스를 생성한다.")
    void createResourceURL() {
        // given
        String url = "/main/index.html";

        // when
        HttpURL httpURL = HttpRequestParser.parseUrl(url);

        // then
        assertAll(
                () -> assertEquals(url, httpURL.getUrl()),
                () -> assertEquals(".html", httpURL.getExtension()),
                () -> assertEquals("/main/index.html", httpURL.getPath())
        );
    }

    @Test
    @DisplayName("쿼리 스트링이 존재하는 url 클래스를 생성한다.")
    void createQuery() {
        // given
        String url = "/user/login.index?userId=abc123&password=1234&name=gildong";

        // when
        HttpURL httpURL = HttpRequestParser.parseUrl(url);

        // then
        assertAll(
                () -> assertEquals(url, httpURL.getUrl()),
                () -> assertEquals(".index", httpURL.getExtension()),
                () -> assertEquals("/user/login.index", httpURL.getPath()),
                () -> assertTrue(httpURL.getParameters().containsKey("userId")),
                () -> assertTrue(httpURL.getParameters().containsKey("password")),
                () -> assertTrue(httpURL.getParameters().containsKey("name")),
                () -> assertEquals("abc123", httpURL.getParameters().get("userId")),
                () -> assertEquals("1234", httpURL.getParameters().get("password")),
                () -> assertEquals("gildong", httpURL.getParameters().get("name"))
        );
    }
}
