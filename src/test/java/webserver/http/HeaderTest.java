package webserver.http;

import http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static utils.StringUtils.appendNewLine;

class HeaderTest {
    private String headerStr;
    private InputStream in;
    private BufferedReader br;

    @BeforeEach
    private void init(){
        headerStr = appendNewLine("Host: localhost:8080") +
                appendNewLine("Connection: keep-alive") +
                appendNewLine("Cache-Control: max-age=0") +
                appendNewLine("");
        in = new ByteArrayInputStream(headerStr.getBytes());
        br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("헤더가 정상적으로 생성되는지 확인한다")
    void createHeader() throws IOException {
        // given
        Header header = Header.from(br);

        // when
        int headerSize = header.size();

        // then
        assertEquals(3, headerSize);
    }

    @Test
    @DisplayName("ContentLength의 value 값을 가져온다. 존재하지 않으면 0을 가져온다")
    void getContentLength() throws IOException {
        // given
        Header header = Header.from(br);
        
        // when
        int contentLength = header.getContentLength();
        
        // then
        assertEquals(0, contentLength);
    }

    @Test
    @DisplayName("ContentLength가 존재하는지 확인한다")
    void containsContentLength() throws IOException {
        // given
        Header header = Header.from(br);

        // when
        boolean exist = header.containsContentLength();

        // then
        assertFalse(exist);
    }
}