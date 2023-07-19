package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Controller Test")
class UserControllerTest {

    @Test
    @DisplayName("GET으로 회원가입을 요청하면 입력 정보를 바탕으로 유저가 생성되어야 한다.")
    void createUser() throws IOException {
        // given
        String method = "GET";
        String requestPath = "/user/create?userId=wjddus9132&password=1234&name=김정연&email=wjddus9132@gmail.com";
        String version = "HTTP/1.1";

        HttpRequest httpRequest = new HttpRequest(method, requestPath, version);

        // when
        String url = UserController.createUser(httpRequest);

        //then
        assertEquals("/index.html", url);
    }

}
