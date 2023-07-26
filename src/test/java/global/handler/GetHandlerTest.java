package global.handler;

import controller.Controller;
import exception.BadRequestException;
import global.constant.HttpMethod;
import global.request.RequestBody;
import global.request.RequestHeader;
import global.request.RequestLine;
import model.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetHandlerTest {

    private GetHandler getHandler;
    private Controller controller;

    @BeforeEach
    public void setup() {
        RequestHeader header = new RequestHeader("ost: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Origin: http://localhost:8080\n" +
                "Accept: */*\n" +
                "Referer: http://localhost:8080/css/bootstrap.min.css\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                "Cookie: sid=123456");
        RequestBody body = new RequestBody("\nuserId=non_existent_user&password=invalid_password");
        Session session = new Session();
        getHandler = new GetHandler(header, body, session);
        controller = new Controller();
    }

    @Test
    @DisplayName("GET Method 요청 시, True를 반환한다.")
    public void testMatchHttpMethod() {
        //given
        HttpMethod getMethod = HttpMethod.GET;

        //when&then
        assertTrue(getHandler.matchHttpMethod(getMethod));
    }

    @Test
    public void testStartControllerWithMatchingMapping() throws Exception {
        //given
        RequestLine requestLine = new RequestLine("GET / HTTP/1.1 ");

        //when
        byte[] result = getHandler.startController(requestLine, controller);

        //then
        assertAll(
                () -> assertEquals(result[0], "Hello world!".getBytes()[0])
        );
    }

    @Test
    public void testStartControllerWithNonMatchingMapping() {
        //given
        RequestLine requestLine = new RequestLine("GET /invalid HTTP/1.1 ");

        //when&then
        assertThrows(BadRequestException.class, () -> getHandler.startController(requestLine, controller));
    }
}
