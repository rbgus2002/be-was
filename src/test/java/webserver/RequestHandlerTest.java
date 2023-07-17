package webserver;

import db.Database;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.model.Request;

import static model.User.USERID;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.Socket;
import java.util.Map;

class RequestHandlerTest {
    @Test
    @DisplayName("유저가 회원가입하면 Database에 계정이 생성된다")
    public void signUp() throws Exception {
        // Given
        String targetUri = "/user/create?userId=jst0951&password=password&name=정성태&email=jst0951@naver.com";
        RequestHandler requestHandler = new RequestHandler(new Socket());
        Map<String, String> queryParameterMap = requestHandler.parseQueryParameter(targetUri);

        // When
        requestHandler.userSignUp(queryParameterMap);

        // Then
        assertThat(Database.findAll().size()).isEqualTo(1);
        assertThat(Database.findUserById("jst0951").getName()).isEqualTo("정성태");
    }
}
