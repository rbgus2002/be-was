package global.handler;

import controller.Controller;
import global.constant.HttpMethod;
import global.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Handler 테스트")
class HandlerTest {

    private final Handler handler = new GetHandler();

    @Test
    @DisplayName("일치하는 HTTP 메서드인 경우, 성공한다.")
    void testMatchHttpMethodWithMatchingMethod() {
        HttpMethod httpMethod = HttpMethod.GET;
        assertTrue(handler.matchHttpMethod(httpMethod));
    }

    @Test
    @DisplayName("컨트롤러 메서드 실행한다.")
    void testStartController() throws Exception {
        //given
        String requestLine = "GET / HTTP/1.1";
        RequestLine requestLineObject = new RequestLine(requestLine);
        Controller controller = new Controller();

        //when
        String expectedResponse = "HTTP/1.1 200 OK";
        byte[] actualResponse = handler.startController(requestLineObject, controller);

        //then
        assertAll(
                () -> assertEquals(actualResponse[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(actualResponse[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(actualResponse[2], expectedResponse.getBytes()[2])
        );
    }
}
