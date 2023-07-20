package controller;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import static db.Database.clear;
import static http.HttpMethod.GET;
import static http.HttpMethod.POST;
import static http.HttpStatus.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller controller;
    SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        controller = new Controller();
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("존재하지 않는 리소스에 대한 요청 시 NOT_FOUND 404 상태를 반환해야 한다")
    void wrongRequestNoFile() {
        // Given
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(GET, "/no_index.html", "HTTP/1.1").build();

        // When
        HttpResponse httpResponse = controller.loadFileByRequest(httpRequest).build();

        // Then
        assertThat(httpResponse.getHttpStatus()).isEqualTo(NOT_FOUND);
    }

    @Test
    @DisplayName("GET 방식 사용 시: 존재하지 않는 경로인 경우 NOT_FOUND 404 상태를 반환해야 한다")
    void wrongRequestUriUsingGet() {
        // Given
        String uri = "/user/user/create?userId=kimahhh&password=1234&name=김아현&email=kimahyunn132@gmail.com";
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(GET, uri, "HTTP/1.1").build();

        // When
        HttpResponse httpResponse = controller.loadFileByRequest(httpRequest).build();

        // Then
        assertThat(httpResponse.getHttpStatus()).isEqualTo(NOT_FOUND);
    }

    @Test
    @DisplayName("POST 방식 사용 시: 존재하지 않는 경로인 경우 NOT_FOUND 404 상태를 반환해야 한다")
    void wrongRequestUriUsingPost() {
        // Given
        String uri = "/user/user/create";
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(POST, uri, "HTTP/1.1")
                .setBody("userId=kimahhh&password=1234&name=김아현&email=kimahyunn132@gmail.com")
                .build();

        // When
        HttpResponse httpResponse = controller.loadFileByRequest(httpRequest).build();

        // Then
        assertThat(httpResponse.getHttpStatus()).isEqualTo(NOT_FOUND);
    }

    @Test
    @DisplayName("유저가 회원가입 시 Database에 정보가 추가되어야 한다")
    void signUp() {
        // Given
        clear();
        String uri = "/user/create";
        String body = "userId=kimahhh&password=1234&name=김아현&email=kimahyunn132@gmail.com";
        HttpRequest httpRequest = new HttpRequest.RequestBuilder("POST", uri, "HTTP/1.1")
                .setBody(body)
                .build();
        Controller controller = new Controller();

        // When
        controller.loadFileByRequest(httpRequest);

        // Then
        softAssertions.assertThat(Database.findAll().size())
                .as("Database의 크기가 1이 아닙니다.\n현재 값: %d", Database.findAll().size()).isEqualTo(1);
        softAssertions.assertThat(Database.findUserById("kimahhh").getName())
                .as("Id로 이름을 찾을 수 없습니다.\n 현재 값: %s", Database.findUserById("kimahhh").getName()).isEqualTo("김아현");
    }
    @Test
    @DisplayName("유저가 회원가입 시 정보를 모두 입력하지 않는 경우, NOT_FOUND 404 상태를 반환해야 한다")
    void notEnoughInformation() {
        // Given
        clear();
        String uri = "/user/create";
        String body = "userId=kimahhh&password=&name=김아현&email=kimahyunn132@gmail.com";
        HttpRequest httpRequest = new HttpRequest.RequestBuilder("POST", uri, "HTTP/1.1")
                .setBody(body)
                .build();
        Controller controller = new Controller();

        // When
        HttpResponse httpResponse = controller.loadFileByRequest(httpRequest).build();

        // Then
        softAssertions.assertThat(httpResponse.getHttpStatus())
                .as("Http Status 상태가 404 Not Found가 아닙니다.\n현재 값: %s", httpResponse.getHttpStatus()).isEqualTo(NOT_FOUND);
    }

}