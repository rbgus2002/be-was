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
        byte[] response = controller.root(new LinkedHashMap<>());

        //then
        assertAll(
                () -> assertEquals(response[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(response[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(response[2], expectedResponse.getBytes()[2])
        );
    }


    @Test
    @DisplayName("createUser 메서드 - 유효한 쿼리 매개변수를 받아서 사용자 생성")
    void testCreateUserWithValidQueryParams() {
        //given
        Map<String, String> queryParams = new LinkedHashMap<>();
        queryParams.put(UserParam.EMAIL, "test@example.com");

        String expectedResponse = "HTTP/1.1 200 OK ";

        //when
        byte[] actualResponse = controller.createUser(queryParams);

        //then
        assertAll(
                () -> assertEquals(actualResponse[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(actualResponse[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(actualResponse[2], expectedResponse.getBytes()[2])
        );
    }
}
