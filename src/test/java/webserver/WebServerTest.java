package webserver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

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
    }
}