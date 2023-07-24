package controller;

import global.request.RequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.*;

import static model.db.Database.deleteAll;
import static model.db.Database.findAll;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Controller 테스트")
class ControllerTest {

    Controller controller = new Controller();

    @BeforeEach
    void setup() {
        deleteAll();
    }

    @Test
    @DisplayName("root 메서드 테스트")
    void testRoot() throws IOException {
        //given
        String expectedResponse = "HTTP/1.1 200 OK \n";

        Map<String, String> map = new LinkedHashMap<>();
        //when
        byte[] response = controller.root(map);

        //then
        assertAll(
                () -> assertEquals(response[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(response[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(response[2], expectedResponse.getBytes()[2])
        );
    }


    @Test
    @DisplayName("createUser 메서드 - 유효한 쿼리 매개변수를 받아서 사용자 생성")
    void testCreateUserWithValidQueryParams() throws IOException {
        //given
        RequestBody body = new RequestBody("\n" +
                "userId=chocochip&password=password&name=kiho&email=fingercut@naver.com");

        String expectedResponse = "HTTP/1.1 200 OK ";

        //when
        byte[] actualResponse = controller.createUser(body);

        //then
        assertAll(
                () -> assertEquals(actualResponse[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(actualResponse[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(actualResponse[2], expectedResponse.getBytes()[2])
        );
    }

    @Test
    @DisplayName("사용자 회원가입에 성공한다.")
    void testCreateUserAndFind() throws IOException {
        //given
        RequestBody body = new RequestBody("\n" +
                "userId=chocochip&password=password&name=kiho&email=fingercut@naver.com");

        String expectedResponse = "HTTP/1.1 200 OK ";

        //when
        byte[] actualResponse = controller.createUser(body);

        //then
        assertEquals(findAll().size(), 1);
    }
}
