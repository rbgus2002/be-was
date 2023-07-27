package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static constant.Uri.USER_CREATE_REQUEST_URI;
import static io.restassured.RestAssured.DEFAULT_PORT;
import static io.restassured.RestAssured.given;
import static model.enums.HttpStatusCode.*;

class RequestHandlerTest {
    private final int PORT = DEFAULT_PORT;

    @Test
    @DisplayName("index.html 요청 테스트")
    void htmlRequestTest() {
        given()
            .log().all()
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
    @DisplayName("/user/create 요청 후 리다이렉트 되야 한다.")
    void redirectAfterRequestCreateUser() {
        String uri = USER_CREATE_REQUEST_URI;
        given()
            .log().all()
            .redirects().follow(false)
        .when()
            .post(uri)
        .then()
            .header("Location", "/index.html")
            .statusCode(MOVED_PERMANENTLY.getValue());
    }

    @Test
    @DisplayName("/user/create 요청 테스트")
    void requestCreateUser() {
        String uri = USER_CREATE_REQUEST_URI;
        String body = "name=qwe;id=id213;password=pw1234;email=q@qq.qq";

        given()
            .log().all()
            .redirects().follow(false)
//            .body(body)
        .when()
            .post(uri)
        .then()
            .statusCode(200); // 왜 안 될까...
    }
}