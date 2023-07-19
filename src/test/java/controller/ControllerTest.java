package controller;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static db.Database.clear;
import static http.HttpMethod.GET;
import static http.HttpMethod.POST;
import static http.HttpStatus.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller controller;

    @BeforeEach
    void setup() {
        controller = new Controller();
    }

    @Test
    @DisplayName("적절한 확장자가 아닌 잘못된 요청에 NOT_FOUND 404 상태를 반환해야 한다")
    void wrongRequestNoExtension() {
        // Given
        HttpRequest httpRequest = new HttpRequest.RequestBuilder(GET, "/index.htm1", "HTTP/1.1").build();

        // When
        HttpResponse httpResponse = controller.loadFileByRequest(httpRequest).build();

        // Then
        assertThat(httpResponse.getHttpStatus()).isEqualTo(NOT_FOUND);
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
        String uri = "/user/create?userId=kimahhh&password=1234&name=김아현&email=kimahyunn132@gmail.com";
        HttpRequest httpRequest = new HttpRequest.RequestBuilder("GET", uri, "HTTP/1.1").build();
        Controller controller = new Controller();

        // When
        controller.loadFileByRequest(httpRequest);

        // Then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(Database.findAll().size())
                .as("Database의 크기가 1이 아닙니다.\n현재 값: %d", Database.findAll().size()).isEqualTo(1);
        softAssertions.assertThat(Database.findUserById("kimahhh").getName())
                .as("Id로 이름을 찾을 수 없습니다.\n 현재 값: %s", Database.findUserById("kimahhh").getName()).isEqualTo("김아현");
        softAssertions.assertAll();
    }

}