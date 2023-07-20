package webserver;

import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.controllers.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.controllers.ResolveController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class HttpControllerTest {
    SoftAssertions s = new SoftAssertions();

    @ParameterizedTest
    @ValueSource(strings = {"/index.html","/user/form.html"})
    @DisplayName("정적 파일 리턴 기능 확인 테스트")
    void handleStaticFileReturn(String fileName) throws IOException {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        HttpRequest testRequest = builder
                .uri(fileName)
                .path(fileName)
                .version("HTTP/1.1")
                .build();

        Controller controller = ResolveController.getInstance().resolveRequest(testRequest);
        HttpResponse response = controller.handle(testRequest);

        String template_path = System.getProperty("user.dir") + "/src/main/resources/templates/" + fileName;
        HttpResponse actual = HttpResponse.newBuilder()
                .statusCode(200)
                .version("HTTP/1.1")
                .body(Files.readAllBytes(Paths.get(template_path)))
                .build();

        s.assertThat(response.version()).isEqualTo(actual.version());
        s.assertThat(response.statusCode()).isEqualTo(actual.statusCode());
        s.assertThat(response.statusText()).isEqualTo(actual.statusText());
        s.assertThat(response.body()).isEqualTo(actual.body());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/css/bootstrap.min.css","/notExistingFile.html"})
    @DisplayName("정적 파일 리턴 기능 확인 테스트2")
    void handleStaticFileReturn2(String fileName) throws IOException {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        HttpRequest testRequest = builder
                .uri(fileName)
                .path(fileName)
                .version("HTTP/1.1")
                .build();

        Controller controller = ResolveController.getInstance().resolveRequest(testRequest);
        HttpResponse response = controller.handle(testRequest);

        String template_path = System.getProperty("user.dir") + "/src/main/resources/templates/" + fileName;

        HttpResponse actual = HttpResponse.newBuilder()
                .version("HTTP/1.1")
                .statusCode(404)
                .body("요청하신 파일을 찾을 수 없습니다.".getBytes())
                .build();

        s.assertThat(response.version()).isEqualTo(actual.version());
        s.assertThat(response.statusCode()).isEqualTo(actual.statusCode());
        s.assertThat(response.statusText()).isEqualTo(actual.statusText());
        s.assertThat(response.body()).isEqualTo(actual.body());
    }

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
                .statusCode(200)
                .version("HTTP/1.1")
                .body(actualUser.toString().getBytes())
                .build();

        s.assertThat(response.version()).isEqualTo(actual.version());
        s.assertThat(response.statusCode()).isEqualTo(actual.statusCode());
        s.assertThat(response.statusText()).isEqualTo(actual.statusText());
        s.assertThat(response.body()).isEqualTo(actual.body());
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
                .statusCode(400)
                .version("HTTP/1.1")
                .build();

        s.assertThat(response.version()).isEqualTo(actual.version());
        s.assertThat(response.statusCode()).isEqualTo(actual.statusCode());
        s.assertThat(response.statusText()).isEqualTo(actual.statusText());
    }

}
