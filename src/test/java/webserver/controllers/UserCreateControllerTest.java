package webserver.controllers;

import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

import static webserver.http.enums.ContentType.HTML;
import static webserver.http.enums.HttpResponseStatus.*;

class UserCreateControllerTest {

    SoftAssertions softly = new SoftAssertions();

    @Test
    @DisplayName("handleUserCreateRequest의 기능 확인 테스트")
    void handleUserCreateRequest() throws IOException {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        HttpRequest testRequest = builder
                .uri("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net")
                .path("/user/create")
                .version("HTTP/1.1")
                .build();

        Controller controller = ResolveController.getInstance().resolveRequest(testRequest);
        HttpResponse response = controller.handle(testRequest);

        User actualUser = new User("javajigi", "password", "박재성", "javajigi@slipp.net");

        HttpResponse actual = HttpResponse.newBuilder()
                .status(FOUND)
                .version("HTTP/1.1")
                .contentType(HTML)
                .setHeader("Location", "http://localhost:8080".concat("/user/form.html"))
                .build();

        softly.assertThat(response.version()).isEqualTo(actual.version());
        softly.assertThat(response.statusCode()).isEqualTo(actual.statusCode());
        softly.assertThat(response.statusText()).isEqualTo(actual.statusText());
        softly.assertThat(response.body()).isEqualTo(actual.body());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/user/create", "/user/create?"
            , "/user/create?password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
            , "/user/create?userId=&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
            , "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email="})
    @DisplayName("handleUserCreateRequest의 기능 확인 테스트2")
    void handleUserCreateRequest2(String wrongUri) throws IOException {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        HttpRequest testRequest = builder.path("/user/create")
                .uri(wrongUri)
                .path("/user/create")
                .version("HTTP/1.1")
                .build();

        Controller controller = ResolveController.getInstance().resolveRequest(testRequest);
        HttpResponse response = controller.handle(testRequest);

        HttpResponse actual = HttpResponse.newBuilder()
                .status(BAD_REQUEST)
                .version("HTTP/1.1")
                .build();

        softly.assertThat(response.version()).isEqualTo(actual.version());
        softly.assertThat(response.statusCode()).isEqualTo(actual.statusCode());
        softly.assertThat(response.statusText()).isEqualTo(actual.statusText());
    }

}