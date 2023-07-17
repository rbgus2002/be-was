package webserver;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("웹 서버 테스트")
class WebServerTest {
    @BeforeAll
    static void beforeAll() throws Exception {
        WebServer.main(new String[]{String.valueOf(8080)});
    }

    @DisplayName("http://localhost:8080/index.html 로 접속했을 때 src/main/resources/templates 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void request() {
        RestAssured.given()
                .when()
                .get("/index.html")
                .then()
                .statusCode(200)
                .contentType("text/html;charset=utf-8")
                .statusCode(HttpStatus.SC_OK);
    }
}