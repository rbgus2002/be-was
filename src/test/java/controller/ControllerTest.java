package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Controller 테스트")
class ControllerTest {

    @Test
    @DisplayName("root 메서드 테스트")
    void testRoot() {
        //given
        Controller controller = new Controller();
        String expectedResponse = "HTTP/1.1 200 OK \n";

        //when
        String response = controller.root();

        //then
        assertTrue(response.contains(expectedResponse));
    }

    @Test
    @DisplayName("getIndexHtml 메서드 테스트")
    void testGetIndexHtml() {
        //given
        Controller controller = new Controller();
        String expectedResponse = "HTTP/1.1 200 OK \n";

        //when
        String response = controller.getIndexHtml();

        //then
        assertTrue(response.contains(expectedResponse));
    }

    @Test
    @DisplayName("getFormHtml 메서드 테스트")
    void testGetFormHtml() {
        //given
        Controller controller = new Controller();
        String expectedResponse = "HTTP/1.1 200 OK \n";

        //when
        String response = controller.getFormHtml();

        //then
        assertTrue(response.contains(expectedResponse));
    }
}
