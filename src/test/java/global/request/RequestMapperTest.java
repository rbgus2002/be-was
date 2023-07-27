package global.request;

import exception.BadRequestException;
import global.util.SessionUtil;
import model.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RequestMapper 테스트")
class RequestMapperTest {

    @Test
    @DisplayName("response 메서드 - GET 요청에 대한 응답")
    void testResponseWithGetRequest() throws Exception {
        RequestLine requestLine = new RequestLine("GET / HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader("ost: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Origin: http://localhost:8080\n" +
                "Accept: */*\n" +
                "Referer: http://localhost:8080/css/bootstrap.min.css\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                "Cookie: sid=123456");
        RequestBody requestBody = new RequestBody("");
        SessionUtil sessionUtil = new SessionUtil();
        RequestMapper requestMapper = new RequestMapper(requestLine, requestHeader, requestBody, sessionUtil);

        String expectedResponse = "HTTP/1.1 200 OK";
        byte[] actualResponse = requestMapper.response();

        assertAll(
                () -> assertEquals(actualResponse[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(actualResponse[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(actualResponse[2], expectedResponse.getBytes()[2])
        );
    }

    @Test
    @DisplayName("response 메서드 - 잘못된 요청 메서드일 경우 BadRequestException 발생")
    void testResponseWithInvalidRequestMethod() {
        RequestLine requestLine = new RequestLine("POST / HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader("ost: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Origin: http://localhost:8080\n" +
                "Accept: */*\n" +
                "Referer: http://localhost:8080/css/bootstrap.min.css\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                "Cookie: sid=123456");
        RequestBody requestBody = new RequestBody("");
        SessionUtil sessionUtil = new SessionUtil();
        RequestMapper requestMapper = new RequestMapper(requestLine, requestHeader, requestBody, sessionUtil);

        assertThrows(BadRequestException.class, requestMapper::response);
    }
}
