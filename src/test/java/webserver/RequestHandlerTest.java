package webserver;

import controller.Controller;
import db.Database;
import http.HttpRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static db.Database.clear;

class RequestHandlerTest {
    @BeforeEach
    void setup() {
        clear();
    }

    @Test
    @DisplayName("유저가 회원가입 시 Database에 정보가 추가되어야 한다")
    void signUp() {
        // Given
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