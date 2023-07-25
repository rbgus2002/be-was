package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpStatusCode;

import static io.restassured.RestAssured.given;

class RequestHandlerTest {
    @Test
    @DisplayName("index.html 파일 요청 시 statusCode 200을 리턴해야한다.")
    void getIndexHtml() {
        given()
                .log().all()
                .when()
                .get("/index.html")
                .then()
                .statusCode(HttpStatusCode.OK.getCode());
    }

    @Test
    @DisplayName("존재하지 않는 파일 요청 시 statusCode 404을 리턴해야한다.")
    void getNoExistFile() {
        given()
                .log().all()
                .when()
                .get("/no_exist_file.html")
                .then()
                .statusCode(HttpStatusCode.NOT_FOUND.getCode());
    }

    @Test
    @DisplayName("POST /user/create 요청 시 302을 리턴해야한다.")
    void getUserCreate() {
        given()
                .log().all()
                .param("name", "이름")
                .param("email", "mail@mail.com")
                .param("userId", "id")
                .param("password", "passwd")
                .when()
                .post("/user/create")
                .then()
                .statusCode(HttpStatusCode.FOUND.getCode());
    }
}
