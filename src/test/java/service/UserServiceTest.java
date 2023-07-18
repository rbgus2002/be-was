package service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static http.HttpUtil.parseQueryParameter;

class UserServiceTest {
    @Test
    @DisplayName("유저가 회원가입하면 Database에 계정이 생성된다")
    public void signUp() throws Exception {
        // Given
        String targetUri = "/user/create?userId=jst0951&password=password&name=정성태&email=jst0951@naver.com";
        Map<String, String> queryParameterMap = parseQueryParameter(targetUri);

        // When
        UserService.userSignUp(queryParameterMap);

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(UserService.getAllUser().size()).isEqualTo(1);
        assertions.assertThat(UserService.getUser("jst0951").getName()).isEqualTo("정성태");
        assertions.assertAll();
    }
}
