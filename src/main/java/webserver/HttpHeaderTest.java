package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpHeaderTest {
    @Test
    @DisplayName("http 헤더를 생성한다")
    void createHttpHeader() throws IOException {
        // given
        String requestStr = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "";
        InputStream in = new ByteArrayInputStream(requestStr.getBytes());

        // when
        HttpHeader request = HttpHeader.from(in);

        // then
        assertNotNull(request);
    }


}