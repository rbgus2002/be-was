package util;

import model.HttpRequest;
import model.enums.Method;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private String requestSample =
            "GET /index.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*\n" +
                    "\n";

    private String requestSampleWithBody = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    @Test
    @DisplayName("요청을 파싱하여 HttpRequest 객체로 반환한다.")
    void returnHttpRequest() throws IOException {
        InputStream in = new ByteArrayInputStream(requestSample.getBytes());

        HttpRequest result = Parser.getHttpRequest(in);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.getProtocol()).isEqualTo("HTTP/1.1");
        softAssertions.assertThat(result.getUri()).isEqualTo("/index.html");
        softAssertions.assertThat(result.match(Method.GET)).isTrue();
        softAssertions.assertThat(result.isUriStaticFile()).isTrue();
    }

    @Test
    @DisplayName("request body까지 받을 수 있다.")
    void httpRequestWithBody() throws IOException {
        InputStream in = new ByteArrayInputStream(requestSampleWithBody.getBytes());

        HttpRequest result = Parser.getHttpRequest(in);

        assertEquals(result.getProtocol(), "HTTP/1.1");
        assertEquals(result.getUri(), "/user/create");
        assertTrue(result.match(Method.POST));
        assertFalse(result.isUriStaticFile());
    }

}