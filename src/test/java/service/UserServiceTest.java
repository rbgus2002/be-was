package service;

import db.Database;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static model.User.*;
import static webserver.http.HttpParser.*;

class UserServiceTest {
    @BeforeEach
    public void setup() {
        Database.deleteAllUser();
        Database.deleteAllSession();
    }

    @Test
    @DisplayName("유저가 회원가입하면 Database에 계정이 생성된다")
    public void signUp() throws Exception {
        // Given
        String targetUri = "/user/create?userId=jst0951&password=password&name=정성태&email=jst0951@naver.com";
        Map<String, String> queryParameterMap = parseQueryParameter(targetUri);

        // When
        UserService.userSignup(
                queryParameterMap.get(USERID), queryParameterMap.get(PASSWORD),
                queryParameterMap.get(NAME), queryParameterMap.get(EMAIL)
        );

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(UserService.getAllUser().size()).isEqualTo(1);
        assertions.assertThat(UserService.getUser("jst0951").getName()).isEqualTo("정성태");
        assertions.assertAll();
    }

    @Test
    @DisplayName("이미 등록된 유저가 회원가입을 시도하면 계정이 추가되지 않는다.")
    public void signUpAgain() throws Exception {
        // Given
        String targetUri = "/user/create?userId=jst0951&password=password&name=정성태&email=jst0951@naver.com";
        Map<String, String> queryParameterMap = parseQueryParameter(targetUri);
        UserService.userSignup(
                queryParameterMap.get(USERID), queryParameterMap.get(PASSWORD),
                queryParameterMap.get(NAME), queryParameterMap.get(EMAIL)
        );

        // When
        queryParameterMap.put(PASSWORD, "newPassword");
        UserService.userSignup(
                queryParameterMap.get(USERID), queryParameterMap.get(PASSWORD),
                queryParameterMap.get(NAME), queryParameterMap.get(EMAIL)
        );

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(UserService.getAllUser().size()).isEqualTo(1);
        assertions.assertThat(UserService.getUser("jst0951").getPassword()).isEqualTo("password");
        assertions.assertAll();
    }
}
