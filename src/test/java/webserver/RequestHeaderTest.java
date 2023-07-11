package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static utils.StringUtils.appendNewLine;

class RequestHeaderTest {
    String requestLine = "GET /index.html HTTP/1.1";
    String headers = appendNewLine("Host: localhost:8080", "Connection: keep-alive");
    String body = ""; // 본문 내용을 추가할 수 있습니다.
    String httpRequest = appendNewLine(requestLine, headers, "", body);
    InputStream in;

    @BeforeEach
    void setUp() {
        in = new ByteArrayInputStream(httpRequest.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("Request Line에서 정상적으로 요청 Url을 분리하는지 검증한다.")
    void parseRequestUrl() throws IOException {
        //given
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        RequestHeader.RequestHeaderBuilder requestHeaderBuilder = new RequestHeader.RequestHeaderBuilder();
        String line = br.readLine();
        requestHeaderBuilder = requestHeaderBuilder.requestLine(line);
        line = br.readLine();
        while(!"".equals(line)){
            requestHeaderBuilder.header(line);
            line = br.readLine();
        }
        RequestHeader requestHeader = requestHeaderBuilder.build();

        //when
        String requestUrl = requestHeader.getRequestUrl();
        String requestHeaders = requestHeader.getHeaders();

        //then
        assertEquals("/index.html", requestUrl);
        assertNotEquals(0, requestHeaders.length());
    }
}
