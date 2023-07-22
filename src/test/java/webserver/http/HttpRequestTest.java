package webserver.http;

import exception.NotSupportedContentTypeException;
import org.junit.jupiter.api.*;
import webserver.ContentType;
import webserver.http.request.HttpRequest;
import webserver.http.request.Uri;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static utils.StringUtils.*;

class HttpRequestTest {
    private final String GET = "GET";
    private final String PATH = "/index.html";
    private final Uri URI = Uri.from(PATH);
    private String requestStr;
    private InputStream in;

    @BeforeEach
    void setRequestStr() {
        requestStr = GET + " " + URI + " " + appendNewLine("HTTP/1.1") +
                appendNewLine("Host: localhost:8080") +
                appendNewLine("Connection: keep-alive") +
                appendNewLine("Cache-Control: max-age=0") +
                "";
        in = new ByteArrayInputStream(requestStr.getBytes());
    }

    @Nested
    class HttpRequestLine {
        @Test
        @DisplayName("요청 메소드를 가져온다")
        void getMethod() throws IOException {
            // given, when
            HttpRequest request = HttpRequest.from(in);

            // then
            assertNotNull(request);
            assertEquals(GET, request.getMethod());
        }

        @Test
        @DisplayName("요청 uri를 가져온다")
        void getUri() throws IOException {
            // given, when
            HttpRequest request = HttpRequest.from(in);

            // then
            assertEquals(PATH, request.getPath());
        }
    }

    @Test
    @DisplayName("지원하지 않는 ContentType의 경우 예외를 던진다")
    void mapResourceFolders(){
        // given
        ContentType type = ContentType.NONE;

        // when, then
        assertThatThrownBy(() -> type.mapResourceFolders(""))
                .isInstanceOf(NotSupportedContentTypeException.class);
    }
}