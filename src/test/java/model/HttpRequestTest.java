package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestTest {

    String inputValue;
    InputStream inputStream;
    HttpRequest httpRequest;

    @BeforeEach
    void setUp() throws IOException {
        inputValue =
                "GET /index.html?name=hello&pass=1234 HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "sec-ch-ua: \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"\n" +
                "sec-ch-ua-mobile: ?0\n" +
                "sec-ch-ua-platform: \"macOS\"\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-User: ?1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,zh-TW;q=0.6,zh;q=0.5\n" +
                "\n";

        inputStream = new ByteArrayInputStream(inputValue.getBytes());

        httpRequest = new HttpRequest(inputStream);

    }

    @Test
    @DisplayName("Request Header를 파싱한다.")
    void parseHeaderTest() {
        assertEquals("/index.html", httpRequest.getRequestURI());
        assertEquals("localhost:8080/index.html?name=hello&pass=1234", httpRequest.getRequestURL());
        assertEquals("localhost:8080", httpRequest.getHeader("Host"));
        assertEquals("keep-alive", httpRequest.getHeader("Connection"));
        assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36", httpRequest.getHeader("User-Agent"));
        assertEquals("ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,zh-TW;q=0.6,zh;q=0.5", httpRequest.getHeader("Accept-Language"));
        assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7", httpRequest.getHeader("Accept"));
        assertEquals(HttpMethod.GET, httpRequest.getMethod());
        assertEquals("name=hello&pass=1234", httpRequest.getQueryString());
        assertEquals("HTTP/1.1", httpRequest.getVersion());
        assertEquals("hello", httpRequest.getParameter("name"));
        assertEquals("1234", httpRequest.getParameter("pass"));
    }
}