package webserver;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Http.Headers;

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
                .get("/index.html")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.HTML);
    }

    @DisplayName("http://localhost:8080/styles.css 를 요청했을때 src/main/resources/static/css 디렉토리의 styles.css 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void requestCSS() {
        RestAssured.given().log().all()
                .when()
                .contentType("text/css")
                .get("/css/styles.css")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType("text/css");
    }

    @DisplayName("http://localhost:8080/80-text.png 를 요청했을때 src/main/resources/static/images 디렉토리의 80-text.png 파일을 읽어 클라이언트에 응답한다.")
    @Test
    void requestImage() {
        RestAssured.given().log().all()
                .when()
                .contentType("image/png")
                .get("/images/80-text.png")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType("image/png");
    }

    @DisplayName("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net 를 요청했을때 회원가입한다.")
    @Test
    void createUser() {
        RestAssured.given().log().all()
                .when()
                .body("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net")
                .post("/user/create")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_MOVED_TEMPORARILY);
    }

    @DisplayName("회원가입된 유저로 /user/login 을 요청하면 index.html로 이동한다.")
    @Test
    void loginSuccess() {
        // given
        회원가입("javajigi", "password", "박재성", "javajigi@slip.net");

        // when
        RestAssured.given().log().all()
                .when()
                .body("userId=javajigi&password=password")
                .post("/user/login")
                .then().log().all()
                .assertThat()
                .header(Headers.LOCATION.getName(), "/index.html")
                .statusCode(HttpStatus.SC_MOVED_TEMPORARILY);
    }

    @DisplayName("회원가입되지 않은 유저로 /user/login 을 요청하면 /user/login_failed.html로 이동한다.")
    @Test
    void loginFail() {
        // when
        RestAssured.given().log().all()
                .when()
                .body("userId=javajigi&password=password")
                .post("/user/login")
                .then().log().all()
                .assertThat()
                .header(Headers.LOCATION.getName(), "/user/login_failed.html")
                .statusCode(HttpStatus.SC_MOVED_TEMPORARILY);
    }

    @DisplayName("사용자가 로그인 상태일 경우 /index.html에서 사용자 이름을 표시해 준다.")
    @Test
    void displayLoginUserName() {
        // given
        회원가입("javajigi", "password", "박재성", "javajigi@slip.net");

        Cookie cookie = RestAssured.given().log().all()
                .when()
                .body("userId=javajigi&password=password")
                .post("/user/login")
                .then().log().all()
                .assertThat()
                .header(Headers.LOCATION.getName(), "/index.html")
                .statusCode(HttpStatus.SC_MOVED_TEMPORARILY)
                .extract()
                .response()
                .getDetailedCookie("sid");

        // when
        var response = RestAssured.given().log().all()
                .when()
                .cookie(cookie)
                .get("/index.html")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        Assertions.assertThat(response.body().asString()).contains("javajigi");
    }

    @DisplayName("사용자가 로그인 상태가 아닐 경우 /index.html에서 [로그인] 버튼을 표시해 준다.")
    @Test
    void displayLoginButton() {
        // when
        var response = RestAssured.given().log().all()
                .when()
                .get("/index.html")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        Assertions.assertThat(response.body().asString())
                .contains("<a href=\"user/login.html\" role=\"button\">로그인</a>");
    }

    @DisplayName("사용자가 로그인 상태일 경우 http://localhost:8080/user/list 에서 사용자 목록을 출력한다.")
    @Test
    void displayUserList() {
        회원가입("user1", "password1", "최윤식", "user1@slip.net");
        회원가입("user2", "password2", "서시언", "user2@slip.net");
        회원가입("user3", "password3", "이요한", "user3@slip.net");

        Cookie cookie = RestAssured.given().log().all()
                .when()
                .body("userId=user1&password=password")
                .post("/user/login")
                .then().log().all()
                .assertThat()
                .header(Headers.LOCATION.getName(), "/index.html")
                .statusCode(HttpStatus.SC_MOVED_TEMPORARILY)
                .extract()
                .response()
                .getDetailedCookie("sid");

        // when
        var response = RestAssured.given().log().all()
                .when()
                .cookie(cookie)
                .get("/index.html")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        Assertions.assertThat(response.body().asString())
                .contains(
                        "user1", "user2", "user3",
                        "최윤식", "서시언", "이요한",
                        "user1@slip.net", "user2@slip.net", "user3@slip.net"
                );
    }

    private static void 회원가입(final String userId, final String password, final String name, final String email) {
        RestAssured.given().log().all()
                .when()
                .body("userId=" + userId + "&password=" + password + "&name=" + name + "&email=" + email)
                .post("/user/create")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_MOVED_TEMPORARILY);
    }
}
