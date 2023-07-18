package webserver;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static constant.Uri.USER_CREATE_URI;
import static io.restassured.RestAssured.DEFAULT_PORT;
import static io.restassured.RestAssured.given;
import static model.enums.HttpStatusCode.*;

class RequestHandlerTest {
    private final int PORT = DEFAULT_PORT;

    @Test
    @DisplayName("index.html 요청 테스트")
    void htmlRequestTest() {
        given()
            .port(PORT)
        .when()
            .get("/index.html")
        .then()
            .statusCode(OK.getValue());
    }

    @Test
    @DisplayName("index.html 요청 테스트")
    void htmlRequestFailTest() {
        // TODO rest assured
        given()
        .when()
            .get("/any.html")
        .then()
            .statusCode(BAD_REQUEST.getValue());
    }

    @Test
    @DisplayName("/user/create 요청 테스트")
    void requestCreateUser() {
        String uri = USER_CREATE_URI;
        given()
            .port(PORT)
                .param("name", "kim")
                .param("email", "a@a.a")
                .param("userId", "id")
                .param("password", "pawd")
        .when()
            .get(uri)
        .then()
            .statusCode(CREATED.getValue());
    }
}