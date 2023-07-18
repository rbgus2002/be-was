package webserver.http;

import org.junit.jupiter.api.*;
import webserver.http.request.HttpRequest;
import webserver.http.request.Uri;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    @DisplayName("static 파일을 요청하는지 구분한다")
    void requestStaticFile() throws IOException {
        // given, when
        HttpRequest request = HttpRequest.from(in);

        // then
        assertTrue(request.requestStaticFile());
    }
}