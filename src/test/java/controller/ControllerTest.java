package controller;

import http.HttpResponse;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;
    @BeforeEach
    void init(){
        controller = new Controller();
        controller.createUser(createBodyForUser1());
    }

    private Map<String, String> createBodyForUser1(){
        Map<String, String> body = new HashMap<>();
        body.put("userId", "rbgus2002");
        body.put("password", "0000");
        body.put("name", "최규현");
        body.put("email", "rbgus2002@naver.com");
        return body;
    }

    @Nested
    class loginUser{
        @Test
        @DisplayName("아이디 비밀번호가 일치하면 정상적으로 로그인되며 redirect 한다.")
        void success(){
            // given
            Map<String, String> body = Map.of("userId", "rbgus2002", "password", "0000");

            // when
            HttpResponse result = controller.loginUser(body);

            // then
            assertEquals(HttpResponse.redirect(), result);
        }

        @Test
        @DisplayName("아이디 비밀번호가 일치하지 않으면 로그인에 실패하고 login_failed.html로 이동한다")
        void invalidRequestLogin(){
            // given
            Map<String, String> body = Map.of("userId", "tc", "password", "0000");

            // when
            HttpResponse result = controller.loginUser(body);

            // then
            assertEquals(HttpResponse.redirect(), result);
        }
    }
}