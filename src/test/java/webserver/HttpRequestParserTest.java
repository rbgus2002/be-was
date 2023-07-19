package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HttpRequestParserTest {
    String requestInput = "GET /favicon.ico HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "sec-ch-ua: \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"\n" +
            "sec-ch-ua-mobile: ?0\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36\n" +
            "sec-ch-ua-platform: \"macOS\"\n" +
            "Accept: image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8\n" +
            "Sec-Fetch-Site: same-origin\n" +
            "Sec-Fetch-Mode: no-cors\n" +
            "Sec-Fetch-Dest: image\n" +
            "Referer: http://localhost:8080/\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
            "\n" +
            "{\n" +
            "  \"name\": \"John Doe\",\n" +
            "  \"email\": \"john@example.com\",\n" +
            "  \"age\": 30\n" +
            "}\n";

    String header = "GET /favicon.ico HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "sec-ch-ua: \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"\n" +
            "sec-ch-ua-mobile: ?0\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36\n" +
            "sec-ch-ua-platform: \"macOS\"\n" +
            "Accept: image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8\n" +
            "Sec-Fetch-Site: same-origin\n" +
            "Sec-Fetch-Mode: no-cors\n" +
            "Sec-Fetch-Dest: image\n" +
            "Referer: http://localhost:8080/\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n";

    String body = "{\n" +
            "  \"name\": \"John Doe\",\n" +
            "  \"email\": \"john@example.com\",\n" +
            "  \"age\": 30\n" +
            "}\n";
    HttpRequest request;

    @BeforeEach
    void init() throws IOException{
        request = HttpRequestParser.getInstance().getRequest(new ByteArrayInputStream(requestInput.getBytes()));
    }


    @Test
    @DisplayName("http request byte 값이 들어오면 해당 헤더를 제대로 추출해야 한다.")
    void header() {
        assertEquals(header, request.getHeader());//헤더만 추출해야 함
        assertNotEquals(requestInput, request.getHeader());//바디가 포함 된 전체 request와 값이 달라야 한다
    }

    @Test
    @DisplayName("http request byte 값이 들어오면 해당 헤더의 method와 url이 제대로 추출되어야 한다.")
    void methodAndUrl() {
        assertEquals("GET", request.getMethod());
        assertEquals("/favicon.ico", request.getUrl());
    }

    @Test
    @DisplayName("http request byte 값이 들어오면 해당 request의 body를 추출해야 한다.")
    void body() {
        assertEquals(body, request.getBody());
    }
}