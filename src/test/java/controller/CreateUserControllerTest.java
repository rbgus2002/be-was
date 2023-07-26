package controller;

import webserver.Controller;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateUserControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

//    @Test
//    @DisplayName("회원가입이 된다.")
//    void signUpUser() {
//        Map<String, String> params = new HashMap<>();
//        params.put("userId", "uuu");
//        params.put("password", "ppp");
//        params.put("name", "nnn");
//        params.put("email", "eee@a.com");
//        HttpRequest request = createRequest(params);
//
//        controller.process(request);
//
//        User user = UserRepository.findUserById("uuu");
//        assertEquals("uuu", user.getUserId());
//        assertEquals("ppp", user.getPassword());
//        assertEquals("nnn", user.getName());
//        assertEquals("eee@a.com", user.getEmail());
//    }

//    private HttpRequest createRequest(Map<String, String> params) {
//        return new HttpRequest(
//                new RequestLine(GET, "/create", "HTTP/1.1", ContentType.HTML, params),
//                null,
//                null
//        );
//    }
}