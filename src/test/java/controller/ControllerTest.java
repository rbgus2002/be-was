package controller;

import model.UserParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Controller 테스트")
class ControllerTest {

    Controller controller = new Controller();

    @Test
    @DisplayName("root 메서드 테스트")
    void testRoot() {
        //given
        String expectedResponse = "HTTP/1.1 200 OK \n";

        //when
        String response = controller.root(new LinkedHashMap<>());

        //then
        assertTrue(response.contains(expectedResponse));
    }

    @Test
    @DisplayName("getIndexHtml 메서드 테스트")
    void testGetIndexHtml() {
        //given
        String expectedResponse = "HTTP/1.1 200 OK \n";

        //when
        String response = controller.getIndexHtml(new LinkedHashMap<>());

        //then
        assertTrue(response.contains(expectedResponse));
    }

    @Test
    @DisplayName("getFormHtml 메서드 테스트")
    void testGetFormHtml() {
        //given
        String expectedResponse = "HTTP/1.1 200 OK \n";

        //when
        String response = controller.getFormHtml(new LinkedHashMap<>());

        //then
        assertTrue(response.contains(expectedResponse));
    }

    @Test
    @DisplayName("createUser 메서드 - 유효한 쿼리 매개변수를 받아서 사용자 생성")
    void testCreateUserWithValidQueryParams() {
        //given
        Map<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put(UserParam.EMAIL, "test@example.com");

        String expectedResponse = "HTTP/1.1 200 OK ";

        //when
        String actualResponse = controller.createUser(queryParams);

        //then
        assertTrue(actualResponse.contains(expectedResponse));
    }
}
