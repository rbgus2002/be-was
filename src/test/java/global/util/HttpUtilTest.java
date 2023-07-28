package global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpUtil 테스트")
class HttpUtilTest {

    @Test
    @DisplayName("getResponse 메서드 - 유효한 요청에 대한 응답")
    void testGetResponseWithValidRequest() throws IOException {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080/index.html\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        HttpUtil httpUtil = new HttpUtil(inputStream);

        String expectedResponse = "HTTP/1.1 200 OK ";
        byte[] actualResponse = httpUtil.getResponse();

        assertAll(
                () -> assertEquals(actualResponse[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(actualResponse[1], expectedResponse.getBytes()[1])
        );
    }

    @Test
    @DisplayName("getResponse 메서드 - 잘못된 요청에 대한 BadRequestException 발생")
    void testGetResponseWithInvalidRequest() throws IOException {
        String request = "POST / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        HttpUtil httpUtil = new HttpUtil(inputStream);

        assertAll(
                () -> assertEquals(httpUtil.getResponse()[0], "Error!".getBytes()[0]),
                () -> assertEquals(httpUtil.getResponse()[1], "Error!".getBytes()[1]),
                () -> assertEquals(httpUtil.getResponse()[2], "Error!".getBytes()[2])
        );
    }
}
