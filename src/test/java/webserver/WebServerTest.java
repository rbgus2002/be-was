package webserver;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class WebServerTest {

    @Test
    void getHome() {
        given()
                .when()
                .get("http://localhost:8080/")
                .then()
                .statusCode(200);
    }

    @Test
    void join() {
        String requestBody = "userId=abc&password=abc&name=abc&email=abc%40abc";
        given()
                .body(requestBody)
                .when()
                .post("http://localhost:8080/user/create")
                .then()
                .statusCode(302);
    }
}