package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Controller Test")
class ControllerTest {

    Controller controller = new Controller();

    @Test
    @DisplayName("GET으로 회원가입을 요청하면 입력 정보를 바탕으로 유저가 생성되어야 한다.")
    void createUser() throws IOException {
        // given
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "test123");
        parameters.put("password", "password");
        parameters.put("name", "userA");
        parameters.put("email", "test@gmail.com");

        // when
        HttpResponse httpResponse = controller.createUser(parameters);

        //then
        assertEquals("/index.html", httpResponse.getPath());
    }

}
