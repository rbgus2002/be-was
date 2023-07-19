package global.handler;

import controller.Controller;
import exception.BadRequestException;
import global.constant.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetHandlerTest {

    private GetHandler getHandler;
    private Controller controller;

    @BeforeEach
    public void setup() {
        getHandler = new GetHandler();
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
        String url = "/";

        //when
        String result = getHandler.startController(url, controller);

        //then
        assertTrue(result.contains("Hello world!"));
    }

    @Test
    public void testStartControllerWithNonMatchingMapping() {
        //given&when&then
        assertThrows(BadRequestException.class, () -> getHandler.startController("/invalid", controller));
    }
}
