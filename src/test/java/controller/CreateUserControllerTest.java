package controller;

import db.Database;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import common.HttpRequest;
import common.RequestLine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new CreateUserController();
    }

    @Test
    @DisplayName("회원가입이 된다.")
    void signUpUser() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "uuu");
        params.put("password", "ppp");
        params.put("name", "nnn");
        params.put("email", "eee@a.com");
        HttpRequest request = createRequest(params);

        controller.process(request);

        User user = Database.findUserById("uuu");
        assertEquals("uuu", user.getUserId());
        assertEquals("ppp", user.getPassword());
        assertEquals("nnn", user.getName());
        assertEquals("eee@a.com", user.getEmail());
    }

    private HttpRequest createRequest(Map<String, String> params) {
        return new HttpRequest(
                new RequestLine(HttpRequest.Method.GET, "/create", "HTTP/1.1", params),
                null,
                null
        );
    }
}