package webserver.controller.user;

import db.Database;
import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.session.Session;
import webserver.session.SessionManager;
import webserver.utils.HttpField;
import webserver.utils.HttpRequestCreateUtil;

class UserListControllerTest {
    UserListController userListController = new UserListController();
    SessionManager sessionManager = SessionManager.getInstance();
    SoftAssertions softAssertions;

    @BeforeAll
    static void init() {
        Database.addUser(new User("userIdA", "passwordA", "userA", "userA@naver.com"));
        Database.addUser(new User("userIdB", "passwordB", "userB", "userB@naver.com"));
        Database.addUser(new User("userIdC", "passwordC", "userC", "userC@naver.com"));
    }

    @BeforeEach
    void initSoftAssertions() {
        softAssertions = new SoftAssertions();
    }

    @Test
    @DisplayName("로그인 한 사용자가 사용자 목록 페이지 요청시, 사용자 목록을 응답한다")
    public void requestUserListWithValidSessionId() throws Exception {
        //given
        Session loginUserSession = new Session("userC");
        sessionManager.addSession(loginUserSession);

        String requestMessage = "GET /user/list HTTP/1.1\r\n" +
                "Cookie: sid=" + loginUserSession.getSessionId() + "\r\n" +
                "\r\n";

        //when
        HttpRequest httpRequest = HttpRequestCreateUtil.createHttpRequest(requestMessage);
        HttpResponse httpResponse = new HttpResponse();
        userListController.process(httpRequest, httpResponse);

        //then
        String responseBodyMessage = new String(httpResponse.getBodyBytes());

        softAssertions.assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK);
        softAssertions.assertThat(responseBodyMessage.contains("userA"));
        softAssertions.assertThat(responseBodyMessage.contains("userB"));
        softAssertions.assertThat(responseBodyMessage.contains("userC"));
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("세션 아이디가 없는 상태로 사용자 목록 페이지 요청 시, 로그인 페이지로 이동시킨다")
    public void requestUserListWithoutSessionId() throws Exception {
        //given
        String requestMessage = "GET /user/list HTTP/1.1\r\n" +
                "\r\n";

        //when
        HttpRequest httpRequest = HttpRequestCreateUtil.createHttpRequest(requestMessage);
        HttpResponse httpResponse = new HttpResponse();
        userListController.process(httpRequest, httpResponse);

        //then
        softAssertions.assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
        softAssertions.assertThat(httpResponse.get(HttpField.LOCATION)).isEqualTo("/login.html");
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("유효하지 않는 세션 아이디로 사용자 목록 페이지 요청 시, 로그인 페이지로 이동시킨다")
    public void requestUserListWithoutSessionId() throws Exception {
        //given
        Session loginUserSession = new Session("userC");
        sessionManager.addSession(loginUserSession);

        String requestMessage = "GET /user/list HTTP/1.1\r\n" +
                "Cookie: sid=" + loginUserSession.getSessionId() + "dummyString" + "\r\n" +
                "\r\n";

        //when
        HttpRequest httpRequest = HttpRequestCreateUtil.createHttpRequest(requestMessage);
        HttpResponse httpResponse = new HttpResponse();
        userListController.process(httpRequest, httpResponse);

        //then
        String responseBodyMessage = new String(httpResponse.getBodyBytes());

        softAssertions.assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
        softAssertions.assertThat(httpResponse.get(HttpField.LOCATION)).isEqualTo("/login.html");
        softAssertions.assertAll();
    }
}
