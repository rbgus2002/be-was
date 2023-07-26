package webserver.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestParserTest {
    String requestInput = "GET /url?id=1&password=1234 HTTP/1.1\n" +
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
            "Content-Type: text/plain\n" +
            "Content-Length: 64\n" +
            "\n" +
            "{\n" +
            "    name: John Doe,\n" +
            "    email: john@example.com,\n" +
            "    age: 30\n" +
            "}";

    String header = "GET /url?id=1&password=1234 HTTP/1.1\n" +
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
            "Content-Type: text/plain\n" +
            "Content-Length: 64\n";

    String body = "{\n" +
            "    name: John Doe,\n" +
            "    email: john@example.com,\n" +
            "    age: 30\n" +
            "}";
    HttpRequest request;

    @BeforeEach
    void init() throws IOException{
        request = HttpRequestParser.getRequest(new ByteArrayInputStream(requestInput.getBytes()));
    }


    @Test
    @DisplayName("http request byte 값이 들어오면 해당 헤더를 제대로 추출해야 한다.")
    void header() {
        assertEquals(header, request.getHeaderString());//헤더만 추출해야 함
        assertNotEquals(requestInput, request.getHeaderString());//바디가 포함 된 전체 request와 값이 달라야 한다
    }

    @Test
    @DisplayName("http request byte 값이 들어오면 해당 헤더의 method와 url이 제대로 추출되어야 한다.")
    void methodAndUrl() {
        assertEquals("GET", request.getMethod());
        assertEquals("/url", request.getPath());
    }

    @Test
    @DisplayName("http request byte 값이 들어오면 해당 request의 body를 추출해야 한다.")
    void body() {
        assertEquals(body, request.getBody());
    }

    @Test
    @DisplayName("http request byte 값이 들어오면 해당 request url 내의 파라미터를 추출해야 한다.")
    void params(){
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("id","1");
        tempMap.put("password","1234");

        assertEquals(tempMap.get("id"), request.getParamValueByKey("id"));
        assertEquals(tempMap.get("password"), request.getParamValueByKey("password"));
    }
}