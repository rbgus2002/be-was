package webserver;

import db.Database;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {

    @Test
    @DisplayName("localhost:8080/index.html로 get 요청이 들어오면 index.html 파일을 반환해야 한다.")
    void indexHtml() throws IOException {
        assertTrue(Arrays.equals(RestAssured.get("/index.html")
                .getBody()
                .asByteArray(), Files.readAllBytes(new File("src/main/resources/templates/index.html").toPath())));
    }

    @Test
    @DisplayName("localhost:8080/styles.css로 get 요청이 들어오면 styles.css 파일을 반환해야 한다.")
    void stylesCss() throws IOException {
        Arrays.equals(RestAssured.get("/styles.css")
                .getBody()
                .asByteArray(), Files.readAllBytes(new File("src/main/resources/static/css/styles.css").toPath()));
    }

    @Test
    @DisplayName("localhost:8080/user/create로 적절한 파라미터와 함께 get 요청이 들어오면 로그인 처리를 하고 status 200을 반환해야 한다.")
    void createUser() throws IOException {

        RestAssured
                .given()
                    .param("userId", "id")
                    .param("password", "password")
                    .param("email", "email")
                    .param("name", "name")
                .when()
                    .get("/user/create")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .contentType(ContentType.TEXT);
    }

    @Test
    @DisplayName("localhost:8080/user/create로 get 요청시 파라미터가 제대로 들어있지 않다면 400에러를 반환해야 한다.")
    void createUserBadRequest() throws IOException {
        RestAssured
                .given()
                    .param("userId", "id")
                    .param("password", "password")
                    .param("email", "email")
                .when()
                    .get("/user/create")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .contentType(ContentType.TEXT);
    }

    @Test
    @DisplayName("일치하는 메소드와 url을 처리할 controller가 없을 시 404 not found를 반환한다.")
    void NotFound() throws IOException {
        RestAssured
                .when()
                    .get("/abc/abc")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .contentType(ContentType.TEXT);
    }


}