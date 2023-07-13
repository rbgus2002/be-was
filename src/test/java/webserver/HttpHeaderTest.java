package webserver;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpHeaderTest {
    private final String GET = "GET";
    private final String URI = "/index.html";
    private String requestStr;
    private InputStream in;

    @BeforeEach
    void setRequestStr() {
        requestStr = GET + " " + URI + " " + "HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "";
        in = new ByteArrayInputStream(requestStr.getBytes());
    }

    @Test
    @DisplayName("http 헤더를 생성한다")
    void createHttpHeader() throws IOException {
        // given, when
        HttpHeader request = HttpHeader.from(in);

        // then
        assertNotNull(request);
    }

    @Nested
    class HttpRequestLine {
        @Test
        @DisplayName("요청 메소드를 가져온다")
        void getMethod() throws IOException {
            // given, when
            HttpHeader request = HttpHeader.from(in);

            // then
            assertEquals(GET, request.getMethod());
        }

        @Test
        @DisplayName("요청 uri를 가져온다")
        void getUri() throws IOException {
            // given, when
            HttpHeader request = HttpHeader.from(in);

            // then
            assertEquals(URI, request.getUri());
        }
    }
}