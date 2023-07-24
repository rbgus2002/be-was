package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.response.Body;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.appendNewLine;

class HeaderTest {
    @Test
    @DisplayName("bufferedReader에서 읽어와 header 목록을 생성해야 한다.")
    void buildHeader() throws IOException {
        // given
        String httpHeaders = "Content-Type: application/json\r\n" +
                "User-Agent: PostmanRuntime/7.32.3\r\n" +
                "Accept: */*\r\n" +
                "Postman-Token: cbe20735-a35b-44b5-920d-0cef0c894b21\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 22\r\n";
        String blankLine = "\r\n";

        InputStream inputStream = new ByteArrayInputStream((httpHeaders + blankLine).getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // when
        Headers headers = Headers.from(bufferedReader);

        // then
        assertEquals(httpHeaders, headers.toString());
    }

    @Test
    @DisplayName("bufferedReader에서 읽어와 header 목록을 생성해야 한다.")
    void buildHeaderWithBody() throws IOException {
        // given
        String httpHeaders = "Content-Type: application/json\r\n" +
                "User-Agent: PostmanRuntime/7.32.3\r\n" +
                "Accept: */*\r\n" +
                "Postman-Token: cbe20735-a35b-44b5-920d-0cef0c894b21\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 22\r\n";
        String blankLineWithBody = "\r\n" +
                "{" +
                "    \"test\": \"test\"\r\n" +
                "}\r\n";

        InputStream inputStream = new ByteArrayInputStream((httpHeaders + blankLineWithBody).getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // when
        Headers headers = Headers.from(bufferedReader);

        // then
        assertEquals(httpHeaders, headers.toString());
    }

    @Test
    @DisplayName("body를 받아 Content-type, Content-length를 갖는 Headers를 생성해야 한다.")
    void buildHeaderFromBody() {
        // given
        byte[] content = "바디입니다.".getBytes();
        Body body = Body.from(content, MIME.TXT);

        // when
        Headers headers = Headers.from(body);

        // then
        String expectedHeadersString = appendNewLine("Content-Type: " + MIME.TXT.getContentType()) +
                appendNewLine("Content-Length: " + headers.getContentLength());

        assertAll(() -> {
            assertEquals(content.length, headers.getContentLength());
            assertEquals(expectedHeadersString, headers.toString());
        });
    }
}
