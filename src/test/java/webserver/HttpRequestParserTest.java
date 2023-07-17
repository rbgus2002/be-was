package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.IIOException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestParserTest {
    String headerString = "GET /favicon.ico HTTP/1.1\n" +
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

    @Test
    @DisplayName("헤더 입력이 들어오면 해당 헤더를 출력해야 한다.")
    void header() throws IOException {
        HttpRequestParser requestParser = new HttpRequestParser(new ByteArrayInputStream(headerString.getBytes()));

        assertEquals("\n" + headerString, requestParser.getHeader());
    }

    @Test
    @DisplayName("헤더 입력이 들어오면 해당 헤더의 method와 url이 제대로 추출되어야 한다.")
    void methodAndUrl() throws IOException {
        HttpRequestParser requestParser = new HttpRequestParser(new ByteArrayInputStream(headerString.getBytes()));

        assertEquals("GET", requestParser.getMethod());
        assertEquals("/favicon.ico", requestParser.getPath());
    }
}