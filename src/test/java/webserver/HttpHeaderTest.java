package webserver;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static utils.StringUtils.*;

class HttpHeaderTest {
    private final String GET = "GET";
    private final String URI = "/index.html";
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
            HttpHeader request = HttpHeader.from(in);

            // then
            assertNotNull(request);
            assertEquals(GET, request.getMethod());
        }

        @Test
        @DisplayName("요청 uri를 가져온다")
        void getUri() throws IOException {
            // given, when
            HttpHeader request = HttpHeader.from(in);

            // then
            assertEquals(URI, request.getRequestLine());
        }
    }
}