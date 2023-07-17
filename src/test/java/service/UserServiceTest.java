package service;

import db.Database;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestHandler;

import java.net.Socket;
import java.util.Map;

class UserServiceTest {
    @Test
    @DisplayName("유저가 회원가입하면 Database에 계정이 생성된다")
    public void signUp() throws Exception {
        // Given
        String targetUri = "/user/create?userId=jst0951&password=password&name=정성태&email=jst0951@naver.com";
        RequestHandler requestHandler = new RequestHandler(new Socket());
        Map<String, String> queryParameterMap = requestHandler.parseQueryParameter(targetUri);

        // When
        UserService.userSignUp(queryParameterMap);

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(Database.findAll().size()).isEqualTo(1);
        assertions.assertThat(Database.findUserById("jst0951").getName()).isEqualTo("정성태");
        assertions.assertAll();
    }
}
