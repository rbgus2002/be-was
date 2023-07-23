package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Controller Test")
class ControllerTest {

    @Test
    @DisplayName("GET으로 회원가입을 요청하면 입력 정보를 바탕으로 유저가 생성되어야 한다.")
    void createUser() throws IOException {
        // given
        String firstLine = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
        Map<String, String> parameters = HttpRequest.parseQueryString(firstLine);

        // when
        Controller controller = new Controller();
        HttpResponse httpResponse = controller.createUser(parameters);

        //then
        assertEquals("/index.html", httpResponse.getPath());
    }

}
