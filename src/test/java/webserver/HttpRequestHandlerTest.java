package webserver;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestHandlerTest {

    @Test
    @DisplayName("handleUserCreateRequest의 기능 확인 테스트")
    void handleUserCreateRequest() throws IOException {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        HttpRequest testRequest = builder.path("/user/create")
                .uri("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net")
                .build();
        byte[] result = HttpRequestHandler.handleRequest(testRequest);

        User actual = new User("javajigi", "password", "박재성", "javajigi@slipp.net");
        assertArrayEquals(actual.toString().getBytes(), result);
    }

}