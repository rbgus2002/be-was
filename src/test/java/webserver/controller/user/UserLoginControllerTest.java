package webserver.controller.user;

import db.Database;
import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.utils.HttpField;
import webserver.utils.HttpRequestCreateUtil;

public class UserLoginControllerTest {

    @BeforeAll
    void init() {
        Database.addUser(new User("userId", "password", "name", "email@test.com"));
    }

    @Test
    @DisplayName("")
    void validLoginTest() throws Exception {
        //given
        String body = "userId=userId&password=password";

        String requestMessage = "POST /user/login HTTP/1.1\r\n"
                + "Content-Type: application/x-www-form-urlencoded\r\n"
                + "Content-Length: " + body.length() + "\r\n"
                + "\r\n"
                + body;

        HttpRequest httpRequest = HttpRequestCreateUtil.createHttpRequest(requestMessage);
        HttpResponse httpResponse = new HttpResponse();

        UserLoginController userLoginController = new UserLoginController();

        //when
        userLoginController.process(httpRequest, httpResponse);

        //then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
        softAssertions.assertThat(httpResponse.get(HttpField.LOCATION)).isEqualTo("/index.html");
        softAssertions.assertThat(httpResponse.get(HttpField.SET_COOKIE)).contains("SID");
        softAssertions.assertThat(httpResponse.get(HttpField.SET_COOKIE)).contains("Path=/");
    }

    @Test
    @DisplayName("")
    void invalidLoginTest() throws Exception {
        //given
        String body = "userId=userId&password=password";

        String requestMessage = "POST /user/login HTTP/1.1\r\n"
                + "Content-Type: application/x-www-form-urlencoded\r\n"
                + "Content-Length: " + body.length() + "\r\n"
                + "\r\n"
                + body;

        HttpRequest httpRequest = HttpRequestCreateUtil.createHttpRequest(requestMessage);
        HttpResponse httpResponse = new HttpResponse();

        UserLoginController userLoginController = new UserLoginController();

        //when
        userLoginController.process(httpRequest, httpResponse);

        //then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
        softAssertions.assertThat(httpResponse.get(HttpField.LOCATION)).isEqualTo("/user/login_failed.htm");
        softAssertions.assertThat(httpResponse.get(HttpField.SET_COOKIE)).doesNotContain("SID");
        softAssertions.assertThat(httpResponse.get(HttpField.SET_COOKIE)).doesNotContain("Path=/");
    }

}
