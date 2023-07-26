package controller;

import global.request.RequestBody;
import global.request.RequestHeader;
import model.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static model.db.Database.deleteAll;
import static model.db.Database.findAll;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Controller 테스트")
class ControllerTest {

    Controller controller = new Controller();

    @BeforeEach
    void setup() {
        deleteAll();
    }

    @Test
    @DisplayName("root 메서드 테스트")
    void testRoot() throws IOException {
        // given
        RequestHeader header = new RequestHeader("ost: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Origin: http://localhost:8080\n" +
                "Accept: */*\n" +
                "Referer: http://localhost:8080/css/bootstrap.min.css\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                "Cookie: sid=123456");
        RequestBody body = new RequestBody("\n" +
                "userId=chocochip&password=password&name=kiho&email=fingercut@naver.com");
        Session session = new Session();
        String expectedResponse = "HTTP/1.1 200 OK \n";

        Map<String, String> map = new LinkedHashMap<>();
        // when
        byte[] response = controller.root(header, body, session);

        // then
        assertAll(
                () -> assertEquals(response[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(response[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(response[2], expectedResponse.getBytes()[2])
        );
    }

    @Test
    @DisplayName("createUser 메서드 - 유효한 쿼리 매개변수를 받아서 사용자 생성")
    void testCreateUserWithValidQueryParams() throws IOException {
        // given
        RequestHeader header = new RequestHeader("ost: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Origin: http://localhost:8080\n" +
                "Accept: */*\n" +
                "Referer: http://localhost:8080/css/bootstrap.min.css\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                "Cookie: sid=123456");
        RequestBody body = new RequestBody("\n" +
                "userId=chocochip&password=password&name=kiho&email=fingercut@naver.com");
        Session session = new Session();
        String expectedResponse = "HTTP/1.1 302 Found ";

        // when
        byte[] actualResponse = controller.createUser(header, body, session);

        // then
        assertAll(
                () -> assertEquals(actualResponse[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(actualResponse[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(actualResponse[2], expectedResponse.getBytes()[2])
        );
    }

    @Test
    @DisplayName("사용자 회원가입에 성공한다.")
    void testCreateUserAndFind() throws IOException {
        // given
        RequestHeader header = new RequestHeader("ost: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Origin: http://localhost:8080\n" +
                "Accept: */*\n" +
                "Referer: http://localhost:8080/css/bootstrap.min.css\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                "Cookie: sid=123456");
        RequestBody body = new RequestBody("\n" +
                "userId=chocochip&password=password&name=kiho&email=fingercut@naver.com");
        Session session = new Session();

        // when
        byte[] actualResponse = controller.createUser(header, body, session);

        // then
        assertEquals(findAll().size(), 1);
    }

    @Test
    @DisplayName("userLogin 메서드 - 유효한 헤더와 바디 매개변수로 로그인에 성공한다.")
    void testUserLoginWithValidHeaderAndBodyParams() throws IOException {
        // given
        RequestHeader header = new RequestHeader("\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        RequestBody body = new RequestBody("\nuserId=chocochip&password=password");
        Session session = new Session();
        String expectedResponse = "HTTP/1.1 302 Found ";

        // when
        byte[] actualResponse = controller.userLogin(header, body, session);

        // then
        assertAll(
                () -> assertEquals(actualResponse[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(actualResponse[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(actualResponse[2], expectedResponse.getBytes()[2])
        );
    }

    @Test
    @DisplayName("userLogin 메서드 - 존재하지 않는 사용자로 로그인하면 로그인 실패 페이지로 리다이렉트한다.")
    void testUserLoginWithNonExistentUser() throws IOException {
        // given
        RequestHeader header = new RequestHeader("ost: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Origin: http://localhost:8080\n" +
                "Accept: */*\n" +
                "Referer: http://localhost:8080/css/bootstrap.min.css\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
                "Cookie: sid=123456");
        RequestBody body = new RequestBody("\nuserId=non_existent_user&password=invalid_password");
        Session session = new Session();
        String expectedResponse = "HTTP/1.1 302 Found ";

        // when
        byte[] actualResponse = controller.userLogin(header, body, session);

        // then
        assertAll(
                () -> assertEquals(actualResponse[0], expectedResponse.getBytes()[0]),
                () -> assertEquals(actualResponse[1], expectedResponse.getBytes()[1]),
                () -> assertEquals(actualResponse[2], expectedResponse.getBytes()[2])
        );
    }
}
