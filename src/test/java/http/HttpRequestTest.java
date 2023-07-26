package http;

import exception.NotSupportedContentTypeException;
import http.HttpRequest;
import http.Uri;
import org.junit.jupiter.api.*;
import webserver.ContentType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static utils.StringUtils.*;

class HttpRequestTest {
    @Nested
    class HttpRequestLine {
        final String GET = "GET";
        final String PATH = "/index.html";
        final Uri URI = Uri.from(PATH);
        final String requestStr = GET + " " + URI + " " + appendNewLine("HTTP/1.1") +
                appendNewLine("Host: localhost:8080") +
                appendNewLine("Connection: keep-alive") +
                appendNewLine("Cache-Control: max-age=0") +
                appendNewLine("");
        InputStream in = new ByteArrayInputStream(requestStr.getBytes());

        @Test
        @DisplayName("요청 uri를 가져온다")
        void getUri() throws IOException {
            // given, when
            HttpRequest request = HttpRequest.from(in);

            // then
            assertEquals(PATH, request.getPath());
        }

        @Test
        @DisplayName("GET 메소드인 경우 true를 반환한다")
        void isGetMethod() throws IOException {
            // given
            HttpRequest request = HttpRequest.from(in);

            // when
            boolean get = request.isGetMethod();

            // then
            assertTrue(get);
        }
    }

    @Test
    @DisplayName("지원하지 않는 ContentType의 경우 예외를 던진다")
    void mapResourceFolders() {
        // given
        ContentType type = ContentType.NONE;

        // when, then
        assertThatThrownBy(() -> type.mapResourceFolders(""))
                .isInstanceOf(NotSupportedContentTypeException.class);
    }

    @Nested
    class Body{
        final String POST = "POST";
        final String PATH = "/index.html";
        final Uri URI = Uri.from(PATH);
        final String requestStr = POST + " " + URI + " " + appendNewLine("HTTP/1.1") +
                appendNewLine("Host: localhost:8080") +
                appendNewLine("Connection: keep-alive") +
                appendNewLine("Cache-Control: max-age=0") +
                appendNewLine("Con: max-age=0") +
                appendNewLine("Content-Length: 93") +
                appendNewLine("Cookie: Idea-b49a3250=4870f825-f607-4ca3-8fdf-aa4b2949cb71; sid=6d64096c-6f8a-4136-b283-719888edd6d8") +
                appendNewLine("") +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi@40slipp.net";

        InputStream in = new ByteArrayInputStream(requestStr.getBytes());

        @Test
        @DisplayName("POST 요청인 경우 body가 정상적으로 들어오는지 확인한다")
        void getBody() throws IOException {
            // given
            HttpRequest request = HttpRequest.from(in);

            // when
            Map<String, String> body = request.getBody();

            // then
            assertTrue(body.containsKey("userId"));
            assertEquals("javajigi", body.get("userId"));
        }

        @Test
        @DisplayName("toString이 제대로 override 됐는지 확인한다")
        void testToString() throws IOException {
            // given, when
            HttpRequest request = HttpRequest.from(in);

            // then
            System.out.println(request);
        }

        @Test
        @DisplayName("POST 메소드인 경우 true를 반환한다")
        void isGetMethod() throws IOException {
            // given
            HttpRequest request = HttpRequest.from(in);

            // when
            boolean post = request.isPostMethod();

            // then
            assertTrue(post);
        }
        @Test
        @DisplayName("쿠키에 sid가 Session에 존재하면 사용자를 가져온다")
        void getUserInSession() throws IOException {
            // given
            HttpRequest request = HttpRequest.from(in);

            // when
            request.getUserInSession();

            // then
            assertNull(request.getUserInSession());
        }
    }
}