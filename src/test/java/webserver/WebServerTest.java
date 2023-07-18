package webserver;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("웹 서버 테스트")
class WebServerTest {
    @BeforeAll
    static void beforeAll() {
        Main.main(null);
    }

    @DisplayName("http://localhost:8080/index.html 로 접속했을 때 src/main/resources/templates 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void request() {
        RestAssured.given().log().all()
                .when()
                .contentType(ContentType.HTML)
                .get("index.html")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.HTML);
    }
}
