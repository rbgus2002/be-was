package webserver.controller;

import db.Database;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class UserControllerTest {

    @Test
    @DisplayName("회원가입 시 올바르게 폼 입력 시 정상적으로 회원가입된다.")
    void saveUser() {
        //given
        String requestBody = "userId=bigsand123&password=123&name=bigsand&email=bigsand@example.com";

        //when
        Response response = given()
                .contentType(ContentType.URLENC)
                .body(requestBody)
                .when()
                .post("/user/create")
                .then()
                .statusCode(302)
                .extract()
                .response();

        //then
        assertSoftly(softAssertions ->{
            softAssertions.assertThat(response.getStatusCode()).isEqualTo(302);
            softAssertions.assertThat(response.getHeader("Content-Type")).isEqualTo("text/html;charset=utf-8");
            softAssertions.assertThat(response.getHeader("Content-Length")).isEqualTo("0");
            softAssertions.assertThat(response.getHeader("Location")).isEqualTo("/index.html");
                }
        );

    }

    @Test
    @DisplayName("회원가입 시 폼 입력을 올바르게 입력하지 않으면 회원가입 폼으로 리다이렉트")
    void saveUser_Exception() {
        //given
        String requestBody = "userId=bigsand123&password=&name=bigsand&email=bigsand@example.com";

        //when
        Response response = given()
                .contentType(ContentType.URLENC)
                .body(requestBody)
                .when()
                .post("/user/create")
                .then()
                .statusCode(302)
                .extract()
                .response();

        //then
        assertSoftly(softAssertions ->{
                    softAssertions.assertThat(response.getStatusCode()).isEqualTo(302);
                    softAssertions.assertThat(response.getHeader("Content-Type")).isEqualTo("text/html;charset=utf-8");
                    softAssertions.assertThat(response.getHeader("Content-Length")).isEqualTo("0");
                    softAssertions.assertThat(response.getHeader("Location")).isEqualTo("/user/form.html");
                }
        );

    }

    @Test
    @DisplayName("로그인 시 유저 정보가 올바르지 않으면 로그인 실패 페이지로 이동")
    void login_failed() {
        //given
        Database.addUser(new User("bigsand123", "123", "bigsand", "bigsand@example.com"));
        String requestBody = "userId=bigsand123&password=11111";

        //when
        Response response = given()
                .contentType(ContentType.URLENC)
                .body(requestBody)
                .when()
                .post("/user/login")
                .then()
                .statusCode(302)
                .extract()
                .response();

        //then
        assertSoftly(softAssertions ->{
                    softAssertions.assertThat(response.getStatusCode()).isEqualTo(302);
                    softAssertions.assertThat(response.getHeader("Content-Type")).isEqualTo("text/html;charset=utf-8");
                    softAssertions.assertThat(response.getHeader("Content-Length")).isEqualTo("0");
                    softAssertions.assertThat(response.getHeader("Location")).isEqualTo("/user/login_failed.html");
                }
        );

    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        //given
        Database.addUser(new User("bigsand123", "123", "bigsand", "bigsand@example.com"));
        String requestBody = "userId=bigsand123&password=123";

        //when
        Response response = given()
                .contentType(ContentType.URLENC)
                .body(requestBody)
                .when()
                .post("/user/login")
                .then()
                .statusCode(302)
                .extract()
                .response();

        //then
        assertSoftly(softAssertions ->{
                    softAssertions.assertThat(response.getStatusCode()).isEqualTo(302);
                    softAssertions.assertThat(response.getHeader("Content-Type")).isEqualTo("text/html;charset=utf-8");
                    softAssertions.assertThat(response.getHeader("Content-Length")).isEqualTo("0");
                    softAssertions.assertThat(response.getHeader("Location")).isEqualTo("/index.html");
                }
        );

    }

}