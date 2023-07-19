package global.request;

import exception.BadRequestException;
import global.constant.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("RequestLine 테스트")
class RequestLineTest {

    @Test
    @DisplayName("올바른 형식의 경우, request line 분리에 성공한다.")
    void testSplitRequestLineWithValidFormat() {
        //given
        String requestLine = "GET /index.html HTTP/1.1";
        RequestLine requestLineObject = new RequestLine(requestLine);

        //when
        String expectedHttpMethod = "GET";
        String expectedUri = "/index.html";

        //then
        assertEquals(HttpMethod.GET, requestLineObject.getHttpMethod());
        assertEquals(expectedHttpMethod, requestLineObject.getHttpMethod().name());
        assertEquals(expectedUri, requestLineObject.getUri());
    }

    @Test
    @DisplayName("잘못된 형식의 request line의 경우, 예외가 발생한다.")
    void testSplitRequestLineWithInvalidFormat() {
        //given
        String requestLine = "GET";

        //when&then
        assertThrows(BadRequestException.class, () -> new RequestLine(requestLine));
    }
}
