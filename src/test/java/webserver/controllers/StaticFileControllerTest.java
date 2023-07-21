package webserver.controllers;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static webserver.http.enums.HttpResponseStatus.NOT_FOUND;
import static webserver.http.enums.HttpResponseStatus.OK;

class StaticFileControllerTest {
    SoftAssertions s = new SoftAssertions();

    @ParameterizedTest
    @ValueSource(strings = {"/index.html", "/user/form.html"})
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
                .status(OK)
                .version("HTTP/1.1")
                .body(Files.readAllBytes(Paths.get(template_path)))
                .build();

        s.assertThat(response.version()).isEqualTo(actual.version());
        s.assertThat(response.statusCode()).isEqualTo(actual.statusCode());
        s.assertThat(response.statusText()).isEqualTo(actual.statusText());
        s.assertThat(response.body()).isEqualTo(actual.body());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/css/bootstrap.min.css", "/notExistingFile.html"})
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
                .status(NOT_FOUND)
                .body("요청하신 파일을 찾을 수 없습니다.".getBytes())
                .build();

        s.assertThat(response.version()).isEqualTo(actual.version());
        s.assertThat(response.statusCode()).isEqualTo(actual.statusCode());
        s.assertThat(response.statusText()).isEqualTo(actual.statusText());
        s.assertThat(response.body()).isEqualTo(actual.body());
    }

}