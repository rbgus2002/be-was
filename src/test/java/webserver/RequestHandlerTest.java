package webserver;

import db.Database;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.User;
import org.apache.groovy.json.internal.IO;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.reponse.HttpResponse;
import webserver.reponse.HttpResponseStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {

    @Test
    @DisplayName("localhost:8080/styles.css로 get 요청이 들어오면 styles.css 파일을 반환해야 한다.")
    void stylesCss() throws IOException {
        Arrays.equals(RestAssured.get("/styles.css")
                .getBody()
                .asByteArray(), Files.readAllBytes(new File("src/main/resources/static/css/styles.css").toPath()));
    }

    @Test
    @DisplayName("localhost:8080/user/create로 적절한 파라미터와 함께 post 요청이 들어오면 로그인 처리를 하고 status 302을 반환해야 한다.")
    void createUser() throws IOException {

        RestAssured
                .given()
                    .body("userId=userId&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net")
                .when()
                    .post("/user/create")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_MOVED_TEMPORARILY);
    }

    @Test
    @DisplayName("localhost:8080/user/create로 post 요청시 파라미터가 제대로 들어있지 않다면 400에러를 반환해야 한다.")
    void createUserBadRequest() throws IOException {
        RestAssured
                .given()
                    .body("userId=abc&password=abc&name=abc")
                .when()
                    .post("/user/create")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .contentType(ContentType.TEXT);
    }

    @Test
    @DisplayName("일치하는 메소드와 url을 처리할 controller가 없을 시 404 not found를 반환한다.")
    void notFound() throws IOException {
        RestAssured
                .when()
                    .get("/abc/abc")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .contentType(ContentType.TEXT);
    }

    @Test
    @DisplayName("이미 존재하는 userId로 회원가입을 시도할 경우 409 Confilct 에러를 반환해야 한다")
    void signUpConflict() {

        RestAssured
                .given()
                .body("userId=abc&password=abc&name=abc&email=abc")
                .when()
                .post("/user/create");


        RestAssured
                .given()
                .body("userId=abc&password=abc&name=abc&email=abc")
                .when()
                .post("/user/create")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    @DisplayName("로그인을 성공할 경우 Cookie에 sessionId를 전달하고 메인화면으로 redirect해야한다.")
    void loginSuccess() {
        RestAssured
                .given()
                    .body("userId=abc&password=abc&name=abc&email=abc")
                .when()
                    .post("/user/create");

        RestAssured
                .given()
                    .body("userId=abc&password=abc")
                .when()
                    .post("/user/login")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_MOVED_TEMPORARILY)
                    .header("Location", equalTo("/index.html"))
                    .cookie("sid", notNullValue());
    }

    @Test
    @DisplayName("로그인을 실패 할 경우 로그인 실패 화면으로 redirect 해야한다.")
    void loginFailed() {
        RestAssured
                .given()
                .body("userId=abc&password=abc&name=abc&email=abc")
                .when()
                .post("/user/create");

        RestAssured
                .given()
                .body("userId=abc&password=abcdd")
                .when()
                .post("/user/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_MOVED_TEMPORARILY)
                .header("Location", equalTo("/user/login_failed.html"));
    }


}