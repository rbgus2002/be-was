package global.request;

import exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("RequestMapper 테스트")
class RequestMapperTest {

    @Test
    @DisplayName("response 메서드 - GET 요청에 대한 응답")
    void testResponseWithGetRequest() throws Exception {
        RequestLine requestLine = new RequestLine("GET / HTTP/1.1");
        RequestBody requestBody = new RequestBody("");
        RequestMapper requestMapper = new RequestMapper(requestLine, requestBody);

        String expectedResponse = "HTTP/1.1 200 OK";
        String actualResponse = requestMapper.response();

        assertTrue(actualResponse.contains(expectedResponse));
    }

    @Test
    @DisplayName("response 메서드 - 잘못된 요청 메서드일 경우 BadRequestException 발생")
    void testResponseWithInvalidRequestMethod() {
        RequestLine requestLine = new RequestLine("POST / HTTP/1.1");
        RequestBody requestBody = new RequestBody("");
        RequestMapper requestMapper = new RequestMapper(requestLine, requestBody);

        assertThrows(BadRequestException.class, requestMapper::response);
    }
}
