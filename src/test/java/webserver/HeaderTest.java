package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Headers;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
