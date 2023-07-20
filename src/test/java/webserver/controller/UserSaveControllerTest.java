package webserver.controller;

import db.Database;
import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@DisplayName("회원가입 컨트롤러")
class UserSaveControllerTest {

    UserSaveController userSaveController;
    HttpResponse httpResponse;

    @BeforeEach
    void init() {
        Database.clear();
        userSaveController = new UserSaveController();
        httpResponse  = new HttpResponse();
    }

    @Test
    @DisplayName("회원가입 요청 시, Database에 회원 정보가 저장되어야 한다.")
    void userSaveTest() throws Exception {
        //given
        String requestMessage = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        HttpRequest httpRequest = createHttpRequest(requestMessage);

        //when
        userSaveController.process(httpRequest, httpResponse);
        User user = Database.findUserById("javajigi");

        //then
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(user).isNotEqualTo(null);
        softAssertions.assertThat(user.getUserId()).isEqualTo("javajigi");
        softAssertions.assertThat(user.getPassword()).isEqualTo("password");
        softAssertions.assertThat(user.getName()).isEqualTo("박재성");
        softAssertions.assertThat(user.getEmail()).isEqualTo("javajigi@slipp.net");
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("회원가입 요청 시, 누락된 URL 파라미터가 있으면 BAD REQUESET로 응답한다")
    void userSaveRequestWithMissingParameter() throws Exception {
        //given
        String requestMessageWithoutUserIdParameter = "GET /user/create?&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        HttpRequest httpRequest = createHttpRequest(requestMessageWithoutUserIdParameter);

        //when
        userSaveController.process(httpRequest, httpResponse);

        //then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    @DisplayName("회원가입 요청 시, 입력하지 않은 회원정보가 있으면 BAD REQUEST로 응답한다")
    void userSaveRequestWithEmptyParameter() throws Exception {
        //given
        String requestMessageWithEmptyUserId = "GET /user/create?userId=&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        HttpRequest httpRequest = createHttpRequest(requestMessageWithEmptyUserId);

        //when
        userSaveController.process(httpRequest, httpResponse);

        //then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatus());
    }

    @Test
    @DisplayName("회원가입 요청 시, 아이디가 중복되면 CONFLICT로 응답한다")
    void userSaveRequestWithConflictedUserID() throws Exception {
        //given
        User user = new User("javajigi", "password", "name", "email@domain.com");
        Database.addUser(user);

        //when
        String requestMessage = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        HttpRequest httpRequest = createHttpRequest(requestMessage);

        userSaveController.process(httpRequest, httpResponse);

        //then
        Assertions.assertEquals(HttpStatus.CONFLICT, httpResponse.getStatus());
    }

    private HttpRequest createHttpRequest(String requestMessage) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestMessage.getBytes());
        return new HttpRequest(byteArrayInputStream);
    }
}
