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
        String request = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        HttpUtil httpUtil = new HttpUtil(inputStream);

        String expectedResponse = "HTTP/1.1 200 OK ";
        String actualResponse = httpUtil.getResponse();

        assertTrue(actualResponse.contains(expectedResponse));
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

        assertEquals(httpUtil.getResponse(), "Error!");
    }
}
